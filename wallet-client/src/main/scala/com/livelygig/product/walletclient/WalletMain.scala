package com.livelygig.product.walletclient

import com.livelygig.product.walletclient.router.ApplicationRouter
import com.livelygig.product.walletclient.services.LocalStorageApi
import com.livelygig.product.walletclient.utils.Bundles
import org.scalajs.dom

object WalletMain extends Bundles {

  def main(args: Array[String]): Unit = {
    LocalStorageApi.subscribeToAppRootChanges()

    ApplicationRouter.router().renderIntoDOM(dom.document.getElementById("root"))
  }

}