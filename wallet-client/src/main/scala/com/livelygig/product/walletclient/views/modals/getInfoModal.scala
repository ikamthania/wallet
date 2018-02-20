package com.livelygig.product.walletclient.views.modals

import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }
import japgolly.scalajs.react.{ Callback, _ }

object getInfoModal {

  case class Props()

  final case class State()

  final class Backend(t: BackendScope[Props, Unit]) {

    def componentDidMount(): Callback = {

      Callback.empty
    }

    def render(p: Props): VdomElement =
      <.div(
        <.div(^.className := "modal fade", ^.id := "getInfoModal", ^.role := "dialog", VdomAttr("aria-labelledby") := "exampleModalLabel", VdomAttr("aria-hidden") := "true",
          <.div(^.className := "modal-dialog", ^.role := "document",
            <.div(
              ^.className := "modal-content",
              <.div(
                ^.className := "modal-header",
                <.div(
                  ^.className := "row",
                  <.h5(^.className := "modal-title col-xs-6", ^.id := "exampleModalLabel", "Confirm"),
                  <.button(^.`type` := "button", ^.className := "close col-xs-1 col-xs-offset-5", VdomAttr("data-dismiss") := "modal", VdomAttr("aria-label") := "Close",
                    <.span(VdomAttr("aria-hidden") := "true", "×")))),
              <.div(
                ^.className := "modal-body",
                <.div(
                  ^.className := "container",
                  <.div(
                    ^.className := "row",
                    <.div(
                      ^.className := "confirmContainer",
                      <.div(
                        ^.className := "messageContainer",
                        <.h5("Please confirm the send of 1234.003 ETH to 0x12345... .")),
                      <.div(
                        <.input(^.id := "iptJsonFile", ^.name := "jsonFile", ^.`type` := "file")),
                      <.div(
                        ^.className := "passwordContainer",
                        <.p("To confirm, enter password for your address 0xabcdef..."),
                        <.input(^.`type` := "password", ^.className := ""),
                        <.i(^.className := "fa fa-lock", VdomAttr("aria-hidden") := "true", VdomAttr("data-dismiss") := "modal"))))),
                <.div(
                  ^.className := "modal-footer",
                  <.button(^.`type` := "button", ^.className := "btn btnDefault", VdomAttr("data-dismiss") := "modal", "Confirm")))))))

  }

  val component = ScalaComponent.builder[Props]("getInfoModal")
    .renderBackend[Backend]
    .componentWillMount(scope => scope.backend.componentDidMount())
    //.configure(Reusability.shouldComponentUpdate)
    .build
  def apply(props: Props) = component(props)
}
