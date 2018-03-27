package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.components.HeaderComponent
import com.livelygig.product.walletclient.router.ApplicationRouter._
import com.livelygig.product.walletclient.services.WalletCircuit
import japgolly.scalajs.react.extra.router.{ Resolution, RouterCtl }
import japgolly.scalajs.react.vdom.html_<^._

/**
 * Created by shubham.k on 17-03-2017.
 */
// scalastyle:off
object MainLayout {
  val sidebarNotRequiredFor = Seq(ViewBackupPhraseLoc, SetupRegisterLoc, BackupAccountLoc)
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(^.className := "wallet-main")(
      <.div(^.className := "wallet-inner container-fluid")(
        if (WalletCircuit.zoomTo(_.user.isloggedIn).value || r.page == SendLoc) {
          if (!sidebarNotRequiredFor.contains(r.page)) {
            HeaderComponent.component(HeaderComponent.Props(c, r))
          } else {
            EmptyVdom
          }
        } else {
          EmptyVdom
        },
        r.render()))
  }

}
