package com.livelygig.walletclient.modals

import com.livelygig.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.walletclient.utils.AppUtils
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }

object ShowQRCode {

  case class Props()

  final case class State()

  final class Backend(t: BackendScope[Props, State]) {

    def getImgSrc(): Callback = Callback {
      val imgData = AppUtils.getQRCode().createImgTag(8)
      jQuery("#qrCodeinmodal").prepend(imgData)
    }

    def render(p: Props, s: State): VdomElement =
      <.div(
        <.div(^.className := "modal fade", ^.id := "showQRCode", ^.role := "dialog", ^.marginTop := "-70%", VdomAttr("aria-labelledby") := "exampleModalLabel", VdomAttr("aria-hidden") := "true",
          <.div(^.className := "modal-dialog", ^.role := "document",
            <.div(
              ^.className := "modal-content",
              <.div(
                ^.className := "modal-header",
                <.div(
                  ^.className := "row",

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
                      <.div(^.id := "qrCodeinmodal", ^.className := "qr-background")))),
                <.div(
                  ^.className := "modal-footer",
                  <.button(^.`type` := "button", ^.className := "btn btnDefault", VdomAttr("data-dismiss") := "modal", "Close")))))))
  }

  val component = ScalaComponent.builder[Props]("ShowQRCode")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.getImgSrc())
    .build
  def apply(props: Props) = component(props)
}

