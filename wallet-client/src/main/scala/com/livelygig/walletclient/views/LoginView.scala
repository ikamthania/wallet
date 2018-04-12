package com.livelygig.walletclient.views

import com.livelygig.walletclient.router.ApplicationRouter.Loc
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{<, VdomAttr, VdomElement, ^, _}
import japgolly.scalajs.react.{BackendScope, Callback, ReactEventFromHtml, ScalaComponent}

import scala.scalajs.js

object LoginView {

  case class Props(router: RouterCtl[Loc], loc: String = "")

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
        <.div(^.className := "wallet-inner-navigation",
          <.div(^.className := "row",
          <.fieldset(^.className := "col-md-6 col-md-offset-3",
          <.div(^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
          <.div(^.className := "wallet-information",
          <.h2("Wallet"),
          <.h3("Login")
          )
          )
          )
          )
          ),
        <.div(
          ^.className := "importAccount",
        
//          <.div(
//            ^.className := "starter-template row",
//            """@request.flash.get("error").map { msg =>""",
//            <.div(
//              ^.className := "col-md-6 col-md-offset-3 alert alert-danger",
//              <.a(
//                ^.href := "javascript:void(0)",
//                ^.className := "close",
//                VdomAttr("data-dismiss") := "alert",
//                "×"),
//              <.strong("""@Messages("error")"""),
//              "@msg"),
//            """}
//        @request.flash.get("info").map { msg =>""",
//            <.div(
//              ^.className := "col-md-6 col-md-offset-3 alert alert-info",
//              <.a(
//                ^.href := "javascript:void(0)",
//                ^.className := "close",
//                VdomAttr("data-dismiss") := "alert",
//                "×"),
//              <.strong("""@Messages("info")"""),
//              "@msg"),
//            """}
//        @request.flash.get("success").map { msg =>""",
//            <.div(
//              ^.className := "col-md-6 col-md-offset-3 alert alert-success",
//              <.a(
//                ^.href := "javascript:void(0)",
//                ^.className := "close",
//                VdomAttr("data-dismiss") := "alert",
//                "×"),
//              <.strong("""@Messages("success")"""),
//              "@msg"),
//            "}"),
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 account",
              <.h4("Public address or email id:"),
              <.div(
                ^.className := "form-group  col-md-12 col-xs-12",
                ^.id := "defaultAlias_field",
                <.input(
                  ^.`type` := "text",
                  ^.className := "col-md-12 col-xs-12",
                  ^.id := "defaultAlias",
                  ^.name := "walletIdentity",
                  ^.value := "",
                  ^.required := true,
                  VdomAttr("data-error") := "default alias required!"),
                <.div(
                  ^.className := "help-block with-errors",
                  ^.style := js.Dictionary("font-size" -> "16px"))),
              <.h4("Unlocking password:"),
              <.div(
                ^.className := "form-group  col-md-12 col-xs-12",
                ^.id := "password_field",
                <.input(
                  ^.`type` := "password",
                  ^.className := "col-md-12 col-xs-12",
                  ^.id := "password",
                  ^.name := "password",
                  ^.value := "",
                  ^.required := true,
                  VdomAttr("data-error") := "valid unlocking password is required"),
                <.div(
                  ^.className := "help-block with-errors",
                  ^.style := js.Dictionary("font-size" -> "16px"))))),
          <.div(
            ^.className := "form-group",
            <.div(
              ^.className := "container btnDefault-container info",
              <.div(
                ^.className := "not_signup",
                <.span("Not a member"),
                <.a(
                  ^.href := "#setup/register",
                  "Sign up now")),
              <.button(
                ^.className := "btn  btnDefault",
                ^.`type` := "submit",
                "Log In")))),
          )
    }
  }

  val component = ScalaComponent.builder[Props]("BackupAccount")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build
  def apply(props: Props) = component(props)
}
