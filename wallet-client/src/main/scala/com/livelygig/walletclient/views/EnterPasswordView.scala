package com.livelygig.walletclient.views

import com.livelygig.walletclient.facades.VaultGaurd
import com.livelygig.walletclient.facades.bootstrapvalidator.BootstrapValidator.bundle._
import com.livelygig.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.walletclient.handler.{ LoginUser, UpdatePassword }
import com.livelygig.walletclient.router.ApplicationRouter.{ AccountLoc, Loc, SetupRegisterLoc, ViewBackupPhraseLoc }
import com.livelygig.walletclient.services.WalletCircuit
import diode.AnyAction._
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{ BackendScope, Callback, ReactEventFromInput, ScalaComponent }
import org.scalajs.jquery.JQueryEventObject

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object EnterPasswordView {

  case class Props(router: RouterCtl[Loc])

  final case class State(password: String)

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props, state: State): Callback = {
      Callback {
        jQuery("#setupForm").validator("update").on("submit", (e: JQueryEventObject) => {
          if (!e.isDefaultPrevented()) {
            e.preventDefault()
            VaultGaurd.decryptVault(
              t.state.runNow().password).map {
                e =>
                  WalletCircuit.dispatch(LoginUser())
                  WalletCircuit.dispatch(UpdatePassword(t.state.runNow().password))
                  if (WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo.accounts).value == Nil) {
                    t.props.flatMap(_.router.set(SetupRegisterLoc)).runNow()
                  } else if (e.mnemonicPhrase == "") {
                    t.props.flatMap(_.router.set(ViewBackupPhraseLoc)).runNow()
                  } else {
                    t.props.flatMap(_.router.set(AccountLoc)).runNow()
                  }

              }.recover {
                case _ =>
                  jQuery("div.form-group").removeClass("has-success").addClass("has-error has-danger")
                  jQuery("h4.help-block").html("<ul class=\"list-unstyled\"><li>Wrong Password</li></ul>").css("with-errors")

              }
          } else {
            e.preventDefault()
          }
        })
      }
    }

    def componentWillMount(props: Props): Callback = {
      Callback {
      }
    }

    def onPasswordStateChange(e: ReactEventFromInput): react.Callback = {
      val newValue = e.target.value
      t.modState(s => s.copy(password = newValue))
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
                <.h3("Authenticate"))))),
        <.div(
          ^.className := "initialSetup-password",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12 col-sm-12 col-md-12 col-lg-12",
              <.h4(
                """Please enter the password for the application."""))),
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12 col-sm-12 col-md-12 col-lg-12",
              <.div(
                ^.className := "form-group has-feedback",
                <.input(^.id := "inputPassword", ^.className := "form-control", ^.placeholder := "Enter your password", ^.`type` := "password", ^.required := true, VdomAttr("data-error") := "Please enter password", ^.onChange ==> onPasswordStateChange, ^.value := s.password),
                <.h4(^.className := "help-block with-errors")))),
          <.div(
            ^.className := "container btnDefault-container container-NoBorder",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                <.button(^.id := "btnSetPassword", ^.className := "btn btnDefault goupButton", "Next", ^.`type` := "submit"))))))
    }
  }

  val component = ScalaComponent.builder[Props]("EnterPasswordView")
    .initialState(State(""))
    .renderBackend[Backend]
    .componentWillMount(scope => scope.backend.componentWillMount(scope.props))
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props, scope.state))
    .build

  def apply(props: Props) = component(props)
}
