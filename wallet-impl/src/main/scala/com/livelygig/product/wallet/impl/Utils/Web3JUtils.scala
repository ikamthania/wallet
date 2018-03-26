package com.livelygig.product.wallet.impl.Utils

import java.io.{ File, FileNotFoundException, FileOutputStream }
import java.math.BigInteger
import java.util

import com.livelygig.product.shared.models.wallet.{ ERC20ComplientToken, EtherTransaction }
import com.livelygig.product.wallet.api.models.ValidateWalletFile
import net.ceedubs.ficus.Ficus._
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.{ Address, Function, Utf8String }
import org.web3j.abi.{ FunctionEncoder, FunctionReturnDecoder, TypeReference }
import org.web3j.crypto.{ CipherException, Credentials, TransactionEncoder, WalletUtils }
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request._
import org.web3j.protocol.exceptions._
import org.web3j.protocol.http.HttpService
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.utils.{ Convert, Numeric }
import play.api.Configuration

import scala.concurrent.{ ExecutionContext, Future }
import scala.io.Source
import scala.math.BigDecimal.RoundingMode

class Web3JUtils(configuration: Configuration)(implicit ec: ExecutionContext) {
  private val walletConfig = configuration.getConfig("wallet").get.underlying
  val uploadPath = s"${new File(".").getCanonicalPath()}/${walletConfig.as[String]("uploadDirectory")}"
  val directory = new File(uploadPath)
  if (!directory.exists) {
    directory.mkdirs
  }

  private val isInfuraEnabled = walletConfig.as[Option[Boolean]]("infura.enabled").getOrElse(false)
  private val web3j = if (isInfuraEnabled) {
    val infuraServer = walletConfig.as[String]("infura.ropsten.server")
    Web3j.build(new InfuraHttpService(infuraServer))
  } else {
    val rpcServer = walletConfig.as[Option[String]]("rpc.server").getOrElse("http://localhost:8545/")
    Web3j.build(new HttpService(rpcServer))
  }

  //  def getWeb3ClientVersion() = {
  //    web3j.web3ClientVersion().sendAsync().get()
  //  }

  def getBalance(accountID: String): String = {

    val ethGetBalance = web3j.ethGetBalance(accountID, DefaultBlockParameterName.LATEST).sendAsync.get
    Convert.fromWei(ethGetBalance.getBalance.toString, Convert.Unit.ETHER).setScale(4, RoundingMode.UP).toString()

  }

  //  def obtainCredentials(validateWalletFile: ValidateWalletFile): Credentials = {
  //    val ftmp = File.createTempFile("wa_", "json", new File("/tmp"))
  //    val fos = new FileOutputStream(ftmp)
  //    fos.write(validateWalletFile.fileContent.getBytes)
  //    fos.close()
  //    val credentials = WalletUtils.loadCredentials(
  //      validateWalletFile.unlockPassword,
  //      ftmp.getAbsolutePath)
  //    ftmp.delete
  //    credentials
  //  }

  def getNetworkInfo(): Future[String] = {
    val ntwrk = web3j.netVersion().send().getNetVersion match {
      case "1" => "Ethereum Mainnet"
      case "2" => "Morden Testnet"
      case "3" => "Ropsten Testnet"
      case "4" => "Rinkeby Testnet"
      case "42" => "Kovan Testnet"
      case "5810" => "INFURA Testnet"
      case _ => "Not Found"
    }
    Future(ntwrk)
  }

