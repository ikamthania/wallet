package com.livelygig.walletclient.views

import com.livelygig.walletclient.components.HeaderComponent
import com.livelygig.walletclient.router.ApplicationRouter._
import com.livelygig.walletclient.services.WalletCircuit
import japgolly.scalajs.react.extra.router.{ Resolution, RouterCtl }
import japgolly.scalajs.react.vdom.html_<^._

/**
 * Created by shubham.k on 17-03-2017.
 */
// scalastyle:off
object MainLayout {
  val sidebarNotRequiredFor = Seq(ViewBackupPhraseLoc, SetupRegisterLoc, BackupAccountLoc)
  val sidebarRequiredFor = Seq(SendLoc, PopulateQRCodeLoc)
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    val isSidebarRequired = r.page match {
      case PopulateQRCodeLoc(to) => true
      case _ => false
    }
    <.div(^.className := "wallet-main")(
      <.div(^.className := "wallet-inner container-fluid")(
        if (WalletCircuit.zoomTo(_.user.isloggedIn).value || isSidebarRequired) {
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
