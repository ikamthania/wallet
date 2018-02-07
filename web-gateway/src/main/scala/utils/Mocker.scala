package utils

import com.livelygig.product.shared.models._
import com.livelygig.product.shared.models.wallet.{CoinExchange, CurrencyList, ERC20ComplientToken, Transaction}
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.apache.http.util.EntityUtils
import play.api.libs.json.Json

/**
 * Created by shubham.k on 21-03-2017.
 */
trait Mocker extends AppLogger {
  val mockdataLocation: String = ""
  def getMockNotificationsResponse = {
    try {
      val mock = scala.io.Source.fromFile(s"${mockdataLocation}/notifications.json", "utf-8").mkString
      Json.parse(mock).validate[Seq[NotificationResponse]]
        .asEither match {
          case Left(err) =>
            log.error(err.toString())
            Nil
          case Right(res) =>
            res
        }
    } catch {
      case e: Exception =>
        log.error(s"Error in loading mock file at ${mockdataLocation}/notifications.json")
        Nil
    }
  }

  def getMockConnectionResponse = {
    try {
      val mock = scala.io.Source.fromFile(s"${mockdataLocation}/connections.json", "utf-8").mkString
      Json.parse(mock).validate[Seq[Connection]]
        .getOrElse(Nil)
    } catch {
      case e: Exception =>
        log.error(s"Error in loading mock file at ${mockdataLocation}/connections.json")
        Nil
    }
  }

  def getMockContentResponse(responseType: String) = {
    try {
      val mock = scala.io.Source.fromFile(s"${mockdataLocation}/${responseType}.json", "utf-8").mkString
      Json.parse(mock).validate[Seq[ContentResponse]]
        .asEither match {
          case Left(err) =>
            log.error(s"Error in parsing content for $responseType : ${err}")
            Nil
          case Right(res) =>
            res
        }
    } catch {
      case e: Exception =>
        log.error(s"Error in loading mock file at ${mockdataLocation}/${responseType}.json")
        Nil
    }
  }

  def getMockWalletTokenListResponse(responseType: String) = {
    try {
      val mock = scala.io.Source.fromFile(s"${mockdataLocation}/${responseType}.json", "utf-8").mkString
      Json.parse(mock).validate[Seq[ERC20ComplientToken]]
        .asEither match {
          case Left(err) =>
            log.error(s"Error in parsing content for $responseType : ${err}")
            Nil
          case Right(res) =>
            res
        }
    } catch {
      case e: Exception =>
        log.error(s"Error in loading mock file at ${mockdataLocation}/${responseType}.json")
        Nil
    }
  }

  def getMockWalletHistoryResponse(responseType: String) = {
    try {
      val mock = scala.io.Source.fromFile(s"${mockdataLocation}/${responseType}.json", "utf-8").mkString
      Json.parse(mock).validate[Seq[Transaction]]
        .asEither match {
          case Left(err) =>
            log.error(s"Error in parsing content for $responseType : ${err}")
            Nil
          case Right(res) =>
            res
        }
    } catch {
      case e: Exception =>
        log.error(s"Error in loading mock file at ${mockdataLocation}/${responseType}.json")
        Nil
    }
  }

  def getLiveMarketPrices(): CoinExchange = {

    val coinList = scala.io.Source.fromFile(s"${mockdataLocation}/accountTokensDetails.json", "utf-8").mkString

    val coinSymbolList = Json.parse(coinList).validate[Seq[ERC20ComplientToken]].get.map { token => token.symbol.toUpperCase }

    val mock = scala.io.Source.fromFile(s"${mockdataLocation}/currencyExchange.json", "utf-8").mkString

    val currencyList = Json.parse(mock).validate[CurrencyList].map(e => e)

    val httpClient: CloseableHttpClient = HttpClients.createDefault()
    CoinExchange(
      coinSymbolList.map { token =>
        val get = new HttpGet(s"https://min-api.cryptocompare.com/data/price?fsym=${token}&tsyms=${currencyList.get.currencies.map(e => e.symbol).mkString(",")}")
        try {
          val httpResponse = httpClient.execute(get)
          val httpEntity = httpResponse.getEntity
          val response = EntityUtils.toString(httpEntity)
          CurrencyList(token, currencyList.get.currencies.map(e => e.copy(price = Json.parse(response).\(e.symbol).validate[Double].get)))

        } catch {
          case err: Exception =>
            err.getCause
            CurrencyList(token, currencyList.get.currencies.map(currency => currency.copy(price = 0)))
        }
      }
    )
  }
}

