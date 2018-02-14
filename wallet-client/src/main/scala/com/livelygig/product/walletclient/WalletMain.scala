package com.livelygig.product.walletclient

import com.livelygig.product.shared.models.wallet.UserDetails
import com.livelygig.product.walletclient.facades.PullToRefresh
import com.livelygig.product.walletclient.handler.GetUserDetails
import com.livelygig.product.walletclient.router.ApplicationRouter
import com.livelygig.product.walletclient.services.{CoreApi, WalletCircuit}
import diode.AnyAction._
import org.scalajs.dom
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

object WalletMain {
  def main(args: Array[String]): Unit = {

    // initialize pulldowntorefresh.js
    PullToRefresh.init(js.Dictionary(
      "mainElement" -> "body"
    ))
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