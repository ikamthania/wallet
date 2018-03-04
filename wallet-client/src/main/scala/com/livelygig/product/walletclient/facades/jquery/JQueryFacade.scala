package com.livelygig.product.walletclient.facades.jquery

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import org.scalajs.jquery.JQueryStatic

trait JQueryFacade {
  def jQuery = JQueryFacade.jQuery
}

object JQueryFacade {
  var jQuery: JQueryStatic = org.scalajs.jquery.jQuery

  object imports {

    @js.native
    @JSImport("jquery", JSImport.Namespace) // scalastyle:off org.scalastyle.scalariform.ObjectNamesChecker
    object jQuery extends JQueryStatic

  }

  def useStatic(): Unit = {
    jQuery = org.scalajs.jquery.jQuery
  }

  def useNpmImport(): Unit = {
    jQuery = imports.jQuery
  }
}
