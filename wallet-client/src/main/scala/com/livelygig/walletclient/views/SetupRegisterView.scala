package com.livelygig.walletclient.views

import com.livelygig.shared.models.wallet.Account
import com.livelygig.walletclient.facades.bootstrapvalidator.BootstrapValidator.bundle._
import com.livelygig.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.walletclient.facades.{ EthereumJsUtils, HDKey, Mnemonic, VaultGaurd }
import com.livelygig.walletclient.handler.{ AddAccount, SelectAddress }
import com.livelygig.walletclient.modals.SetupPasswordModal
import com.livelygig.walletclient.router.ApplicationRouter._
import com.livelygig.walletclient.services.WalletCircuit
import com.livelygig.walletclient.utils.Defaults
import diode.AnyAction._
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{ BackendScope, Callback, ReactEventFromInput, ScalaComponent }
import org.scalajs.jquery.JQueryEventObject

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object SetupRegisterView {

  case class Props(router: RouterCtl[Loc])

  final case class State(accountName: String = "", mnemonicPhrase: String = "",
    privateKey: String = "", keystoreText: String = Defaults.keyStoreText, regMode: String = "newId")

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      jQuery("#newId").prop("checked", true)
      Callback {
        jQuery("#setupForm").validator("update").on("submit", (e: JQueryEventObject) => {
          if (!e.isDefaultPrevented()) {
            e.preventDefault()
            onSubmitClicked()
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

    def createNewWallet(): Callback = {
      t.state.zip(t.props) >>= {
        case (_, props) =>
          if (WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo.accounts).value.isEmpty) {
            // create a default account in vault
            WalletCircuit.dispatch(AddAccount(Account(Defaults.defaultAccountPublicKey, t.state.runNow().accountName)))
            props.router.set(BackupAccountLoc)
          } // if user is logged in create an account and then navigate back to all account view
          else {
            Callback {
              VaultGaurd.decryptVault(WalletCircuit.zoomTo(_.user.userPassword).value).map {
                e =>
                  val hdKey = HDKey.fromExtendedKey(e.privateExtendedKey)
                  val child = hdKey.derive(s"${e.hdDerivePath}/${
                    WalletCircuit
                      .zoomTo(_.appRootModel.appModel.data.accountInfo.accounts).value.length
                  }")
                  WalletCircuit.dispatch(AddAccount(Account(s"0x${
                    EthereumJsUtils.privateToAddress(child.privateKey)
                      .toString("hex")
                  }", t.state.runNow().accountName)))
                  props.router.set(AllAccountsLoc)
              }
            }
          }
      }
    }

    def createNewWalletWithPassphrase() = {
      val password = WalletCircuit.zoomTo(_.user.userPassword).value
      t.state.zip(t.props) >>= {
        case (state, props) =>
          Callback {
            VaultGaurd.decryptVault(password).map {
              e =>
                val mnemonicString = state.mnemonicPhrase
                val seed = Mnemonic.mnemonicToSeed(mnemonicString)
                val hdKey = HDKey.fromMasterSeed(seed)
                val child = hdKey.derive(s"${e.hdDerivePath}/0")
                val address = s"0x${EthereumJsUtils.privateToAddress(child.privateKey).toString("hex")}"
                WalletCircuit.dispatch(AddAccount(Account(address, state.accountName)))
                WalletCircuit.dispatch(SelectAddress(address))
                VaultGaurd.encryptWallet(
                  password,
                  e.copy(
                    privateExtendedKey = hdKey.privateExtendedKey.toString(),
                    mnemonicPhrase = mnemonicString)).map {
                    _ =>
                      props.router.set(AccountLoc).runNow()
                  }

            }
          }
      }
    }

    def onSubmitClicked() = {
      val regMode = t.state.runNow().regMode
      val password = WalletCircuit.zoomTo(_.user.userPassword).value
      if (password.isEmpty) {
        t.props.flatMap(_.router.set(EnterPasswordLoc)).runNow()
      } else {
        regMode match {
          case "newId" => {
            createNewWallet().runNow()
          }
          case "passPhrase" => {
            // create an account with mnemonic phrase
            createNewWalletWithPassphrase().runNow()

          }
          case "createSharedWallet" => {
            t.props.flatMap(_.router.set(AddSharedWalletLoc)).runNow()
          }
          case "privateKey" | "keyStoreJson" | "keyStoreFile" | "sharedWallet" | "web3Provider" | "ledgerWallet" => {
            Callback.empty
          }
        }

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
                if (WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo.accounts).value.isEmpty) {
                  <.div(
                    ^.className := "radio",
                    <.label(
                      <.input(^.id := "passPhrase", ^.onClick --> onRegClicked("passPhrase"), ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#passphraseText", ^.value := "on", ^.`type` := "radio"),
                      "Mnemonic phrase"),
                    <.div(^.id := "passphraseText", ^.className := "collapse",
                      <.textarea(^.id := "passphraseTxt", ^.placeholder := "Enter 12 word mnemonic phrase", ^.rows := 3, ^.className := "form-control", ^.value := s.mnemonicPhrase, ^.onChange ==> updateMnemonicPhrase)))
                } else {
                  EmptyVdom
                },
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "privateKey", ^.onClick --> onRegClicked("privateKey"), ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#privateKeyText", ^.value := "on", ^.`type` := "radio"),
                    "Private key"),
                  <.div(^.id := "privateKeyText", ^.className := "collapse",
                    <.textarea(^.id := "privateKeyTexta", ^.placeholder := "Enter private key", ^.rows := 1, ^.className := "form-control"))),
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "keyStoreJson", ^.onClick --> onRegClicked("keyStoreJson"), ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#jsonText", ^.value := "on", ^.`type` := "radio"),
                    "Keystore text (UTC / JSON)"),
                  <.div(^.id := "jsonText", ^.className := "collapse",
                    <.textarea(^.id := "jsonTxt", ^.rows := 3, ^.placeholder := "Enter keystore text", ^.className := "form-control", ^.value := s.keystoreText, ^.onChange ==> updateKeystoreText))),
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "keyStoreFile", ^.onClick --> onRegClicked("keyStoreFile"), ^.name := "initialIdentifierExisting", ^.disabled := false, ^.value := "on", ^.`type` := "radio"),
                    "Keystore file (UTC / JSON)")),
                if (WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo.accounts).value.length.>(0)) {
                  <.div(
                    ^.className := "radio",
                    <.label(
                      <.input(^.id := "createSharedWallet", ^.onClick --> onRegClicked("createSharedWallet"), ^.name := "initialIdentifierExisting", ^.disabled := false, ^.value := "on", ^.`type` := "radio"),
                      "Create shared wallet"))
                } else {
                  EmptyVdom
                },
                <.div(
                  ^.className := "radio",
                  <.label(
                    <.input(^.id := "sharedWallet", ^.disabled := true, ^.onClick --> onRegClicked("sharedWallet"), ^.name := "initialIdentifierExisting", ^.disabled := false, VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#shrdWallet", ^.value := "on", ^.`type` := "radio"),
                    "Join shared wallet"),
                  <.div(^.id := "shrdWallet", ^.className := "collapse",
                    <.textarea(^.id := "sharedWalletKeyText", ^.placeholder := "Enter multisig contract", ^.rows := 1, ^.className := "form-control"))),
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
                  if (WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo.accounts).value.nonEmpty) {
                    <.button(^.id := "", ^.className := "btn btnDefault", "Cancel")
                  } else {
                    EmptyVdom
                  },
                  <.button(^.id := "setSelectedItem", ^.`type` := "submit", ^.className := "btn btnDefault", "Next")))))),
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
