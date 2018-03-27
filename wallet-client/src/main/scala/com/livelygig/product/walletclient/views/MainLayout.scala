package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.components.HeaderComponent
import com.livelygig.product.walletclient.router.ApplicationRouter.{ BackupAccountLoc, Loc, SetupRegisterLoc, ViewBackupPhraseLoc }
import com.livelygig.product.walletclient.services.WalletCircuit
import com.livelygig.product.walletclient.utils.SessionKeys
import japgolly.scalajs.react.extra.router.{ Resolution, RouterCtl }
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

/**
 * Created by shubham.k on 17-03-2017.
 */
// scalastyle:off
object MainLayout {
  val sidebarNotRequiredFor = Seq(ViewBackupPhraseLoc, SetupRegisterLoc, BackupAccountLoc)
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(^.className := "wallet-main")(
      <.div(^.className := "wallet-inner container-fluid")(
        if (WalletCircuit.zoomTo(_.user.isloggedIn).value) {
          if (!sidebarNotRequiredFor.contains(r.page) || dom.window.sessionStorage.getItem(SessionKeys.isSessionVerified) != null) {
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
