package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.imports.jQuery
import com.livelygig.product.walletclient.facades.{EthereumJsUtils, HDKey, Mnemonic, VaultGaurd}
import com.livelygig.product.walletclient.handler.UpdateDefaultAccount
import com.livelygig.product.walletclient.router.ApplicationRouter.{AccountLoc, Loc}
import com.livelygig.product.walletclient.services.WalletCircuit
import diode.AnyAction._
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object ViewBackupPhrase {

  case class Props(router: RouterCtl[Loc])

  final case class State(phrase: String = Mnemonic.generateMnemonic(), isChecked: Boolean = false, showConfirmScreen: Boolean = false)

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      Callback.empty
    }

    def onBtnClicked(): react.Callback = {

      if (jQuery("#chkbSecure").is(":checked")) {
        t.modState(s => s.copy(isChecked = true, showConfirmScreen = true)).runNow()
        //        t.props.runNow().router.set(ConfirmBackupPhraseLoc).runNow()
      } else
        t.modState(s => s.copy(isChecked = true)).runNow()
      Callback.empty
    }

    def render(p: Props, s: State): VdomElement = {
      if (s.showConfirmScreen) {
        ConfirmBakupPhrase(ConfirmBakupPhrase.Props(p.router, s.phrase.split(" ")))
      }
      else
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
                    <.a(^.href := "#", ^.className := "close", VdomAttr("data-dismiss") := "alert", VdomAttr("aria-label") := "close", "×"),
                    <.strong("Please click on checkbox!!!"),
                  )
                } else {
                  <.div()
                }),
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

object ConfirmBakupPhrase {

  case class Props(router: RouterCtl[Loc], phraseWords: Seq[String])

  final case class State(phraseSelection: Seq[String],
                         phraseSelected: Seq[String],
                         isValidPhrase: Boolean, showLoader: Boolean,
                         showconfirmedScreen: Boolean = false)


  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      t.modState(s => s.copy(phraseSelection = t.props.runNow().phraseWords)).runNow()
      Callback.empty
    }

    def onBtnClicked(): react.Callback = {
      if (t.props.runNow().phraseWords.equals(t.state.runNow().phraseSelected.filter(_.nonEmpty)) /*true*/ ) {
        val password = WalletCircuit.zoomTo(_.user.userPassword).value
        VaultGaurd.decryptVault(password).map {
          e =>
            val mnemonicString = t.props.runNow().phraseWords.mkString(" ")
            val seed = Mnemonic.mnemonicToSeed(mnemonicString)
            val hdKey = HDKey.fromMasterSeed(seed)
            val child = hdKey.derive(s"${e.hdDerivePath}/0")
            WalletCircuit
              .dispatch(
                UpdateDefaultAccount(s"0x${EthereumJsUtils.privateToAddress(child.privateKey).toString("hex")}")
              )
            VaultGaurd.encryptWallet(password,
              e.copy(privateExtendedKey = hdKey.privateExtendedKey.toString(),
                mnemonicPhrase = mnemonicString)).map {
              _ =>
                t.modState(_.copy(showconfirmedScreen = true)).runNow()
            }

        }

        t.modState(s => s.copy(showLoader = true))

      } else {
        // todo add validator for mnemonic phrase
        t.modState(s => s.copy(isValidPhrase = false))
      }
    }

    def generateWordList(e: String): VdomElement = {

      <.li(^.cursor := "pointer", ^.onClick --> generateWordListSelected(e), e)
    }

    def generateWordListSelected(e: String) = {
      if (t.state.runNow().phraseSelected.contains(e)) {
        val selected = t.state.runNow().phraseSelection :+ e
        val diselect = t.state.runNow().phraseSelected.filterNot(_.equals(e))
        t.modState(s => s.copy(phraseSelected = diselect, phraseSelection = selected))
      }
      else if (t.state.runNow().phraseSelection.contains(e)) {
        val selected = t.state.runNow().phraseSelected :+ e
        val diselect = t.state.runNow().phraseSelection.filterNot(_.equals(e))
        t.modState(s => s.copy(phraseSelected = selected, phraseSelection = diselect))

      } else {
        Callback.empty
      }
    }


    def render(p: Props, s: State): VdomElement = {
      if (s.showconfirmedScreen) {
        ConfirmedBackupPhrase(ConfirmedBackupPhrase.Props(p.router))
      } else <.div(
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
          ^.className := "backupView-phrase",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12",
              <.h3("Confirm your back-up phrase"),
              <.div(
                ^.className := "backupPhrase-section",
                <.ul(
                  ^.id := "phrase-container ",
                  ^.className := "col-xs-12 backupPhrase-container")(s.phraseSelected.filter(_.nonEmpty) map generateWordList: _*)),
              if (!s.isValidPhrase) {
                <.div(^.className := "alert alert-danger alert-dismissible fade in",
                  <.a(^.href := "#", ^.className := "close", VdomAttr("data-dismiss") := "alert", VdomAttr("aria-label") := "close", "×"),
                  <.strong("Mnemonic-phrase not matched!!!")
                )
              } else
                <.div())),
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12 wordsList",
              <.ul(
                ^.id := "mnemonic-list")(s.phraseSelection.sorted map generateWordList: _*))),

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
                  ^.onClick --> onBtnClicked(),
                  "Next"))))))
    }
  }

  val component = ScalaComponent.builder[Props]("ConfirmBakupPhrase")
    .initialState(State(Seq(""), Seq(""), true, false))
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}

object ConfirmedBackupPhrase {

  case class Props(router: RouterCtl[Loc])

  final case class State()

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): react.Callback = {
      Callback.empty
    }

    def onBtnClicked(): react.Callback = {
      t.props.flatMap(e => e.router.set(AccountLoc))
    }

    def componentWillMount(props: Props): Callback = {
      Callback {
      }
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
                  <.p("If this app is deleted or you lose access to your account, your account can be recovered using the Backup Mnemonic Phrase you have just secured.")))),
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
                    ^.onClick --> onBtnClicked(),
                    "Next")))))))
    }
  }

  val component = ScalaComponent.builder[Props]("ConfirmBakupPhrase")
    .initialState(State())
    .renderBackend[Backend]
    .componentWillMount(scope => scope.backend.componentWillMount(scope.props))
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}
