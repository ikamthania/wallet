package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.rootmodel.ERCTokenRootModel
import com.livelygig.product.walletclient.router.ApplicationRouter.{ BackupAccountLoc, Loc }
import diode.data.Pot
import diode.react.ModelProxy
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ <, VdomAttr, VdomElement, ^, _ }
import japgolly.scalajs.react.{ BackendScope, Callback, ReactEventFromHtml, ReactEventFromInput, ScalaComponent }
import org.querki.jquery.$
import org.scalajs.dom

object SetupView {

  case class Props(proxy: ModelProxy[Pot[ERCTokenRootModel]], router: RouterCtl[Loc], loc: String = "")

  final case class State(password: String = "", setRegAccount: String = "")

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {

      Callback.empty
    }

    def onStateChange(value: String)(e: ReactEventFromInput): react.Callback = {
      var newValue = e.target.value
      value match {
        case "password" => {
          t.modState(s => s.copy(password = newValue))
        }
      }
    }

    def setSelectedItem(e: ReactEventFromHtml): react.Callback = Callback {
      val accpuntName = $("#accountName").`val`()

      if ($("newId").`val`() == true) {
        println("newId")
        t.modState(s => s.copy(setRegAccount = "newId"))
      } else if ($("keyStoreFile").`val`() == true) {
        println("keyStoreFile")
        t.modState(s => s.copy(setRegAccount = "keyStoreFile"))
      } else if ($("keyStoreJson").`val`() == true) {
        println("keyStoreJson")
        t.modState(s => s.copy(setRegAccount = "keyStoreJson"))
      } else if ($("passPhrase").`val`() == true) {
        println("passPhrase")
        t.modState(s => s.copy(setRegAccount = "passPhrase"))
      } else if ($("privateKey").`val`() == true) {
        println("privateKey")
        t.modState(s => s.copy(setRegAccount = "privateKey"))
      } else {
        println("")
      }

    }

    def onStateChange()(e: ReactEventFromInput): react.Callback = {
      var getVal = t.state.runNow().setRegAccount
      getVal match {
        case "newId" => { t.props.runNow().router.set(BackupAccountLoc).runNow() }
        case "keyStoreFile" => {}
        case "keyStoreJson" => {}
        case "passPhrase" => {}
        case "privateKey" => {}
      }

      Callback.empty
    }

    def onNextClicked(e: ReactEventFromHtml): react.Callback = Callback {
      val state = t.state.runNow()
      println("adhaskjdhadks" + state.password)
      dom.window.localStorage.setItem("ubunda-psswd", state.password)
      $(".initialSetup-password").hide()
      $(".initialSetup-main").show()
    }

    def onItemClicked(e: ReactEventFromHtml): react.Callback = {

      Callback.empty
    }

