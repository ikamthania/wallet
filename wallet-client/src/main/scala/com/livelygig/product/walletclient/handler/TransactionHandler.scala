package com.livelygig.product.walletclient.handler

import com.livelygig.product.shared.models.wallet.TransactionWithSymbol
import com.livelygig.product.walletclient.rootmodel.TransactionRootModel
import com.livelygig.product.walletclient.services.{ CoreApi, WalletCircuit }
import diode.data.{ Empty, Pot, PotActionRetriable, Ready }
import diode.util.{ Retry, RetryPolicy }
import diode.{ ActionHandler, ActionResult, ModelRW }
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global

case class GetAccountHistoryDetails(potResult: Pot[TransactionRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
  extends PotActionRetriable[TransactionRootModel, GetAccountHistoryDetails] {
  override def next(newValue: Pot[TransactionRootModel], newRetryPolicy: RetryPolicy): GetAccountHistoryDetails = GetAccountHistoryDetails(newValue, newRetryPolicy)
}
case class AddTransactions(newTransaction: TransactionWithSymbol)

class TransactionHandler[M](modelRW: ModelRW[M, Pot[TransactionRootModel]]) extends ActionHandler(modelRW) {

  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case action: GetAccountHistoryDetails => {
      val updateF = action.effectWithRetry {
        CoreApi.mobileGetAccountHistoryDetails(WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo.selectedAddress).value)
      } { transactionListResponse =>
        val updatedTxnList = Json.parse(transactionListResponse)
          .validate[Seq[TransactionWithSymbol]].get

        TransactionRootModel(updatedTxnList)
      }
      action.handleWith(this, updateF)(PotActionRetriable.handler())
    }
    case AddTransactions(newTransactionList: Seq[TransactionWithSymbol]) => {
      updated(Ready(TransactionRootModel(newTransactionList)))

    }
  }

}