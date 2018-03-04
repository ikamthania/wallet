package com.livelygig.product.walletclient

import com.karasiq.bootstrap.jquery.BootstrapJQueryContext
import com.livelygig.product.walletclient.facades.ReactFacade
import com.livelygig.product.walletclient.facades.bootstrapvalidator.ValidatorJQueryContext
import com.livelygig.product.walletclient.router.ApplicationRouter
import org.scalajs.dom

object WalletMain {

  def main(args: Array[String]): Unit = {
    ReactFacade.React
    ReactFacade.ReactDOM
    BootstrapJQueryContext.useNpmImports()
    ValidatorJQueryContext.useNpmImports()
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