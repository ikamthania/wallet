package com.livelygig.walletclient.handler

import com.livelygig.shared.models.wallet.TokenDetails
import com.livelygig.walletclient.rootmodel.TokenDetailsRootModel
import com.livelygig.walletclient.services.{ CoreApi, WalletCircuit }
import diode.data.{ Empty, Pot, PotActionRetriable, Ready }
import diode.util.{ Retry, RetryPolicy }
import diode.{ ActionHandler, ActionResult, ModelRW }
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global

case class UpdateAccountTokenList(potResult: Pot[TokenDetailsRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
  extends PotActionRetriable[TokenDetailsRootModel, UpdateAccountTokenList] {
  override def next(newValue: Pot[TokenDetailsRootModel], newRetryPolicy: RetryPolicy): UpdateAccountTokenList = UpdateAccountTokenList(newValue, newRetryPolicy)
}

case class AddTokens(newToken: TokenDetails)

class ERCTokenHandler[M](modelRW: ModelRW[M, Pot[TokenDetailsRootModel]]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case action: UpdateAccountTokenList => {
      val updatedERCTokenList = action.effectWithRetry {
        CoreApi.mobileGetAccountDetails(WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo.selectedAddress).value)
      } { tokenList =>
        TokenDetailsRootModel(Json.parse(tokenList).validate[Seq[TokenDetails]].getOrElse(Nil))
      }
      action.handleWith(this, updatedERCTokenList)(PotActionRetriable.handler())
    }
    case AddTokens(newTokenList: Seq[TokenDetails]) => {
      updated(Ready(TokenDetailsRootModel(newTokenList)))

    }
  }

}
