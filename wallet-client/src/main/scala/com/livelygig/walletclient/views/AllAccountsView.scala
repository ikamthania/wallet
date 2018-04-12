package com.livelygig.walletclient.views

import com.livelygig.shared.models.wallet.AccountInfo
import com.livelygig.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.walletclient.handler.SelectAddress
import com.livelygig.walletclient.router.ApplicationRouter._
import com.livelygig.walletclient.services.WalletCircuit
import diode.AnyAction._
import diode.react.ModelProxy
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }
import japgolly.scalajs.react.{ BackendScope, Callback, ScalaComponent, _ }

object AllAccountsView {

  case class Props(router: RouterCtl[Loc], proxy: ModelProxy[AccountInfo])

  case class State(addressSelected: String)

  final class Backend(t: BackendScope[Props, State]) {

    def setSetupScreenView(): Callback = {
      t.props.flatMap(_.router.set(SetupRegisterLoc))
    }

    def onSelectAccountClicked(): Callback = {
      val redirectCallback = t.props.map(_.router.set(AccountLoc))
      val updateAddressCallback = t.state.map {
        state =>
          WalletCircuit.dispatch(SelectAddress(state.addressSelected))
      }
      (updateAddressCallback >> redirectCallback).runNow()
    }

    def onItemClicked(addressSelected: String)(e: ReactEventFromHtml): react.Callback = {
      Callback {
        e.preventDefault()
        jQuery("li").removeClass("active")
        if (!jQuery(e.target).is("li")) {
          {
            t.modState(state => state.copy(addressSelected = addressSelected)).runNow()
          }
        } else {
          jQuery(e.target).addClass("active")
        }
      }
    }

    def componentDidMount(): Callback = {
      Callback.empty
    }

    def render(p: Props, s: State): VdomElement =
      <.div(^.id := "bodyWallet")(
        <.div(
          ^.className := "identify-screen-main",
          <.div(
            ^.className := "identify-screen-inner",
            <.div(
              ^.className := "identify-screen-profileslist scrollableArea",
              <.ul(
                ^.id := "accountList",
                p.proxy.value.accounts.map(account =>
                  <.li(
                    (^.className := "active").when(s.addressSelected == account.address),
                    ^.id := account.address,
                    (^.className := "selected").when(account.address == p.proxy.value.selectedAddress),
                    ^.key := account.address,
                    ^.onClick ==> onItemClicked(account.address),
                    <.a(
                      ^.href := "javascript:void(0)",
                      <.div(
                        ^.className := "row",
                        <.div(^.className := "col-lg-4 col-md-4 col-sm-4 col-xs-3")(
                          <.p(account.accountName)),
                        <.div(^.className := "col-lg-8 col-md-8 col-sm-8 col-xs-9")(
                          <.p(
                            ^.id := "keystoreKey._1._2",
                            ^.className := "ellipseText",
                            "")),
                        <.div(
                          ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                          <.p(
                            ^.id := "keystoreKey._1._2",
                            ^.className := "ellipseText publicAdd",
                            account.address)))))).toVdomArray)))),
        <.div(
          ^.className := "container btnDefault-container homeButtonContainer",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.button(^.`type` := "button", ^.className := "btn btnDefault accountBtn goupButton notAlphaV", "Delete", ^.onClick --> Callback.empty),
              <.button(^.`type` := "button", ^.className := "btn btnDefault accountBtn goupButton", "Configure", ^.onClick --> p.router.set(AddTokenLoc)),
              <.button(^.`type` := "button", ^.className := "btn btnDefault accountBtn goupButton", "Add", ^.onClick --> setSetupScreenView()),
              <.button(^.`type` := "button", ^.className := "btn btnDefault accountBtn goupButton", "Select", ^.onClick --> onSelectAccountClicked())))))
  }

  val component = ScalaComponent.builder[Props]("AllAccountsView")
    .initialState(State(""))
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount())
    .build

  def apply(props: Props) = component(props)
}
