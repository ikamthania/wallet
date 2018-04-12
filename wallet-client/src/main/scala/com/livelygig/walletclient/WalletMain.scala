package com.livelygig.walletclient

import com.livelygig.walletclient.router.ApplicationRouter
import com.livelygig.walletclient.services.LocalStorageApi
import com.livelygig.walletclient.utils.Bundles
import org.scalajs.dom

object WalletMain extends Bundles {

  def main(args: Array[String]): Unit = {
    println("Application started, creating initial root model and subscribing to changes")
    LocalStorageApi.subscribeToAppRootChanges()
    ApplicationRouter.router().renderIntoDOM(dom.document.getElementById("root"))
  }

}