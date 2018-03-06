package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.router.ApplicationRouter.{ ConfirmBackupPhraseLoc, Loc }
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{ BackendScope, Callback, ScalaComponent }

object ViewBackupPhrase {

  case class Props(router: RouterCtl[Loc])

  final case class State()

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      Callback.empty
    }

    def onBtnClicked(): react.Callback = {
      t.props.flatMap(_.router.set(ConfirmBackupPhraseLoc))
    }

    def componentWillMount(props: Props): Callback = {
      Callback {
      }
    }

    def render(p: Props, s: State): VdomElement = {
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
                "Next")))))

    }
  }

  val component = ScalaComponent.builder[Props]("ViewBackupPhrase")
    .initialState(State())
    .renderBackend[Backend]
    .componentWillMount(scope => scope.backend.componentWillMount(scope.props))
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}

