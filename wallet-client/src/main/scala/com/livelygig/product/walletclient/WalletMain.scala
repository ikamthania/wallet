package com.livelygig.product.walletclient

import com.livelygig.product.walletclient.router.ApplicationRouter
import com.livelygig.product.walletclient.services.LocalStorageApi
import com.livelygig.product.walletclient.utils.{ Bundles, TestApi2 }
import org.scalajs.dom

object WalletMain extends Bundles {

  def main(args: Array[String]): Unit = {
    println("Application started, creating initial root model and subscribing to changes")
    LocalStorageApi.subscribeToAppRootChanges()
    ApplicationRouter.router().renderIntoDOM(dom.document.getElementById("root"))
  }

}