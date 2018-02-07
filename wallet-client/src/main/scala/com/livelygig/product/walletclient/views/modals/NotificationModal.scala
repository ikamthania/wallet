package com.livelygig.product.walletclient.views.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, ^, _}

object NotificationModal {

  case class Props()

  final case class State()

  object State {
    def init: State =
      State()

  }
  final class Backend(t: BackendScope[Props, Unit]) {
    def render(p: Props): VdomElement =
      <.div(^.className := "modal fade", ^.id := "notificationModal", ^.role := "dialog", VdomAttr("aria-labelledby") := "exampleModalLabel", VdomAttr("aria-hidden") := "true",
        <.div(^.className := "modal-dialog", ^.role := "document",
          <.div(
            ^.className := "modal-content",
            <.div(
              ^.className := "modal-header",
              <.div(
                ^.className := "row",
                <.h5(^.className := "modal-title col-xs-6", ^.id := "exampleModalLabel", "Notification"),
                <.button(^.`type` := "button", ^.className := "close col-xs-1 col-xs-offset-5", VdomAttr("data-dismiss") := "modal", VdomAttr("aria-label") := "Close",
                  <.span(VdomAttr("aria-hidden") := "true", "×"))
              )
            ),
            <.div(
              ^.className := "modal-body",
              <.div(
                ^.className := "container",
                <.div(
                  ^.className := "row",
                  <.div(
                    ^.className := "notificationContainer",
                    <.h4(
                      ^.className := "title",
                      "Lorem ipsum dolor sit amet."
                    ),
                    <.p(
                      ^.className := "message",
                      "Lorem ipsum dolor sit amet, consectetur adipiscing elit ipsum dolor sit amet, consectetur adipiscing elit."
                    )
                  )
                )
              ),
              <.div(
                ^.className := "modal-footer",
                <.button(^.`type` := "button", ^.className := "btn btnDefault", VdomAttr("data-dismiss") := "modal", "Close")
              )
            )
          )))
  }

  val component = ScalaComponent.builder[Props]("NotificationModal")
    .renderBackend[Backend]
    // .componentDidMount(scope => scope.backend.componentDidMount())
    //.configure(Reusability.shouldComponentUpdate)
    .build
  def apply(props: Props) = component(props)
}
