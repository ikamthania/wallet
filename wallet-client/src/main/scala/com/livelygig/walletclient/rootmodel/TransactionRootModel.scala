
package com.livelygig.walletclient.rootmodel

import com.livelygig.shared.models.wallet.TransactionWithSymbol

case class TransactionRootModel(accountTransactionHistory: Seq[TransactionWithSymbol]) {

  def updated(newTransaction: TransactionWithSymbol): TransactionRootModel = {
    accountTransactionHistory.indexWhere(_.transaction.hash == newTransaction.transaction.hash) match {
      case -1 => TransactionRootModel(accountTransactionHistory :+ newTransaction)
      case target => TransactionRootModel(accountTransactionHistory.updated(target, newTransaction))
    }
  }
}
