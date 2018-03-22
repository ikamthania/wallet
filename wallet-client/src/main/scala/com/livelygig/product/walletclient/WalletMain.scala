package com.livelygig.product.walletclient

import com.livelygig.product.shared.models.wallet.Account
import com.livelygig.product.walletclient.handler.{ AddAccount, AddNewAccount }
import com.livelygig.product.walletclient.router.ApplicationRouter
import com.livelygig.product.walletclient.services.{ LocalStorageApi, WalletCircuit }
import com.livelygig.product.walletclient.utils.Bundles
import org.scalajs.dom
import diode.AnyAction._

object WalletMain extends Bundles {

  def main(args: Array[String]): Unit = {
    //    println(LocalStorageApi.updateAppModel)
    LocalStorageApi.subscribeToAppRootChanges()
    WalletCircuit.dispatch(AddNewAccount(Account("test", "test")))
    println(WalletCircuit.zoomTo(_.appRootModel).value)
    ApplicationRouter.router().renderIntoDOM(dom.document.getElementById("root"))
  }

}