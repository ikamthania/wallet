package com.livelygig.product.content.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{ Service, ServiceCall }
import com.livelygig.product.shared.models.wallet._

trait WalletService extends Service {

  def ping(): ServiceCall[String, String]

  //Non-secured API's for Mobile App

  def mobileGetETHNetConnected(): ServiceCall[NotUsed, String]

  def mobileGetAccountTransactionHistory(publicKey: String): ServiceCall[Seq[TokenDetails], Seq[TransactionWithSymbol]]

  def mobileGetTransactionStatus(txnHash: String): ServiceCall[NotUsed, String]

  def mobileAccountTokensDetails(publicKey: String): ServiceCall[Seq[TokenDetails], Seq[TokenDetails]]

  def mobileSendSignedTransaction(): ServiceCall[String, String]

  def mobileGetEncodedFunction(): ServiceCall[EtherTransaction, String]

  def descriptor = {
    import Service._
    named("wallet").withCalls(
      namedCall("/api/wallet/ping", ping _),
      pathCall("/api/wallet/mobile/ethnet/info", mobileGetETHNetConnected _),
      pathCall("/api/wallet/mobile/:publicKey/account/erctoken/details", mobileAccountTokensDetails _),
      pathCall("/api/wallet/mobile/status/:txnHash", mobileGetTransactionStatus _),
      pathCall("/api/wallet/mobile/:publicKey/transactions/", mobileGetAccountTransactionHistory _),
      pathCall("/api//wallet/mobile/signedTxn", mobileSendSignedTransaction _),
      pathCall("/api//wallet/mobile/encodedfunction", mobileGetEncodedFunction _))
  }
}

