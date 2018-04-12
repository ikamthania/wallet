package com.livelygig.walletclient.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }

object FilterModal {

  case class Props()

  final case class State()

  object State {
    def init: State =
      State()

  }
  final class Backend(t: BackendScope[Props, Unit]) {
    def render(p: Props): VdomElement =
      <.div(^.className := "modal fade", ^.id := "filterModal", ^.role := "dialog", VdomAttr("aria-labelledby") := "exampleModalLabel", VdomAttr("aria-hidden") := "true",
        <.div(^.className := "modal-dialog", ^.role := "document",
          <.div(
            ^.className := "modal-content",
            <.div(
              ^.className := "modal-header",
              <.div(
                ^.className := "row",
                <.h5(^.className := "modal-title col-xs-6", ^.id := "exampleModalLabel", "Filter"),
                <.button(^.`type` := "button", ^.className := "close col-xs-1 col-xs-offset-5", VdomAttr("data-dismiss") := "modal", VdomAttr("aria-label") := "Close",
                  <.span(VdomAttr("aria-hidden") := "true", "×")))),
            <.div(
              ^.className := "modal-body",
              <.div(
                ^.className := "container",
                <.form(
                  ^.className := "col-xs-5",
                  <.h5("Currency:"),
                  <.div(
                    ^.className := "formGroup",
                    <.label(
                      ^.className := "radio-inline",
                      <.input(^.`type` := "radio", ^.name := "optradio"),
                      "Option 1"),
                    <.label(
                      ^.className := "radio-inline",
                      <.input(^.`type` := "radio", ^.name := "optradio"),
                      "Option 2"),
                    <.label(
                      ^.className := "radio-inline",
                      <.input(^.`type` := "radio", ^.name := "optradio"),
                      "Option 3"),
                    <.label(
                      ^.className := "radio-inline",
                      <.input(^.`type` := "radio", ^.name := "optradio"),
                      "Option 4"))),
                <.form(
                  ^.className := "col-xs-7 dateInputGroup",
                  <.h5("Date:"),
                  <.div(
                    ^.className := "formGroup ",
                    <.div(
                      ^.className := "dateInput",
                      <.label("From:"),
                      <.input(^.`type` := "date", ^.className := "")),
                    <.div(
                      ^.className := "dateInput",
                      <.label("To:"),
                      <.input(^.`type` := "date", ^.className := "")))))),
            <.div(
              ^.className := "modal-footer",
              <.button(^.`type` := "button", ^.className := "btn btnDefault", VdomAttr("data-dismiss") := "modal", "Apply")))))
  }

  val component = ScalaComponent.builder[Props]("FilterModal")
    .renderBackend[Backend]
    // .componentDidMount(scope => scope.backend.componentDidMount())
    //.configure(Reusability.shouldComponentUpdate)
    .build
  def apply(props: Props) = component(props)
}
