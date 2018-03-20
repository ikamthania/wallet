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

  override def getETHNetConnected(): ServiceCall[NotUsed, String] = ServerServiceCall { _ =>
    web3JUtils.getNetworkInfo().map(ethNet => ethNet)
  }

  //Client authenticated API's for Mobile App

  override def mobileGetETHNetConnected(): ServiceCall[NotUsed, String] = ServerServiceCall { _ =>
    web3JUtils.getNetworkInfo().map(ethNet => ethNet)
  }

  override def mobileGetAccountDetails(publicKey: String): ServiceCall[NotUsed, UserDetails] = ServerServiceCall { _ =>

    Future(UserDetails(publicKey, WalletDetails(publicKey, web3JUtils.getBalance(publicKey))))

  }

  override def mobileGetAccountTransactionHistory(publicKey: String): ServiceCall[Seq[ERC20ComplientToken], Seq[TransactionWithSymbol]] =
    ServerServiceCall { eRC20ComplientToken =>
      etherScanUtils.getTransactionList(publicKey, eRC20ComplientToken)
        .map { txnList => txnList }

    }

  override def mobileGetTransactionStatus(txnHash: String): ServiceCall[NotUsed, String] = ServerServiceCall { _ =>
    etherScanUtils.getTransactionStatus(txnHash).map { txnHash => txnHash.replace("\"", "") }
  }

  override def mobileAccountTokensDetails(publicKey: String): ServiceCall[Seq[ERC20ComplientToken], Seq[ERC20ComplientToken]] = ServerServiceCall { tokens =>

    web3JUtils
      .getTokenBalance(tokens, publicKey)
      .map { tokenList => tokenList }

  }

  override def mobileGetNonce(publicKey: String): ServiceCall[EtherTransaction, SignedTxnParams] = ServerServiceCall { etherTransaction =>
    web3JUtils
      .getNonce(publicKey, etherTransaction)
      .map { e => e }
  }

  override def mobileSendSignedTransaction(): ServiceCall[String, String] = ServerServiceCall { signedTxn =>
    web3JUtils
      .sendSignedTransaction(signedTxn)
      .map { e => e }
  }
}

