package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.components.Sidebar
import com.livelygig.product.walletclient.router.ApplicationRouter
import com.livelygig.product.walletclient.router.ApplicationRouter.Loc
import com.livelygig.product.walletclient.services.WalletCircuit
import japgolly.scalajs.react.extra.router.{ Resolution, RouterCtl }
import japgolly.scalajs.react.vdom.html_<^._

/**
 * Created by shubham.k on 17-03-2017.
 */
// scalastyle:off
object MainLayout {

  //  val sidebarNotRequiredFor = Seq(ApplicationRouter.LandingLoc, ApplicationRouter.)

  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(^.className := "wallet-main")(
      <.div(^.className := "wallet-inner container-fluid")(
        if (WalletCircuit.zoom(_.user.isloggedIn).value) {
          Sidebar.component(Sidebar.Props(c, r))
        } else {
          EmptyVdom
        },
        r.render()))
  }

}
