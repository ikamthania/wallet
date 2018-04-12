package com.livelygig.walletclient.modals

import com.livelygig.walletclient.router.ApplicationRouter.{ Loc, LoginLoc }
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._

object SetupPasswordModal {

  final case class Props(router: RouterCtl[Loc])

  final class Backend(t: BackendScope[Props, Unit]) {

    def onSubmitClicked() = {
      t.props.flatMap(_.router.set(LoginLoc))
    }

    def render(p: Props): VdomElement =
      <.div(^.className := "modal fade", ^.id := "setupPasswordModal", ^.role := "dialog",
        <.div(
          ^.className := "modal-dialog",
          <.div(
            ^.className := "modal-content",
            <.div(
              ^.className := "modal-header",
              <.button(^.`type` := "button", ^.className := "close", VdomAttr("data-dismiss") := "modal", "×"),
              <.h4(^.className := "modal-title", "Confirm")),
            <.div(
              ^.className := "modal-body",
              <.input(^.id := "keyStorePassword", ^.className := "form-control", ^.`type` := "password", ^.placeholder := "Enter your password")),
            <.div(
              ^.className := "modal-footer",
              <.button(^.`type` := "button", ^.id := "setUpbtnNext", ^.className := "btn btn-default", VdomAttr("data-dismiss") := "modal", "Close")))))
  }

  val component = ScalaComponent.builder[Props]("SetupPasswordModal")
    .renderBackend[Backend]
    //.configure(Reusability.shouldComponentUpdate)
    .build
}
