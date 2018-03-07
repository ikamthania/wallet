package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.facades.KeyStore
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.imports.jQuery
import com.livelygig.product.walletclient.router.ApplicationRouter.{ ConfirmedBackupPhraseLoc, Loc }
import com.livelygig.product.walletclient.services.WalletCircuit
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{ BackendScope, Callback, ReactEventTypes, ScalaComponent }
import org.scalajs.dom
import diode.AnyAction._

import scala.collection.mutable.ArrayBuffer

object ConfirmBakupPhrase {

  case class Props(router: RouterCtl[Loc])

  final case class State(phrase: Seq[String], isValidPhrase: Boolean = false)

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      val phraseWords = WalletCircuit.zoom(_.user.userDetails.phrase).value
      t.modState(s => s.copy(phrase = phraseWords)).runNow()
      Callback.empty
    }

    def onBtnClicked(): react.Callback = {
      if (jQuery("ul#phrase-container li").length == 12) {
        var phraseWords = ArrayBuffer[String]()
        jQuery("ul#phrase-container li").each(func => {
          phraseWords += func.innerHTML
        })
        if (phraseWords.equals(t.state.runNow().phrase)) {

          t.props.runNow().router.set(ConfirmedBackupPhraseLoc).runNow()
          t.modState(s => s.copy(isValidPhrase = false)).runNow()

        } else {
          //        todo add validator for mnemonic phrase
          t.modState(s => s.copy(isValidPhrase = true)).runNow()
        }

      } else {
        //        todo add validator for mnemonic phrase
        t.modState(s => s.copy(isValidPhrase = true)).runNow()
      }
      Callback.empty
    }

    def generateWordList(e: String): VdomElement = {
      <.li(^.id := e, ^.onClick --> wordSelection(e), s"$e")
    }

    def wordSelection(e: /*ReactEventFromInput*/ String): react.Callback = {
      jQuery(".backupPhrase-container").append(s"<li id='li-$e'>$e</>")
      jQuery(s"#$e").hide()
      Callback.empty
    }

    def render(p: Props, s: State): VdomElement = {
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
                ^.id := "phrase-container",
                ^.className := "col-xs-12 backupPhrase-container")),
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
            <.p(
              ^.id := "errorMessage",
              "Invalid phrase"),
            <.ul(
              ^.id := "mnemonic-list")(s.phrase.sorted map generateWordList: _*))),

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
                "Next")))))
    }
  }

  val component = ScalaComponent.builder[Props]("ConfirmBakupPhrase")
    .initialState(State(Seq("")))
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}

