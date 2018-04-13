package com.livelygig.walletclient.modals

import com.livelygig.walletclient.facades.Toastr
import com.livelygig.walletclient.router.ApplicationRouter.{ AllAccountsLoc, Loc }
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }
import org.scalajs.dom

object ConfirmDeleteModal {

  case class Props(publicK: String = "", router: RouterCtl[Loc])

  final case class State()

  final class Backend(t: BackendScope[Props, State]) {

    def onDeleteAccount(): Callback = {
      t.props.flatMap {
        props =>
          if (props.publicK == dom.window.localStorage.getItem("pubKey")) {
            Toastr.info(dom.window.localStorage.getItem("pubKey"))
            //        WalletJS.deleteAccount(state.publicK, "currentAccount")
            props.router.set(AllAccountsLoc)
          } else {
            //        WalletJS.deleteAccount(state.publicK, "")
            props.router.set(AllAccountsLoc)
          }
      }
    }

    def render(p: Props, s: State): VdomElement =
      <.div(
        <.div(^.className := "modal fade", ^.id := "confirmDeleteModal", ^.role := "dialog", VdomAttr("aria-labelledby") := "exampleModalLabel", VdomAttr("aria-hidden") := "true",
          <.div(^.className := "modal-dialog", ^.role := "document",
            <.div(
              ^.className := "modal-content",
              <.div(
                ^.className := "modal-header",
                <.div(
                  ^.className := "row",
                  <.h3(^.className := "modal-title col-xs-6", ^.id := "exampleModalLabel", "Confirm"),
                  <.button(^.`type` := "button", ^.className := "close col-xs-1 col-xs-offset-5", VdomAttr("data-dismiss") := "modal", VdomAttr("aria-label") := "Close",
                    <.span(VdomAttr("aria-hidden") := "true", "×")))),
              <.div(
                ^.className := "modal-body",
                <.div(
                  ^.className := "container",
                  <.div(
                    <.h4("Are you sure you want to delete this ? "))),
                <.div(
                  ^.className := "modal-footer",
                  <.button(^.id := "txConfirmBtn", ^.`type` := "button", ^.className := "btn btnDefault", ^.onClick --> onDeleteAccount(), VdomAttr("data-dismiss") := "modal", "Confirm"),
                  <.button(^.id := "txConfirmDeleteBtn", ^.`type` := "button", ^.className := "btn btnDefault", VdomAttr("data-dismiss") := "modal", "Cancel")))))))
  }

  val component = ScalaComponent.builder[Props]("ConfirmDeleteModal")
    .initialState(State())
    .renderBackend[Backend]
    .componentWillReceiveProps(scope => scope.setState(State()))
    .build

  def apply(props: Props) = component(props)
}

