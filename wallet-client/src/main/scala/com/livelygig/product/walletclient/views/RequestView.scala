package com.livelygig.product.walletclient.views

// import com.livelygig.product.walletclient.facades.Bootstrap._
import com.definitelyscala.bootstrap.ModalOptionsBackdropString
import com.livelygig.product.walletclient.facades.QRCode
import com.livelygig.product.walletclient.modals.ShowQRCode
import com.livelygig.product.walletclient.router.ApplicationRouter.{ LandingLoc, Loc }
import com.livelygig.product.walletclient.services.WalletCircuit

import scala.scalajs.js
//import QRCode
import com.livelygig.product.walletclient.facades.bootstrap.Bootstrap.bundle._
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.jQuery
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ <, VdomAttr, ^, _ }
import japgolly.scalajs.react.{ Callback, _ }
import org.scalajs.dom

object RequestView {

  case class Props(router: RouterCtl[Loc], publicKey: String = WalletCircuit.zoom(_.appRootModel.appModel.data.accountInfo.selectedAddress).value)

  case class State(qrCode: String, showModal: Boolean = false)

  final class Backend(t: BackendScope[Props, State]) {

    def getQRCode() = Callback {
      val qrCode = new QRCode("3", "L")
      qrCode.addData(s"0x${WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo.selectedAddress)}")
      qrCode.make()
      val imgData = qrCode.createSvgTag()
      jQuery("#qrCode").prepend(imgData)
    }

    def toggleDropdownArrow(id: String) = Callback {
      jQuery(s"#$id").toggleClass("active")
    }

    def onBtnClicked(id: String)(e: ReactEventFromInput): react.Callback = {
      val el = jQuery("#lblReceivingAddress")
      el.select()
      dom.document.execCommand("Copy")
      Callback.empty
    }

    def onStateChange(value: String)(e: ReactEventFromInput): react.Callback = {
      val newValue = e.target.value
      value match {
        case "coinAmount" => {
          val newAlphaNumValue = toAlphaNumeric(newValue)
          jQuery("#coinTxtValue").value(newAlphaNumValue)
          changeEraseButtonVisibility(newAlphaNumValue, "coinTxtValue")
        }
        case "usdAmount" => {
          val newAlphaNumValue = toAlphaNumeric(newValue)
          jQuery("#usdTxtValue").value(newAlphaNumValue)
          changeEraseButtonVisibility(newAlphaNumValue, "usdTxtValue")
        }
      }
    }

    def toAlphaNumeric(value: String): String = {
      val numPattern = "^[0-9][\\.\\d]*(,\\d+)?$".r
      val isValid = numPattern.findFirstIn(value)
      if (isValid == None) {
        value.take(value.length - 1)
      } else {
        value
      }
    }

    def changeEraseButtonVisibility(value: String, inputId: String): Callback = {
      if (value != "") {
        jQuery("#" + inputId + " +  i").removeClass("eraseButtonHidden")
      } else {
        if (!jQuery("#" + inputId + " +  i").hasClass("eraseButtonHidden")) jQuery("#" + inputId + " +  i").addClass("eraseButtonHidden")
      }
      Callback.empty
    }

    def onEraseButtonClicked(id: String)(e: ReactEventFromInput): react.Callback = {
      jQuery("#" + id).value("")
      changeEraseButtonVisibility("", id)

      Callback.empty

    }

    def updateModal(): Callback = {
      val state = t.state.runNow()
      ShowQRCode(ShowQRCode.Props(state.qrCode))
      val options = js.Object().asInstanceOf[ModalOptionsBackdropString]
      options.show = true
      options.keyboard = true
      options.backdrop = "static"
      jQuery("#showQRCode").modal(options)
      Callback.empty
    }

    def onDoneClicked(): Callback = {
      t.props.runNow().router.set(LandingLoc).runNow()
      Callback.empty

    }

