package com.livelygig.product.walletclient.facades.bootstrapvalidator

import com.karasiq.bootstrap.jquery.JQueryContext

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

trait ValidatorJQueryContext extends JQueryContext with ValidatorJQueryImplicits {

}

object ValidatorJQueryContext {
  object Imports {

    @js.native
    @JSImport("bootstrap-validator/dist/validator.js", JSImport.Namespace)
    object Validator extends js.Object
  }

  def useNpmImports(): Unit = {
    JQueryContext.useNpmImport()
    Imports.Validator
  }
}