    def render(p: Props, s: State): VdomElement = {
      <.div(
        <.div(
          ^.className := "wallet-inner-navigation",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.div(
                ^.className := "wallet-information",
                <.h2("Wallet"),
                <.h3("Create new account"))))),
        <.div(
          ^.className := "initialSetup-password",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12 col-sm-12 col-md-12 col-lg-12",
              <.h4("Please create the password that will be used to unlock the ubunda application. This password will also be used to locally encrypt the application. Your password must be at least 4 characters long."))),
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12 col-sm-12 col-md-12 col-lg-12",
              <.input(^.id := "txtPassword", ^.className := "form-control", ^.defaultValue := s.password, ^.onChange ==> onStateChange("password")), ^.`type` := "password", ^.placeholder := "Enter your password"),
            <.input(^.id := "txtPasswordConfirm", ^.className := "form-control", ^.`type` := "password", ^.placeholder := "Confirm your password"),
            <.h4(^.id := "messageError"))),
        <.div(
          ^.className := "container btnDefault-container container-NoBorder",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.button(^.id := "btnSetPassword", ^.`type` := "button", ^.className := "btn btnDefault goupButton", ^.onClick ==> onNextClicked, "Next")))),
        <.div(
          ^.className := "initialSetup-main scrollableArea",

          <.div(
            ^.className := "row",
            <.div(^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12 accountNameSection")(
              <.h4(""),
              <.input(^.id := "accountName", ^.`type` := "text"))),
          <.div(
            ^.className := "radio radio-top",
            <.label(
              <.input(^.`type` := "radio", ^.id := "newId", ^.name := "initialIdentifier", ^.value := "on", ^.checked := true),
              "Create a new account")),
          <.button(^.id := "btnAdvOpt", ^.`type` := "button", VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#advOpt", ^.className := "btn btnAdvOpt", "Advanced",
            <.i(VdomAttr("aria-hidden") := "true", ^.className := "fa fa-chevron-down")),
          <.div(^.id := "advOpt", ^.className := "collapse",
            <.div(
              ^.className := "radio",
              <.label(
                <.input(^.`type` := "radio", ^.id := "existingId", ^.name := "initialIdentifier", ^.value := "on"),
                "Use existing account:")),
            <.div(
              ^.className := "existingIdOptions",
              <.div(
                ^.className := "radio",
                <.label(
                  <.input(^.id := "passPhrase", ^.`type` := "radio", ^.name := "initialIdentifierExisting", VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#passphraseText", ^.value := "on"),
                  "Mnemonic phrase"),
                <.div(^.id := "passphraseText", ^.className := "collapse",
                  <.textarea(^.id := "passphraseTxt", ^.rows := 3, ^.className := "form-control"))),
              <.div(
                ^.className := "radio",
                <.label(
                  <.input(^.id := "privateKey", ^.`type` := "radio", ^.name := "initialIdentifierExisting", VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#privateKeyText", ^.value := "on"),
                  "Private key"),
                <.div(^.id := "privateKeyText", ^.className := "collapse",
                  <.textarea(^.id := "privateKeyTexta", ^.rows := 1, ^.className := "form-control"))),
              <.div(
                ^.className := "radio",
                <.label(
                  <.input(^.id := "keyStoreJson", ^.`type` := "radio", ^.name := "initialIdentifierExisting", VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#jsonText", ^.value := "on"),
                  "Keystore text (UTC / JSON)"),
                <.div(^.id := "jsonText", ^.className := "collapse",
                  <.textarea(^.id := "jsonTxt", ^.rows := 3, ^.className := "form-control"))),
              <.div(
                ^.className := "radio",
                <.label(
                  <.input(^.id := "keyStoreFile", ^.`type` := "radio", ^.name := "initialIdentifierExisting", ^.value := "on"),
                  "Keystore file (UTC / JSON)")),
              <.div(
                ^.className := "radio",
                <.label(
                  <.input(^.id := "sharedWallet", ^.`type` := "radio", ^.name := "initialIdentifierExisting", VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#shrdWallet", ^.value := "on"),
                  "Join shared wallet"),
                <.div(^.id := "shrdWallet", ^.className := "collapse",
                  <.textarea(^.id := "sharedWalletKeyText", ^.rows := 1, ^.className := "form-control"))),
              <.div(
                ^.className := "radio notAlpha",
                <.label(
                  <.input(^.id := "web3Provider", ^.`type` := "radio", ^.name := "initialIdentifierExisting", ^.value := "on"),
                  "Web3 provider (Mist, Metamask, Parity, etc.)")),
              <.div(
                ^.className := "radio notAlpha",
                <.label(
                  <.input(^.id := "ledgerWallet", ^.`type` := "radio", ^.name := "initialIdentifierExisting", ^.value := "on"),
                  "Ledger hardware wallet"))),
            <.div(
              ^.className := "radio notAlpha",
              <.label(
                <.input(^.`type` := "radio", ^.id := "restoreApp", ^.name := "initialIdentifier", ^.value := "on"),
                "Restore entire application from backup"))),
          <.p(^.id := "errorMessage"),
          <.div(
            ^.className := "container btnDefault-container",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                <.button(^.id := "setSelectedItem", VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#myModal", ^.`type` := "button", ^.onClick ==> setSelectedItem, ^.className := "btn btnDefault", "Next"))))),

        <.div(^.className := "modal fade", ^.id := "myModal", ^.role := "dialog",
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
                <.button(^.`type` := "button", ^.id := "setUpbtnNext", ^.className := "btn btn-default", VdomAttr("data-dismiss") := "modal", "Close"))))))
    }
  }

  val component = ScalaComponent.builder[Props]("AccountView")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build
  def apply(props: Props) = component(props)
}
