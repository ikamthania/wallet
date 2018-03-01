package com.livelygig.product.walletclient.facades

import org.querki.jquery._
import org.querki.jsext._

import scala.language.{ implicitConversions, reflectiveCalls }
import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

/**
 * Common Bootstrap components for scalajs-react
 */
@js.native
@JSGlobal("Bootstrap")
object Bootstrap extends js.Object {
  // shorthand for styles
  @js.native
  trait BootstrapJQuery extends JQuery {
    def modal(action: String): BootstrapJQuery = js.native
    def modal(options: js.Any): BootstrapJQuery = js.native
  }

  implicit def jq2bootstrap(jq: JQuery): BootstrapJQuery = jq.asInstanceOf[BootstrapJQuery]

}
