package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.views.components._
import com.livelygig.product.walletclient.views.modals.NotificationModal
import japgolly.scalajs.react
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent, _}
import org.querki.jquery.$

object NotificationView {

  case class Props()

  //case class State()

  final class Backend(t: BackendScope[Props, Unit]) {

    def onItemClicked(e: ReactEventFromHtml): react.Callback = {
      e.preventDefault()

      $(".active > div").removeClass("selectedItem")
      $(".row").removeClass("active")

      if (!$(e.target).is(".row")) {
        $(e.target).parents(".row").addClass("active")
      } else {
        $(e.target).addClass("active")
      }
      Callback.empty
    }

    def render(p: Props): VdomElement =
      <.div(^.id := "bodyWallet")(
        NotificationModal.component(NotificationModal.Props()),
        <.div(
          ^.className := "notifications",
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          ),
          <.div(
            ^.className := "row", ^.onClick ==> onItemClicked, VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#notificationModal",
            <.div(
              ^.className := "col-lg-11 col-md-11 col-sm-11 col-xs-10",
              <.h5("Lorem ipsum sit met"),
              <.p(
                ^.className := "ellipseText",
                "Lorem ipsum dolor sit amet, consectetur adiping elit..."
              ),
              <.p(
                ^.className := "messageTimeReceived",
                "Received 2 hours ago"
              )
            ),
            <.div(
              ^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-2 text-right",
              <.i(^.className := "fa fa-times-circle", VdomAttr("aria-hidden") := "true")
            )
          )
        )
      )
  }

  val component = ScalaComponent.builder[Props]("NotificationView")
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}
