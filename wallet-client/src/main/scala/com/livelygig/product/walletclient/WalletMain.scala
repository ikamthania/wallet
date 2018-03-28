package com.livelygig.product.walletclient

import com.livelygig.product.walletclient.handler.{ LoginUser, UpdateAccountTokenList }
import com.livelygig.product.walletclient.router.ApplicationRouter
import com.livelygig.product.walletclient.services.{ LocalStorageApi, WalletCircuit }
import com.livelygig.product.walletclient.utils.{ Bundles, SessionKeys }
import org.scalajs.dom
import diode.AnyAction._

object WalletMain extends Bundles {

  def main(args: Array[String]): Unit = {
    println("Application started, creating initial root model and subscribing to changes")
    /*if (dom.window.sessionStorage.getItem(SessionKeys.isSessionVerified) != null) {
      WalletCircuit.dispatch(LoginUser())
    }*/
    LocalStorageApi.subscribeToAppRootChanges()
    ApplicationRouter.router().renderIntoDOM(dom.document.getElementById("root"))
  }

}