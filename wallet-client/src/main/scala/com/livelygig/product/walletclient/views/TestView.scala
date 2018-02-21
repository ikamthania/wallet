package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.views.facades.{Blockies, Pseudoloc, /*QRCode,*/ Toastr}
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import org.querki.jquery.$
import org.scalajs.dom

import scala.scalajs.js

object TestView {
  val abc: Array[Byte] = "[B@6b141e78".getBytes()

  //  javax.xml.bind.DatatypeConverter.printBase64Binary(abc)

  Toastr.options.timeOut = 500; // How long the toast will display without user interaction
  Toastr.options.extendedTimeOut = 60; // How long the toast will display after a user hovers over it
  Toastr.options.closeButton = true;
  Toastr.options.preventDuplicates = true

  case class Props()

  final case class State()

  object State {
    def init: State =
      State()
  }

  final class Backend(t: BackendScope[Props, Unit]) {
    def tryOut(): Callback = {
      val str = Blockies.create(js.Dynamic.literal(size = 15, scale = 3, seed = "0x7cB57B5A97eAbe94205C07890BE4c1aD31E486A8"))
      $("#blockies").append(str)
      var res = Pseudoloc.str("A test string with a %token%.")
      println(s"Result ===> ${res}")

      /*val qrCode = new QRCode(dom.document.getElementById("qrCode"), "test")
      val imgData = qrCode.makeCode("0x988d9344ba15fb6728b2fee8703b8505f4ed17a9")*/
      //      qrCode.clear()

      Callback.empty
    }

    def onToastrClick(toastrType: String): Callback = {
      if (toastrType == "error")
        Toastr.error("Please try once again. ", Some("Oops cant open it...!"))
      else if (toastrType == "success")
        Toastr.success("Successful ", Some("Its going to work now...!"))
      Callback.empty
    }

    def render(p: Props): VdomElement = <.div()(
      <.button(^.`type` := "button", ^.className := "btn btnDefault", ^.onClick --> tryOut(), "Get Blockies"),
      <.div(^.id := "blockies")(),
      <.div(^.id := "qrCode")(),
      <.button(^.`type` := "button", ^.className := "btn btnDefault ", ^.onClick --> onToastrClick("error"),
        "Error"),
      //      <.img(^.src := "data:image/jpg;base64,"),
      <.button(^.`type` := "button", ^.className := "btn btnDefault ", ^.onClick --> onToastrClick("success"),
        "Success"),
      <.a(^.`type` := "button", ^.href := "/home/ubuntu/work/livelygig/mobile-app/android/app-debug.apk", ^.download := true, ^.className := "btn btnDefault  my-apk", "dowmload apk form"), <.img(^.src := "./assets/images/processing-img.svg", ^.className := "loading-img")
    )
  }

  val component = ScalaComponent.builder[Props]("TestView")
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)

}