  //Get token balance using EIP20 std method
  def getTokenBalance(eRC20ComplientTokenList: Seq[ERC20ComplientToken], ownerAddress: String): Future[Seq[ERC20ComplientToken]] = {
    val erc20TokenSeq = eRC20ComplientTokenList.filterNot(e => e.symbol.equalsIgnoreCase("ETH")).map { eRC20ComplientToken =>
      val function = new Function(
        "balanceOf",
        util.Arrays.asList(new Address(ownerAddress)), util.Arrays.asList(new TypeReference[Uint256]() {}))
      val encodedFunction = FunctionEncoder.encode(function)
      val response = web3j
        .ethCall(Transaction.createEthCallTransaction(ownerAddress, eRC20ComplientToken.contractAddress, encodedFunction), DefaultBlockParameterName.LATEST)
        .sendAsync().get()
      val value = FunctionReturnDecoder.decode(
        response.getValue(), function.getOutputParameters())
      value.iterator().next().getValue.toString match {
        case "0" => eRC20ComplientToken.copy(balance = "0")
        case e => eRC20ComplientToken.copy(balance = s"${BigDecimal(e) / Math.pow(10, eRC20ComplientToken.decimal)}")
      }
    }
    val ethereumToken = eRC20ComplientTokenList.filter(e => e.symbol.equalsIgnoreCase("ETH")).map { e => e.copy(balance = getBalance(ownerAddress)) }

    Future(erc20TokenSeq ++ ethereumToken)
  }
  /*

  //Get token name using EIP20 std method
  def getTokenName(contractAddress: String, ownerAddress: String): String = {
    val function = new Function(
      "name",
      util.Arrays.asList(), util.Arrays.asList(new TypeReference[Utf8String]() {}))
    val encodedFunction = FunctionEncoder.encode(function)
    val response = web3j
      .ethCall(Transaction.createEthCallTransaction(ownerAddress, contractAddress, encodedFunction), DefaultBlockParameterName.LATEST)
      .sendAsync().get()
    val value = FunctionReturnDecoder.decode(
      response.getValue(), function.getOutputParameters())
    value.iterator().next().getValue.toString
  }

  //Get token name using EIP20 std method
  def getTokenSymbol(contractAddress: String, ownerAddress: String) = {
    val function = new Function(
      "symbol",
      util.Arrays.asList(), util.Arrays.asList(new TypeReference[Utf8String]() {}))
    val encodedFunction = FunctionEncoder.encode(function)
    val response = web3j
      .ethCall(Transaction.createEthCallTransaction(ownerAddress, contractAddress, encodedFunction), DefaultBlockParameterName.LATEST)
      .sendAsync().get()
    val value = FunctionReturnDecoder.decode(
      response.getValue(), function.getOutputParameters())
    value.iterator().next().getValue.toString
  }

  def getTokenVersion(contractAddress: String, ownerAddress: String): String = {
    val function = new Function(
      "version",
      util.Arrays.asList(), util.Arrays.asList(new TypeReference[Utf8String]() {}))
    val encodedFunction = FunctionEncoder.encode(function)
    val response = web3j
      .ethCall(Transaction.createEthCallTransaction(ownerAddress, contractAddress, encodedFunction), DefaultBlockParameterName.LATEST)
      .sendAsync().get()
    val value = FunctionReturnDecoder.decode(
      response.getValue(), function.getOutputParameters())
    value.iterator().next().getValue.toString
  }

  def getTokenTotalSupply(contractAddress: String, ownerAddress: String): String = {
    val function = new Function(
      "totalSupply",
      util.Arrays.asList(), util.Arrays.asList(new TypeReference[Uint256]() {}))
    val encodedFunction = FunctionEncoder.encode(function)
    val response = web3j
      .ethCall(Transaction.createEthCallTransaction(ownerAddress, contractAddress, encodedFunction), DefaultBlockParameterName.LATEST)
      .sendAsync().get()
    val value = FunctionReturnDecoder.decode(
      response.getValue(), function.getOutputParameters())
    value.iterator().next().getValue.toString
  }
*/

  def sendSignedTransaction(signedMessage: String): Future[String] = {
    val txnInfo = try {

      val ethSendTransaction = web3j.ethSendRawTransaction(signedMessage).sendAsync().get()

      if (!ethSendTransaction.hasError) {
        ethSendTransaction.getTransactionHash()
      } else {
        ethSendTransaction.getError.getMessage
      }

    } catch {
      case _: TransactionTimeoutException => "Transaction time-out"
      case _: MessageDecodingException => "Invalid recipient address.Recipient address must be in format 0x[1-9]+[0-9]* or 0x0"
      case _: MessageEncodingException => "Encountered error while encoding message"
      case _: CipherException => "Invalid password provided"
      case _: NumberFormatException => "Invalid ether amount"
      case _: FileNotFoundException => "KeyStore file is not available"
      case e => s"Encountered problem while making ether transaction $e"
    }

    Future(txnInfo)
  }

}

