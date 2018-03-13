package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.facades.{HDKey, Mnemonic, Wallet}
import com.livelygig.product.walletclient.router.ApplicationRouter.{ConfirmedBackupPhraseLoc, Loc}
import com.livelygig.product.walletclient.services.WalletCircuit
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import org.scalajs.dom
import scala.util.Random

object ConfirmBakupPhrase {

  case class Props(router: RouterCtl[Loc],phraseWords :Seq[String]= WalletCircuit.zoom(_.user.userDetails.phrase).value)

  final case class State(phraseSelection: Seq[String], phraseSelected: Seq[String] , isValidPhrase: Boolean = false)


  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      t.modState(s => s.copy(phraseSelection = t.props.runNow().phraseWords)).runNow()
      Callback.empty
    }


    def onBtnClicked(): react.Callback = {
      if (t.props.runNow().phraseWords.equals(t.state.runNow().phraseSelected.filter(_.nonEmpty))) {
        val mnemonic=new Mnemonic()
        val privateExtendedKey=HDKey.fromMasterSeed(mnemonic.toSeed(t.props.runNow().phraseWords.mkString(" "))).derive("m/44'/60'/0'/0").privateExtendedKey
        val wallet=Wallet.fromExtendedPrivateKey(privateExtendedKey)
        dom.window.localStorage.setItem("pubKey",wallet.getAddressString())
//        dom.window.localStorage.setItem("keystoreData","")
        t.props.runNow().router.set(ConfirmedBackupPhraseLoc).runNow()

      } else {
        //        todo add validator for mnemonic phrase
        t.modState(s => s.copy(isValidPhrase = true)).runNow()
      }
      Callback.empty
    }

    def generateWordList(e: String):VdomElement = {

      <.li(^.onClick-->generateWordListSelected(e),e)
    }

    def generateWordListSelected(e: String) = {
      if (t.state.runNow().phraseSelected.contains(e)){
      val selected = t.state.runNow().phraseSelection :+ e
      val diselect = t.state.runNow().phraseSelected.filterNot(_.equals(e))
      t.modState(s => s.copy(phraseSelected = diselect, phraseSelection = selected)).runNow
    }
      else if(t.state.runNow().phraseSelection.contains(e)){
      val selected = t.state.runNow().phraseSelected :+ e
      val diselect = t.state.runNow().phraseSelection.filterNot(_.equals(e))
      t.modState(s => s.copy(phraseSelected = selected, phraseSelection = diselect)).runNow

    }
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
        ^.className := "backupView-phrase",
        <.div(
          ^.className := "row",
          <.div(
            ^.className := "col-xs-12",
            <.h3("Confirm your back-up phrase"),
            <.div(
              ^.className := "row backupPhrase-section",
              <.ul(
                ^.id := "phrase-container ",
                ^.className := "col-xs-12 backupPhrase-container mnemonic-phrase-box")(s.phraseSelected.filter(_.nonEmpty) map generateWordList: _*)),
            if (s.isValidPhrase) {
              <.div(^.className := "alert alert-danger alert-dismissible fade in",
                <.a(^.href := "#", ^.className := "close", VdomAttr("data-dismiss") := "alert", VdomAttr("aria-label") := "close","×"),
                <.strong("Mnemonic-phrase not matched!!!"),
              )
            } else
              <.div())),
        <.div(
          ^.className := "row",
          <.div(
            ^.className := "col-xs-12 wordsList",
            <.ul(
              ^.id := "mnemonic-list")(Random.shuffle(s.phraseSelection.filter(_.nonEmpty)) map generateWordList: _*))),

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
    .initialState(State(Seq(""),Seq("")))
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}

