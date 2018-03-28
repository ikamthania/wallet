package com.livelygig.product.wallet.impl

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.Materializer
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.server.ServerServiceCall
import com.livelygig.product.content.api.WalletService
import com.livelygig.product.shared.models.wallet._
import com.livelygig.product.wallet.impl.Utils.{ EtherscanUtils, Web3JUtils }

import scala.concurrent.{ ExecutionContext, Future }

class WalletServiceImpl(
  system: ActorSystem,
  web3JUtils: Web3JUtils,
  etherScanUtils: EtherscanUtils)(implicit ec: ExecutionContext, mat: Materializer) extends WalletService {

  override def ping(): ServiceCall[String, String] = ServiceCall { _ =>
    Future {
      "Pong"
    }
  }

  //Client authenticated API's for Mobile App

  override def mobileGetETHNetConnected(): ServiceCall[NotUsed, String] = ServerServiceCall { _ =>
    web3JUtils.getNetworkInfo().map(ethNet => ethNet)
  }

  override def mobileGetAccountTransactionHistory(publicKey: String): ServiceCall[Seq[TokenDetails], Seq[TransactionWithSymbol]] =
    ServerServiceCall { eRC20ComplientToken =>
      etherScanUtils.getTransactionList(publicKey, eRC20ComplientToken)
        .map { txnList => txnList }

    }

  override def mobileGetTransactionStatus(txnHash: String): ServiceCall[NotUsed, String] = ServerServiceCall { _ =>
    etherScanUtils.getTransactionStatus(txnHash).map { txnHash => txnHash.replace("\"", "") }
  }

  override def mobileAccountTokensDetails(publicKey: String): ServiceCall[Seq[TokenDetails], Seq[TokenDetails]] = ServerServiceCall { tokens =>

    web3JUtils
      .getTokenBalance(tokens, publicKey)
      .map { tokenList => tokenList }

  }

  override def mobileSendSignedTransaction(): ServiceCall[String, String] = ServerServiceCall { signedTxn =>
    web3JUtils
      .sendSignedTransaction(signedTxn)
      .map { e => e }
  }

  override def mobileGetEncodedFunction(): ServiceCall[EtherTransaction, String] = ServerServiceCall { signedTxn =>
    web3JUtils
      .getEncodedFunction(signedTxn)
      .map { e => e }
  }
}