    def render(p: Props, s: State): VdomElement =
      <.div(^.id := "bodyWallet")(
        <.div(
          ^.className := "request-screen-main scrollableArea",
          <.div(
            ^.className := "request-screen-inner",
            <.div(
              ^.className := "request-section-scancode",
              <.h4(^.className := "section-heading", "request"),
              <.div(
                ^.className := "row",
                <.div(
                  ^.className := "col-lg-8 col-md-8 col-sm-8 col-xs-7 reqscancode",
                  <.div(^.id := "qrCode", ^.onClick --> updateModal())),
                <.div(
                  ^.className := "col-lg-4 col-md-4 col-sm-4 col-xs-5 reqscancode-text",
                  <.p("Have this code scanned by the sender. Click to show full-screen.")))),
            <.div(
              ^.className := "request-section-middle",
              <.h4(^.id := "advanced", ^.className := "section-heading", "advanced ", VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#advOpt", ^.onClick --> toggleDropdownArrow("advanced"),
                <.i(^.className := "fa fa-chevron-down", VdomAttr("aria-hidden") := "true")),
              <.div(
                ^.id := "advOpt",
                ^.className := "request-section-middle-inner collapse",
                <.div(
                  ^.className := "copy-secure-main",
                  <.button(
                    ^.id := "btnCopy",
                    ^.className := "btn btnDefault btn-request-icon", ^.onClick ==> onBtnClicked("btnCopy"),
                    <.i(^.className := "fa fa-files-o", VdomAttr("aria-hidden") := "true")),
                  <.button(
                    ^.className := "btn btnDefault btn-request-icon btnHidden",
                    <.i(^.className := "fa fa-share-alt", VdomAttr("aria-hidden") := "true")),
                  <.button(
                    ^.className := "btn btnDefault btn-request-icon btnHidden",
                    <.i(^.className := "fa fa-envelope", VdomAttr("aria-hidden") := "true"))),
                <.div(
                  ^.className := "scoller-main",
                  <.div(
                    ^.className := "req-amount-main notAlpha",
                    <.h4(
                      "Requested amount ",
                      <.span("(Optional)")),
                    <.div(
                      ^.className := "calculate",
                      <.div(
                        ^.className := "row-1",
                        <.input(^.id := "coinTxtValue", ^.`type` := "text", ^.`defaultValue` := "0.0453230",
                          ^.onChange ==> onStateChange("coinAmount")),
                        <.i(^.className := "fa fa-times", VdomAttr("aria-hidden") := "true", ^.onClick ==> onEraseButtonClicked("coinTxtValue")),
                        <.select(
                          ^.className := "form-control",
                          <.option("ETH"),
                          <.option("RHOC"))),
                      <.div(
                        ^.className := "row-1 aprox",
                        <.span("≈"),
                        <.input(^.id := "usdTxtValue", ^.`type` := "text", ^.`defaultValue` := "0.0453230",
                          ^.onChange ==> onStateChange("usdAmount")),
                        <.i(^.className := "fa fa-times", VdomAttr("aria-hidden") := "true", ^.onClick ==> onEraseButtonClicked("usdTxtValue")),
                        <.select(
                          ^.className := "form-control",
                          <.option("USD"))))),
                  <.div(
                    ^.className := "reaciving-add",
                    <.h4(
                      "Receiving address "),
                    <.input(
                      ^.id := "lblReceivingAddress",
                      ^.className := "ellipseText",
                      // ^.disabled := true,
                      ^.`defaultValue` := s"0x${p.publicKey}")),
                  <.div(
                    ^.className := "desscription notAlpha",
                    <.h4("Description"),
                    <.textarea(^.id := "txtDescription", ^.className := "form-control", ^.rows := 1)),
                  <.div(
                    ^.className := "expiresIn notAlpha",
                    <.h4("Expires in: "),
                    <.select(
                      ^.className := "form-control",
                      <.option("2 hours"),
                      <.option("7 hours"),
                      <.option("1 day"),
                      <.option("3 days"),
                      <.option("1 week"))),
                  <.div(
                    ^.className := "send-credentials notAlpha",
                    <.h4(
                      "Send credentials ",
                      <.span("(Patrick Yash Csintalan)")),
                    <.select(
                      ^.className := "form-control",
                      <.option("None"),
                      <.option("1"),
                      <.option("2"))))))),
          ShowQRCode.component(ShowQRCode.Props(p.publicKey))),
        <.div(
          ^.className := "container btnDefault-container container-NoBorder",
          <.h5(
            ^.className := "section-heading notAlpha",
            <.span("Status: "), "waiting for payment..."),
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.button(^.className := "btn btnDefault", ^.onClick --> onDoneClicked(), "Done")))))

  }

  val component = ScalaComponent.builder[Props]("RequestView")
    .initialState(State(""))
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.getQRCode())
    .build

  def apply(props: Props) = component(props)
}
