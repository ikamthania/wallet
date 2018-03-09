package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.router.ApplicationRouter.{ AccountLoc, Loc, LoginLoc }
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ <, _ }
import japgolly.scalajs.react.{ BackendScope, Callback, ScalaComponent }

object ConfirmedBackupPhrase {

  case class Props(router: RouterCtl[Loc])

  final case class State()

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): react.Callback = {
      Callback.empty
    }

    def onBtnClicked(): react.Callback = {
      t.props.flatMap(_.router.set(AccountLoc))
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

