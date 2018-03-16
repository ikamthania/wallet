package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.imports.jQuery
import com.livelygig.product.walletclient.facades.{Mnemonic}
import com.livelygig.product.walletclient.handler.GetUserDetails
import com.livelygig.product.walletclient.router.ApplicationRouter.{ConfirmBackupPhraseLoc, Loc}
import com.livelygig.product.walletclient.services.WalletCircuit
import diode.AnyAction._
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}

object ViewBackupPhrase {

  case class Props(router: RouterCtl[Loc])

  final case class State(phrase: String= new Mnemonic().toString, isChecked:Boolean=false)
  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      Callback.empty
    }

    def onBtnClicked(): react.Callback = {
//      println(s"Extended Public key--->${HDKey.fromMasterSeed(t.state.runNow().phrase.toSeed(t.state.runNow().phrase.toString)).publicExtendedKey}")
//      println(s"Extended Public key--->${t.state.runNow().phrase.toString}")
//      println(s"Extended private key--->${HDKey.fromMasterSeed(t.state.runNow().phrase.toSeed(t.state.runNow().phrase.toString)).privateExtendedKey}")
      //todo convert pub n private key to string format from hex
//      val a=Wallet.fromExtendedPrivateKey(HDKey.fromMasterSeed(t.state.runNow().phrase.toSeed(t.state.runNow().phrase.toString)).privateExtendedKey).getPrivateKeyString().toString
//      println(s"Private key--->${new String(a)}")
//      println(s"Public key--->${Wallet.fromExtendedPrivateKey(HDKey.fromMasterSeed(t.state.runNow().phrase.toSeed(t.state.runNow().phrase.toString)).privateExtendedKey).getAddressString().toString}")

      if (jQuery("#chkbSecure").is(":checked")) {
        t.modState(s=>s.copy(isChecked = true)).runNow()
        t.props.runNow().router.set(ConfirmBackupPhraseLoc).runNow()
      } else
        t.modState(s=>s.copy(isChecked = true)).runNow()
      Callback.empty
    }

    def render(p: Props, s: State): VdomElement = {
      <.div(
        <.div(^.className := "wallet-inner-navigation",
<.div(^.className := "row",
<.div(^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
<.div(^.className := "wallet-information",
<.h2("Wallet"),
<.h3("Back up account")
)
)
)
),
        <.div(
        ^.className := "backupView-main scrollableArea",
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
                  ^.className := "backup-phr-txt", s.phrase.toString)),
              <.form(
                ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                <.div(
                  ^.className := "checkbox",
                  <.label(
                    <.input(
                      ^.`type` := "checkbox",
                      ^.id := "chkbSecure",
                      ^.value := "on"),
                    "I have copied and stored my backup phrase in a secure place.")))),
            if (s.isChecked) {
              <.div(^.className := "alert alert-danger alert-dismissible fade in",
                <.a(^.href := "#", ^.className := "close", VdomAttr("data-dismiss") := "alert", VdomAttr("aria-label") := "close","×"),
                <.strong("Please click on checkbox!!!"),
              )
            } else{ <.div()}),
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
                  ^.className := "btn btnDefault goupButton setdefault",
                  ^.onClick --> onBtnClicked(),
                  "Next")))))))

    }
  }

  val component = ScalaComponent.builder[Props]("ViewBackupPhrase")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}

