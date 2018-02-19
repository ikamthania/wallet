package com.livelygig.product.walletclient.views.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, ^, _}

object AddTokenModal {

  case class Props()

  final case class State()

  object State {
    def init: State =
      State()

  }
  final class Backend(t: BackendScope[Props, Unit]) {
    def render(p: Props): VdomElement =
      <.div(^.className := "modal fade", ^.id := "addTokenModal", ^.role := "dialog", VdomAttr("aria-labelledby") := "exampleModalLabel", VdomAttr("aria-hidden") := "true",
        <.div(^.className := "modal-dialog", ^.role := "document",
          <.div(
            ^.className := "modal-content",
            <.div(
              ^.className := "modal-header",
              <.div(
                ^.className := "row",
                <.button(^.`type` := "button", ^.className := "close col-xs-1 col-xs-offset-5", VdomAttr("data-dismiss") := "modal", VdomAttr("aria-label") := "Close",
                  <.span(VdomAttr("aria-hidden") := "true", "×"))
              )
            ),
            <.div(
              ^.className := "modal-body",
              <.div(
                ^.className := "container",
                <.div(
                  ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12 inputContainer",
                  <.h4("Token name"),
                  <.input.text(
                    // ^.value := state.sharedWalletName,
                    // ^.onChange ==> onNameChange,
                    ^.className := "form-control ellipseText",
                    VdomAttr("data-error") := "Wallet name is required!",
                    ^.required := true
                  )
                ),
                <.div(
                  ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12 inputContainer",
                  <.h4("Token address"),
                  <.input.text(
                    // ^.value := state.sharedWalletName,
                    // ^.onChange ==> onNameChange,
                    ^.className := "form-control ellipseText",
                    VdomAttr("data-error") := "Wallet name is required!",
                    ^.required := true
                  )
                ),
                <.div(
                  ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12 inputContainer",
                  <.h4("Decimals"),
                  <.input.number(
                    // ^.value := state.sharedWalletName,
                    // ^.onChange ==> onNameChange,
                    ^.className := "form-control ellipseText",
                    VdomAttr("data-error") := "Wallet name is required!",
                    ^.required := true
                  )
                )
              ),
              <.div(
                ^.className := "modal-footer",
                <.button(^.`type` := "button", ^.className := "btn btnDefault", VdomAttr("data-dismiss") := "modal", "Apply")
              )
            )
          )))
  }

  val component = ScalaComponent.builder[Props]("AddTokenModal")
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}
