package com.livelygig.product.walletclient.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object SetupPasswordModal {

  final case class Props() {
  }

  final class Backend($: BackendScope[Props, Unit]) {
    def render(p: Props): VdomElement =
      <.div(^.className := "modal fade", ^.id := "setupPasswordModal", ^.role := "dialog",
        <.div(
          ^.className := "modal-dialog",
          <.div(
            ^.className := "modal-content",
            <.div(
              ^.className := "modal-header",
              <.button(^.`type` := "button", ^.className := "close", VdomAttr("data-dismiss") := "modal", "×"),
              <.h4(^.className := "modal-title", "Modal Header")),
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
