package com.livelygig.walletclient.facades.bootstrapvalidator

import com.livelygig.walletclient.facades.jquery.JQueryFacade

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

trait ValidatorBundle extends JQueryFacade with ValidatorJQueryImplicits {

}

object ValidatorBundle {
  object Imports {

    @js.native
    @JSImport("bootstrap-validator/dist/validator.js", JSImport.Namespace)
    object Validator extends js.Object
  }

  def useNpmImports(): Unit = {
    Imports.Validator
  }
}