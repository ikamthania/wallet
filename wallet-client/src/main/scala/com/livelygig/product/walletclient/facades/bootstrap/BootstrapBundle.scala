package com.livelygig.product.walletclient.facades.bootstrap

import com.livelygig.product.walletclient.facades.jquery.JQueryFacade

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

trait BootstrapBundle extends JQueryFacade with BootstrapJQueryImplicits {

}

object BootstrapBundle {
  object Imports {

    @js.native
    @JSImport("bootstrap", JSImport.Namespace)
    object Bootstrap extends js.Object
  }

  def useNpmImports(): Unit = {
    Imports.Bootstrap
  }
}