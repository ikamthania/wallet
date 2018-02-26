package com.livelygig.product.walletclient

import com.livelygig.product.walletclient.views.facades.WalletJS
import com.livelygig.product.walletclient.router.ApplicationRouter
import org.scalajs.dom

object WalletMain {

  def main(args: Array[String]): Unit = {

    //    // initialize pulldowntorefresh.js
    //    PullToRefresh.init(js.Dictionary(
    //      "mainElement" -> "body"
    //    ))
    //    println(WalletJS.postRawTxn("", "", "0x4Cd69393038570d8EA4165445530225A87B3cC82", "ngc", "7", "0"))
    ApplicationRouter.router().renderIntoDOM(dom.document.getElementById("root"))

    /*CoreApi.mobileGetUserDetails().map { userDetails =>
      Json.parse(userDetails).validate[UserDetails].asOpt match {
        case Some(response) =>
          WalletCircuit.dispatch(GetUserDetails(response))
          ApplicationRouter.router().renderIntoDOM(dom.document.getElementById("root"))

        case None => println("Error in parsing user details response")
      }
    }
      .recover {
        case e: Exception => ApplicationRouter.router().renderIntoDOM(dom.document.getElementById("root"))

      }*/
  }

}