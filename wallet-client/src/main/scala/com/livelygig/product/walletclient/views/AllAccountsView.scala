package com.livelygig.product.walletclient.views
import com.livelygig.product.shared.models.wallet.{ Account, AccountInfo }
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.product.walletclient.handler.SelectAddress
import com.livelygig.product.walletclient.router.ApplicationRouter._
import com.livelygig.product.walletclient.services.WalletCircuit
import diode.AnyAction._
import diode.react.ModelProxy
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }
import japgolly.scalajs.react.{ BackendScope, Callback, ScalaComponent, _ }
import org.scalajs.dom

object AllAccountsView {

  case class Props(router: RouterCtl[Loc], proxy: ModelProxy[AccountInfo])

  case class State(addressSelected: String)

  final class Backend(t: BackendScope[Props, State]) {

    def updateURL(loc: String): Callback = {
      val baseUrl = dom.window.location.href
      val updatedUrl = baseUrl.split("#").head
      loc match {
        case "AddNewAccountLoc" => t.props.runNow().router.set(AddNewAccountLoc).runNow()
        case "AccountLoc" => t.props.runNow().router.set(LandingLoc).runNow()
      }
      Callback.empty
    }

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
          t.modState(state => state.copy(addressSelected = addressSelected)).runNow()
          //          jQuery(e.target).parents("li").addClass("active")
        } else jQuery(e.target).addClass("active")
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
                            s"0x${account.address}")))))).toVdomArray)))),
        <.div(
          ^.className := "container btnDefault-container homeButtonContainer",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.button(^.`type` := "button", ^.className := "btn btnDefault accountBtn goupButton notAlphaV", "Delete", ^.onClick --> updateURL("")),
              <.button(^.`type` := "button", ^.className := "btn btnDefault accountBtn goupButton notAlphaV", "Configure", ^.onClick --> updateURL("")),
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
