package com.livelygig.product.walletclient.modals

//import QRCode
import com.livelygig.product.walletclient.facades.QRCode
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.jQuery
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }

object ShowQRCode {

  case class Props(imgsrc: String = "")

  final case class State()

  final class Backend(t: BackendScope[Props, State]) {

    def getImgSrc(): Callback = {
      val props = t.props.runNow()
      val qrCode = new QRCode("3", "L")
      qrCode.addData(props.imgsrc)
      qrCode.make()
      val imgData = qrCode.createImgTag()
      jQuery("#qrCodeinmodal").prepend(imgData)

      Callback.empty
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

