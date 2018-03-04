package com.livelygig.product.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object ReactFacade {
  @JSImport("expose-loader?React!react", JSImport.Namespace)
  @js.native
  object React extends js.Any

  @JSImport("expose-loader?ReactDOM!react-dom", JSImport.Namespace)
  @js.native
  object ReactDOM extends js.Any
}
