package com.livelygig.product.walletclient.views

import com.definitelyscala.bootstrap.ModalOptionsBackdropString
import com.livelygig.product.walletclient.modals.SetupPasswordModal
import com.livelygig.product.walletclient.router.ApplicationRouter.Loc
import com.livelygig.product.walletclient.utils.Defaults
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{ BackendScope, Callback, ReactEventFromInput, ScalaComponent }
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.product.walletclient.facades.bootstrapvalidator.BootstrapValidator.bundle._
import org.scalajs.jquery.JQueryEventObject

import scala.scalajs.js

object SetupRegisterView {

  case class Props(router: RouterCtl[Loc])

  final case class State(accountName: String = "", mnemonicPhrase: String = Defaults.keyStoreText,
    privateKey: String = "", keystoreText: String = Defaults.keyStoreText)

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      Callback {
        jQuery("#setupForm").validator("update").on("submit", (e: JQueryEventObject) => {
          if (!e.isDefaultPrevented()) {
            e.preventDefault()
            import com.livelygig.product.walletclient.facades.bootstrap.Bootstrap.bundle._
            val options = js.Object().asInstanceOf[ModalOptionsBackdropString]
            options.show = true
            options.keyboard = true
            options.backdrop = "static"
            jQuery("#setupPasswordModal").modal(options)
          } else {
            e.preventDefault()
          }

        })
      }
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
                <.input(^.id := "newId", ^.name := "initialIdentifier", ^.value := "on", ^.`type` := "radio"),
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
                    <.input(^.id := "passPhrase", ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#passphraseText", ^.value := "on", ^.`type` := "radio"),
                    "Mnemonic phrase"),
                  <.div(^.id := "passphraseText", ^.className := "collapse",
                    <.textarea(^.id := "passphraseTxt", ^.rows := 3, ^.className := "form-control", ^.value := s.mnemonicPhrase, ^.onChange ==> updateMnemonicPhrase))),
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "privateKey", ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#privateKeyText", ^.value := "on", ^.`type` := "radio"),
                    "Private key"),
                  <.div(^.id := "privateKeyText", ^.className := "collapse",
                    <.textarea(^.id := "privateKeyTexta", ^.rows := 1, ^.className := "form-control"))),
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "keyStoreJson", ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#jsonText", ^.value := "on", ^.`type` := "radio"),
                    "Keystore text (UTC / JSON)"),
                  <.div(^.id := "jsonText", ^.className := "collapse",
                    <.textarea(^.id := "jsonTxt", ^.rows := 3, ^.className := "form-control", ^.value := s.keystoreText, ^.onChange ==> updateKeystoreText))),
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "keyStoreFile", ^.name := "initialIdentifierExisting", ^.disabled := false, ^.value := "on", ^.`type` := "radio"),
                    "Keystore file (UTC / JSON)")),
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "sharedWallet", ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#shrdWallet", ^.value := "on", ^.`type` := "radio"),
                    "Join shared wallet"),
                  <.div(^.id := "shrdWallet", ^.className := "collapse",
                    <.textarea(^.id := "sharedWalletKeyText", ^.rows := 1, ^.className := "form-control"))),
                <.div(
                  ^.className := "radio notAlpha",
                  <.label(
                    <.input(^.id := "web3Provider", ^.name := "initialIdentifierExisting", ^.disabled := false, ^.value := "on", ^.`type` := "radio"),
                    "Web3 provider (Mist, Metamask, Parity, etc.)")),
                <.div(
                  ^.className := "radio notAlpha",
                  <.label(
                    <.input(^.id := "ledgerWallet", ^.name := "initialIdentifierExisting", ^.disabled := false, ^.value := "on", ^.`type` := "radio"),
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
                  <.button(^.id := "setSelectedItem", ^.`type` := "submit", ^.className := "btn btnDefault", "Next")))))),
        <.div(
          SetupPasswordModal.component(SetupPasswordModal.Props())))

    }
  }

  val component = ScalaComponent.builder[Props]("SetupRegisterView")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}
