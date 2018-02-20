package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.router.ApplicationRouter.Loc
import japgolly.scalajs.react.extra.router.{ Resolution, RouterCtl }
import japgolly.scalajs.react.vdom.html_<^._

/**
 * Created by shubham.k on 17-03-2017.
 */
// scalastyle:off
object MainLayout {

  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(^.className := "wallet-main")(
      <.div(^.className := "wallet-inner container-fluid")(
        Sidebar.component(Sidebar.Props(c, r)),
        r.render()))
  }

}
