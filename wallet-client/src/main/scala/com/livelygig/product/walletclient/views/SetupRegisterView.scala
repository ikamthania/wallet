package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.facades.Validator
import com.livelygig.product.walletclient.rootmodel.ERCTokenRootModel
import com.livelygig.product.walletclient.router.ApplicationRouter.Loc
import diode.data.Pot
import diode.react.ModelProxy
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{ BackendScope, Callback, ReactEventFromInput, ScalaComponent }

object SetupRegisterView {

  case class Props(router: RouterCtl[Loc])

  final case class State(password: String = "", setRegAccount: String = "")

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      Callback {
        // init validator
        Validator
      }

    }
    def submitForm(e: ReactEventFromInput): react.Callback = {
      e.preventDefault()
      //      t.modState(s => s.copy(closePopup = true))
      Callback.empty
    }

    def onPasswordStateChange(e: ReactEventFromInput): react.Callback = {
      var newValue = e.target.value
      t.modState(s => s.copy(password = newValue))
    }

    def render(p: Props, s: State): VdomElement = {
      <.form(^.id := "setupForm", VdomAttr("data-toggle") := "validator", ^.onSubmit ==> submitForm)(
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
                ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12 accountNameSection",
                <.h4("Account name"),
                <.input(^.id := "accountName", ^.`type` := "text"))),
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
                    <.textarea(^.id := "passphraseTxt", ^.rows := 3, ^.className := "form-control"))),
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
                    <.textarea(^.id := "jsonTxt", ^.rows := 3, ^.className := "form-control"))),
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
                  <.button(^.id := "setSelectedItem", VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#myModal", ^.`type` := "button", ^.className := "btn btnDefault", "Next")))))))
    }
  }

  val component = ScalaComponent.builder[Props]("SetupRegisterView")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}
