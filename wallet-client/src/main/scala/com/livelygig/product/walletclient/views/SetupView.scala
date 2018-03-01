package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.facades.Validator
import com.livelygig.product.walletclient.router.ApplicationRouter.Loc
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{ BackendScope, Callback, ReactEventFromInput, ScalaComponent }
import org.querki.jquery.$
import org.scalajs.dom

object SetupView {

  case class Props(router: RouterCtl[Loc])

  final case class State(password: String = "", setRegAccount: String = "")

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      Callback {
        // init validator
        Validator
      }

    }
    def componentWillMount(props: Props): Callback = {
      Callback {
        // init validator
      }

    }
    def submitForm(e: ReactEventFromInput): react.Callback = {
      e.preventDefault()
      //      t.modState(s => s.copy(closePopup = true))
      if ($("#btnSetPassword").hasClass("disabled") || t.state.runNow().password.isEmpty) {
        Callback.empty
      } else {
        Callback(dom.window.location.href= "#/setup/register")
      }

    }

    def onPasswordStateChange(e: ReactEventFromInput): react.Callback = {
      var newValue = e.target.value
      t.modState(s => s.copy(password = newValue))
    }

    def render(p: Props, s: State): VdomElement = {
      <.form(^.id := "setupForm", VdomAttr("data-toggle") := "validator", ^.onSubmit ==> submitForm)(
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
          ^.className := "initialSetup-password",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12 col-sm-12 col-md-12 col-lg-12",
              <.h4(
                """Please create the password that will be used to unlock the ubunda application.
                    This password will also be used to locally encrypt the application.
                    Your password must be at least 4 characters long."""))),
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12 col-sm-12 col-md-12 col-lg-12",
              <.div(
                ^.className := "form-group has-feedback",
                <.input(^.id := "inputPassword", ^.className := "form-control", ^.placeholder := "Enter your password", ^.`type` := "password", VdomAttr("data-minlength") := "4", ^.required := true, VdomAttr("data-error") := "Please enter at least 4 characters", ^.onChange ==> onPasswordStateChange, ^.value := s.password),
                <.h4(^.className := "help-block with-errors")),
              <.div(
                ^.className := "form-group has-feedback",
                <.input(^.id := "inputPasswordConfirm", ^.className := "form-control", ^.placeholder := "Confirm your password", ^.`type` := "password", VdomAttr("data-minlength") := "4", ^.required := true, VdomAttr("data-match") := "#inputPassword", VdomAttr("data-match-error") := "The passwords do not match"),
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

  val component = ScalaComponent.builder[Props]("AccountView")
    .initialState(State())
    .renderBackend[Backend]
    .componentWillMount(scope => scope.backend.componentWillMount(scope.props))
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}
