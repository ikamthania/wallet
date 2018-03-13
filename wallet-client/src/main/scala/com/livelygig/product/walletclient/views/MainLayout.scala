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

  val sidebarNotRequiredFor = Seq(ConfirmedBackupPhraseLoc)
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(^.className := "wallet-main")(
      <.div(^.className := "wallet-inner container-fluid")(
        if (dom.window.localStorage.getItem("pubKey") != null && !sidebarNotRequiredFor.contains(r.page)) {
          // not the best place to dispatch login as it will be called every time the page renders.
          // however it is failsafe
          // todo think over proper dispatching of login action globally
          WalletCircuit.dispatch(LoginUser(true))
          Sidebar.component(Sidebar.Props(c, r))
        } else {
          EmptyVdom
        },
        r.render()))
  }

}
