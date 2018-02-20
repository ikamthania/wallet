
package com.livelygig.product.walletclient.rootmodel

import com.livelygig.product.shared.models.wallet.{ Transaction, TransactionWithSymbol }

case class TransactionRootModel(accountTransactionHistory: Seq[TransactionWithSymbol]) {

  def updated(newTransaction: TransactionWithSymbol): TransactionRootModel = {
    accountTransactionHistory.indexWhere(_.transaction.hash == newTransaction.transaction.hash) match {
      case -1 => TransactionRootModel(accountTransactionHistory :+ newTransaction)
      case target => TransactionRootModel(accountTransactionHistory.updated(target, newTransaction))
    }
  }
}
