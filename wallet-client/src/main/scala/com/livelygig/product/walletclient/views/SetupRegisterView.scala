package com.livelygig.product.walletclient.views

import com.definitelyscala.bootstrap.ModalOptionsBackdropString
import com.livelygig.product.walletclient.facades.bootstrapvalidator.BootstrapValidator.bundle._
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.product.walletclient.modals.SetupPasswordModal
import com.livelygig.product.walletclient.router.ApplicationRouter.{ BackupAccountLoc, Loc, LoginLoc }
import com.livelygig.product.walletclient.utils.Defaults
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{ BackendScope, Callback, ReactEventFromInput, ScalaComponent }
import org.scalajs.dom
import org.scalajs.jquery.JQueryEventObject

import scala.scalajs.js

object SetupRegisterView {

  case class Props(router: RouterCtl[Loc])

  final case class State(accountName: String = "", mnemonicPhrase: String = Defaults.keyStoreText,
    privateKey: String = "", keystoreText: String = Defaults.keyStoreText, regMode: String = "newId")

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      jQuery("#newId").prop("checked", true)
      Callback {
        jQuery("#setupForm").validator("update").on("submit", (e: JQueryEventObject) => {
          if (!e.isDefaultPrevented()) {
            e.preventDefault()

          } else {
            e.preventDefault()
          }

        })
      }
    }

    def onRegClicked(regItem: String): react.Callback = {
      if (regItem != "newId")
        jQuery("#newId").prop("checked", false)
      //      dom.document.getElementById("newId").che = false
      t.modState(_.copy(regMode = regItem))
    }

    def onSubmitClicked(): react.Callback = {
      val state = t.state.runNow().regMode
      //      println(s"In onSubmitbuttonClicked  ${state}")
      state match {
        case "newId" => {
          //          println("In backupAccountClicked")
          t.props.flatMap(_.router.set(BackupAccountLoc))
        }
        case "passPhrase" => {
          import com.livelygig.product.walletclient.facades.bootstrap.Bootstrap.bundle._
          val options = js.Object().asInstanceOf[ModalOptionsBackdropString]
          options.show = true
          options.keyboard = true
          options.backdrop = "static"
          jQuery("#setupPasswordModal").modal(options)
          Callback.empty
        }
        case "privateKey" => { t.props.flatMap(_.router.set(LoginLoc)) }
        case "keyStoreJson" => {
          import com.livelygig.product.walletclient.facades.bootstrap.Bootstrap.bundle._
          val options = js.Object().asInstanceOf[ModalOptionsBackdropString]
          options.show = true
          options.keyboard = true
          options.backdrop = "static"
          jQuery("#setupPasswordModal").modal(options)
          Callback.empty
        }
        case "keyStoreFile" => { t.props.flatMap(_.router.set(LoginLoc)) }
        case "sharedWallet" => { t.props.flatMap(_.router.set(LoginLoc)) }
        case "web3Provider" => { t.props.flatMap(_.router.set(LoginLoc)) }
        case "ledgerWallet" => { t.props.flatMap(_.router.set(LoginLoc)) }
      }
      //      Callback.empty
      //      t.modState(_.copy(regMode =))
    }

    def updateAccountName(e: ReactEventFromInput): react.Callback = {
      val newValue = e.target.value
      t.modState(_.copy(accountName = newValue))
    }

    def updateMnemonicPhrase(e: ReactEventFromInput): react.Callback = {
      val newValue = e.target.value
      t.modState(_.copy(mnemonicPhrase = newValue))
    }

    def updateKeystoreText(e: ReactEventFromInput): react.Callback = {
      val newValue = e.target.value
      t.modState(_.copy(keystoreText = newValue))
    }

    def updatePrivateKey(e: ReactEventFromInput): react.Callback = {
      val newValue = e.target.value
      t.modState(_.copy(privateKey = newValue))
    }

    def render(p: Props, s: State): VdomElement = {
      <.form(^.id := "setupForm", VdomAttr("data-toggle") := "validator")(
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
          ^.className := "initialSetup-main scrollableArea",
          <.div(
            ^.className := "initialSetup-inner",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12 accountNameSection form-group has-feedback",
                <.h4("Account name"),
                <.input(^.value := s.accountName, ^.onChange ==> updateAccountName, ^.`type` := "text", ^.required := true),
                <.h4(^.className := "help-block with-errors"))),
            <.div(
              ^.className := "radio radio-top",
              <.label(
                <.input(^.id := "newId", ^.onChange --> onRegClicked("newId"), ^.name := "initialIdentifier", ^.value := "on", ^.`type` := "radio" /*, VdomAttr("checked") := true*/ ),
                "Create a new account")),
            <.button(^.id := "btnAdvOpt", ^.`type` := "button", VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#advOpt", ^.className := "btn btnAdvOpt", "Advanced",
              <.i(VdomAttr("aria-hidden") := "true", ^.className := "fa fa-chevron-down")),
            <.div(^.id := "advOpt", ^.className := "collapse",
              <.div(
                ^.className := "radio",
                <.label(
                  <.input(^.id := "existingId", ^.name := "initialIdentifier", ^.value := "on", ^.`type` := "radio"),
                  "Use existing account:")),
              <.div(
                ^.className := "existingIdOptions",
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "passPhrase", ^.onClick --> onRegClicked("passPhrase"), ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#passphraseText", ^.value := "on", ^.`type` := "radio"),
                    "Mnemonic phrase"),
                  <.div(^.id := "passphraseText", ^.className := "collapse",
                    <.textarea(^.id := "passphraseTxt", ^.rows := 3, ^.className := "form-control", ^.value := s.mnemonicPhrase, ^.onChange ==> updateMnemonicPhrase))),
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "privateKey", ^.onClick --> onRegClicked("privateKey"), ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#privateKeyText", ^.value := "on", ^.`type` := "radio"),
                    "Private key"),
                  <.div(^.id := "privateKeyText", ^.className := "collapse",
                    <.textarea(^.id := "privateKeyTexta", ^.rows := 1, ^.className := "form-control"))),
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "keyStoreJson", ^.onClick --> onRegClicked("keyStoreJson"), ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#jsonText", ^.value := "on", ^.`type` := "radio"),
                    "Keystore text (UTC / JSON)"),
                  <.div(^.id := "jsonText", ^.className := "collapse",
                    <.textarea(^.id := "jsonTxt", ^.rows := 3, ^.className := "form-control", ^.value := s.keystoreText, ^.onChange ==> updateKeystoreText))),
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "keyStoreFile", ^.onClick --> onRegClicked("keyStoreFile"), ^.name := "initialIdentifierExisting", ^.disabled := false, ^.value := "on", ^.`type` := "radio"),
                    "Keystore file (UTC / JSON)")),
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "sharedWallet", ^.onClick --> onRegClicked("sharedWallet"), ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#shrdWallet", ^.value := "on", ^.`type` := "radio"),
                    "Join shared wallet"),
                  <.div(^.id := "shrdWallet", ^.className := "collapse",
                    <.textarea(^.id := "sharedWalletKeyText", ^.rows := 1, ^.className := "form-control"))),
                <.div(
                  ^.className := "radio notAlpha",
                  <.label(
                    <.input(^.id := "web3Provider", ^.onClick --> onRegClicked("web3Provider"), ^.name := "initialIdentifierExisting", ^.disabled := false, ^.value := "on", ^.`type` := "radio"),
                    "Web3 provider (Mist, Metamask, Parity, etc.)")),
                <.div(
                  ^.className := "radio notAlpha",
                  <.label(
                    <.input(^.id := "ledgerWallet", ^.onClick --> onRegClicked("ledgerWallet"), ^.name := "initialIdentifierExisting", ^.disabled := false, ^.value := "on", ^.`type` := "radio"),
                    "Ledger hardware wallet"))),
              <.div(
                ^.className := "radio notAlpha",
                <.label(
                  <.input(^.id := "restoreApp", ^.name := "initialIdentifier", ^.value := "on", ^.`type` := "radio"),
                  "Restore entire application from backup"))),
            <.p(^.id := "errorMessage"),
            <.div(
              ^.className := "container btnDefault-container",
              <.div(
                ^.className := "row",
                <.div(
                  ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                  <.button(^.id := "setSelectedItem", ^.`type` := "submit", ^.onClick --> onSubmitClicked(), ^.className := "btn btnDefault", "Next")))))),
        <.div(
          SetupPasswordModal.component(SetupPasswordModal.Props(p.router))))

    }
  }

  val component = ScalaComponent.builder[Props]("SetupRegisterView")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}
