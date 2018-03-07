package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.facades.KeyStore
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.imports.jQuery
import com.livelygig.product.walletclient.router.ApplicationRouter.{ ConfirmedBackupPhraseLoc, Loc }
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{ BackendScope, Callback, ReactEventTypes, ScalaComponent }
import org.scalajs.dom

object ConfirmBakupPhrase {

  case class Props(router: RouterCtl[Loc])

  final case class State()

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      Callback.empty
    }

    def onBtnClicked(): react.Callback = {
      t.props.flatMap(_.router.set(ConfirmedBackupPhraseLoc))
    }

    def componentWillMount(props: Props): Callback = {
      Callback {
      }
    }
    def generateWordList(e: String): VdomElement = {
      /* val words =e.split(" ");
      // var wordsUnsorted = shuffleArray(words);
      var sortedWords = words.sorted;*/
      <.li(^.id := e, ^.onClick --> wordSelection(e), s"$e")

      //      $("#mnemonic-list").html(items);
      //      $("#mnemonic-list li").on("click", (arg) => onWordClicked(arg));

    }

    def wordSelection(e: /*ReactEventFromInput*/ String): react.Callback = {
      jQuery(".backupPhrase-container").append(s"<li>$e</>")
      jQuery(s"#$e").remove()
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
                ^.className := "col-xs-12 backupPhrase-container")))),
        <.div(
          ^.className := "row",
          <.div(
            ^.className := "col-xs-12 wordsList",
            <.p(
              ^.id := "errorMessage",
              "Invalid phrase"),
            <.ul(
              ^.id := "mnemonic-list")(KeyStore.generateRandomSeed("").toString.split(" ") map generateWordList: _*))),

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
    .initialState(State())
    .renderBackend[Backend]
    .componentWillMount(scope => scope.backend.componentWillMount(scope.props))
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}

