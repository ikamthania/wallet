package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.rootmodel.ERCTokenRootModel
import com.livelygig.product.walletclient.router.ApplicationRouter.Loc
import diode.data.Pot
import diode.react.ModelProxy
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ <, VdomAttr, VdomElement, ^, _ }
import japgolly.scalajs.react.{ BackendScope, Callback, ReactEventFromHtml, ScalaComponent }

object BackupAccount {

  case class Props(proxy: ModelProxy[Pot[ERCTokenRootModel]], router: RouterCtl[Loc], loc: String = "")

  final case class State()

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {

      Callback.empty
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
                <.h3("Back up account"))))),
        <.div(
          ^.className := "backupView-main scrollableArea",
          <.div(
            ^.className := "backupView-start",
            <.ul(
              <.li("On the next screen you'll see a 12 word mnemonic phrase that corresponds to your new account."),
              <.li("This is your account’s Backup Phrase."),
              <.li("Later, you can enter this phrase into Ubunda (or other wallets) to re-gain control over your account."),
              <.li("Make sure you are in a private place and not being watched."),
              <.li("Write down the exact Backup Phrase and keep it in a secure location."),
              <.li("Do not take a picture or screenshot, since that might be easily copied."),
              <.li("Anyone with access to your Backup Phrase can gain access to your account's funds."),
              <.li("If you fail to maintain a copy of this phrase or lose access to this wallet, you will permanently lose access to the funds in this account."))),
          <.div(
            ^.className := "backupView-generate",
            <.div(
              ^.className := "generate-section-content",
              <.p("Backup Phrase")),
            <.div(
              ^.className := "generate-section-middle",
              <.div(
                ^.className := "row mnemonicLangage",
                <.label(
                  ^.className := "col-lg-3 col-md-4 col-sm-4 col-xs-5",
                  "Display in:"),
                <.select(
                  ^.id := "mnemonicLanguageSlct",
                  ^.className := "col-lg-3 col-md-4 col-sm-4 col-xs-7",
                  <.option(
                    ^.value := "english",
                    "English"))),
              <.div(
                ^.className := "row",
                <.div(
                  ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                  ^.id := "mnemonic-phrase-container",
                  <.p(
                    ^.id := "mnemonic-phrase",
                    ^.className := "backup-phr-txt")),
                <.form(
                  ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                  <.div(
                    ^.className := "checkbox",
                    <.label(
                      <.input(
                        ^.`type` := "checkbox",
                        ^.id := "chkbSecure",
                        ^.value := "on"),
                      "I have copied and stored my backup phrase in a secure place."))))),
            <.div(
              ^.className := "generate-section-bottom",
              <.div(
                ^.className := "container btnDefault-container",
                <.div(
                  ^.className := "row",
                  <.div(
                    ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                    <.button(
                      ^.id := "btnNextGenerate",
                      ^.name := "btnNxt",
                      ^.`type` := "button",
                      ^.className := "btn btnDefault goupButton setdefault",
                      "Next")))))),
          <.div(
            ^.className := "backupView-phrase",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-xs-12",
                <.h3("Confirm your back-up phrase"),
                <.div(
                  ^.className := "row backupPhrase-section",
                  <.div(
                    ^.className := "col-xs-12 backupPhrase-container")))),
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-xs-12 wordsList",
                <.p(
                  ^.id := "errorMessage",
                  "Invalid phrase"),
                <.ul(
                  ^.id := "mnemonic-list")))),
          <.div(
            ^.className := "backupView-confirm",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-xs-12",
                <.div(
                  ^.className := "confirm-square",
                  <.i(
                    VdomAttr("aria-hidden") := "true",
                    ^.className := "fa fa-check-circle-o")))),
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-xs-12",
                <.div(
                  ^.className := "confirm-message",
                  <.h3("Your account is backed up."),
                  <.p("If this app is deleted or you lose access to your account, your account can be recovered using the Backup Mnemonic Phrase you have just secured."))))),
          <.div(
            ^.className := "container btnDefault-container",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                <.button(
                  ^.id := "btnNextStart",
                  ^.name := "btnNxt",
                  ^.`type` := "button",
                  VdomAttr("data-toggle") := "modal",
                  ^.className := "btn btnDefault goupButton setdefault",
                  "Next"))))))
    }
  }

  val component = ScalaComponent.builder[Props]("BackupAccount")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build
  def apply(props: Props) = component(props)
}
