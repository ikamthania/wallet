package com.livelygig.product.wallet.impl.Utils

import net.ceedubs.ficus.Ficus._
import java.io.IOException
import java.math.BigInteger

import com.lightbend.lagom.scaladsl.api.transport.{ BadRequest, NotFound }
import com.livelygig.product.shared.models.wallet.{ ERC20ComplientToken, Transaction, TransactionWithSymbol }
import org.apache.commons.codec.binary.Hex
import org.apache.http.client.methods.{ CloseableHttpResponse, HttpGet }
import org.apache.http.impl.client.{ CloseableHttpClient, HttpClients }
import org.apache.http.util.EntityUtils
import org.web3j.utils.Convert
import play.api.Configuration
import play.api.libs.json.{ JsDefined, Json }

import scala.concurrent.{ ExecutionContext, Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.math.BigDecimal.RoundingMode
class EtherscanUtils(configuration: Configuration)(implicit ec: ExecutionContext) {

  private val walletConfig = configuration.getConfig("wallet").get.underlying
  private val PUBLIC_URL: String = walletConfig.as[String]("etherscan.io")
  private val API_KEY: String = walletConfig.as[String]("etherscan.api_key")
  private val httpClient: CloseableHttpClient = HttpClients.createDefault()

  def getTransactionList(address: String, eRC20ComplientTokenList: Seq[ERC20ComplientToken]): Future[Seq[TransactionWithSymbol]] = {
    val get = new HttpGet(PUBLIC_URL + "?module=account&action=txlist&address=" + address + "&startblock=earliest&endblock=latest&sort=asc" + "&apikey=" + API_KEY)
    var response: String = ""
    try {
      val httpResponse = httpClient.execute(get)
      val httpEntity = httpResponse.getEntity
      response = EntityUtils.toString(httpEntity)
    } catch {
      case e: IOException => print("Error-" + e)
    }
    val txnList = Json.parse(response).\("result").validate[Seq[Transaction]].asOpt match {
      case Some(transactionList) =>
        transactionList.map { transaction =>
          val token = eRC20ComplientTokenList.find(_.contractAddress == transaction.to)

          token match {
            case Some(token) =>
              TransactionWithSymbol(transaction.copy(value = tokenTransactionBalance(token, transaction.blockNumber)), token.symbol.toUpperCase)
            case None =>
              TransactionWithSymbol(transaction.copy(value = BigDecimal(Convert.fromWei(transaction.value, Convert.Unit.ETHER)).setScale(4, RoundingMode.FLOOR).toString()), "ETH")
          }
        }

      case None => throw BadRequest("Error in parsing transactionList")
    }
    Future(txnList)
  }

  def getTransactionStatus(txHash: String): Future[String] = {
    val get = new HttpGet(PUBLIC_URL + "?module=proxy&action=eth_getTransactionByHash&txhash=" + txHash + "&apikey=" + API_KEY)
    var response: String = ""
    try {
      val httpResponse = httpClient.execute(get)
      val httpEntity = httpResponse.getEntity
      response = EntityUtils.toString(httpEntity)
    } catch {
      case e: IOException => print("Error-" + e.getStackTrace)
    }
    Future(
      if (Json.parse(response).\("result").asInstanceOf[JsDefined].value.toString != "null")
        Json.parse(response).\("result").\("blockHash").asInstanceOf[JsDefined].value.toString
      else
        "null")
  }

  def tokenTransactionBalance(erc20ComplientToken: ERC20ComplientToken, topicId: String): String = {
    val get = new HttpGet(PUBLIC_URL + "?module=logs&action=getLogs&fromBlock=" + topicId + "&toBlock=" + topicId + "&address=" + erc20ComplientToken.contractAddress + "&apikey=" + API_KEY)
    var response: String = ""
    try {
      val httpResponse = httpClient.execute(get)
      val httpEntity = httpResponse.getEntity
      response = EntityUtils.toString(httpEntity)
      val rawBalance = new BigInteger(Json.parse(response).\("result").\\("data").head.toString().replace("\"", "").substring(2), 16)
      (BigDecimal.apply(rawBalance.toString) / Math.pow(10, erc20ComplientToken.decimal)).setScale(4, RoundingMode.FLOOR).toString()

    } catch {
      case e: NoSuchElementException => "0"
      case e: IOException => "Encountered problem while fetching token balance"
    }

  }
}