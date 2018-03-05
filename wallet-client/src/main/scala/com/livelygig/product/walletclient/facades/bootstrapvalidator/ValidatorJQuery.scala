package com.livelygig.product.walletclient.facades.bootstrapvalidator

import org.scalajs.jquery.JQuery

import scala.scalajs.js

@scalajs.js.native
trait ValidatorJQuery extends JQuery {
  def validator(options: ValidatorOptions): ValidatorJQuery = js.native
  def validator(command: String): ValidatorJQuery = js.native
}