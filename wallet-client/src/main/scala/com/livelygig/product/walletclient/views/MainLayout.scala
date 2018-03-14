package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.components.Sidebar
import com.livelygig.product.walletclient.handler.LoginUser
import com.livelygig.product.walletclient.router.ApplicationRouter.{ ConfirmedBackupPhraseLoc, Loc }
import com.livelygig.product.walletclient.services.WalletCircuit
import japgolly.scalajs.react.extra.router.{ Resolution, RouterCtl }
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import diode.AnyAction._

/**
 * Created by shubham.k on 17-03-2017.
 */
// scalastyle:off
object MainLayout {
  println(WalletCircuit.zoomTo(_.user.isloggedIn).value)
  val sidebarNotRequiredFor = Seq(ConfirmedBackupPhraseLoc)
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(^.className := "wallet-main")(
      <.div(^.className := "wallet-inner container-fluid")(
        if (WalletCircuit.zoomTo(_.user.isloggedIn).value || !sidebarNotRequiredFor.contains(r.page)) {
          Sidebar.component(Sidebar.Props(c, r))
        } else {
          EmptyVdom
        },
        r.render()))
  }

}
