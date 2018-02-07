package com.livelygig.product.walletclient.handler

import com.livelygig.product.shared.models.wallet.ERC20ComplientToken
import com.livelygig.product.walletclient.rootmodel.ERCTokenRootModel
import com.livelygig.product.walletclient.services.{CoreApi, WalletCircuit}
import diode.data.{Empty, Pot, PotActionRetriable, Ready}
import diode.util.{Retry, RetryPolicy}
import diode.{ActionHandler, ActionResult, ModelRW}
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global

case class UpdateAccountTokenList(potResult: Pot[ERCTokenRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
    extends PotActionRetriable[ERCTokenRootModel, UpdateAccountTokenList] {
  override def next(newValue: Pot[ERCTokenRootModel], newRetryPolicy: RetryPolicy): UpdateAccountTokenList = UpdateAccountTokenList(newValue, newRetryPolicy)
}

case class AddTokens(newToken: ERC20ComplientToken)

class ERCTokenHandler[M](modelRW: ModelRW[M, Pot[ERCTokenRootModel]]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case action: UpdateAccountTokenList => {
      val updatedERCTokenList = action.effectWithRetry {
        CoreApi.mobileGetAccountDetails()
      } { tokenList =>
        ERCTokenRootModel(Json.parse(tokenList).validate[Seq[ERC20ComplientToken]].get)
      }
      action.handleWith(this, updatedERCTokenList)(PotActionRetriable.handler())
    }
    case AddTokens(newTokenList: Seq[ERC20ComplientToken]) => {
      updated(Ready(ERCTokenRootModel(newTokenList)))

    }
  }

}
