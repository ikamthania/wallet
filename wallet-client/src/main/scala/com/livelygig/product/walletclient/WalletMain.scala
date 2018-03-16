package com.livelygig.product.walletclient

import com.livelygig.product.walletclient.handler.GetSenderAddress
import com.livelygig.product.walletclient.router.ApplicationRouter
import com.livelygig.product.walletclient.services.WalletCircuit
import com.livelygig.product.walletclient.utils.Bundles
import org.scalajs.dom
import diode.AnyAction._

object WalletMain extends Bundles {

  def main(args: Array[String]): Unit = {

    dom.window.document.addEventListener("message", (fun: dom.MessageEvent) => {
      dom.window.alert(fun.data.toString)
      WalletCircuit.dispatch(GetSenderAddress(fun.data.toString))
    })
    ApplicationRouter.router().renderIntoDOM(dom.document.getElementById("root"))
  }

}