package com.livelygig.product.walletclient.views

import com.livelygig.product.shared.models.wallet._
import com.livelygig.product.walletclient.facades.{ Toastr, WalletJS }
import com.livelygig.product.walletclient.handler.{ GetCurrencies, GetUserDetails, UpdateAccountTokenList }
import com.livelygig.product.walletclient.rootmodel.ERCTokenRootModel
import com.livelygig.product.walletclient.router.ApplicationRouter.{ Loc, _ }
import com.livelygig.product.walletclient.services.{ CoreApi, WalletCircuit }
import com.livelygig.product.walletclient.facades.Toastr
import diode.AnyAction._
import diode.data.Pot
import diode.react.ModelProxy
import diode.react.ReactPot._
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ ^, _ }
import japgolly.scalajs.react.{ BackendScope, Callback, ScalaComponent, _ }
import org.querki.jquery.$
import org.scalajs.dom
import org.scalajs.dom.raw.Element
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global
//import japgolly.scalajs.react.vdom.Implicits._

object AccountView {

  // Toastr options
  Toastr.options.timeOut = 5000; // How long the toast will display without user interaction
  Toastr.options.extendedTimeOut = 60; // How long the toast will display after a user hovers over it
  Toastr.options.closeButton = true
  Toastr.options.positionClass = "toast-top-full-width"
  case class Props(proxy: ModelProxy[Pot[ERCTokenRootModel]], router: RouterCtl[Loc], loc: String = "")

  final case class State(currencySelected: String, coinExchange: CoinExchange, userDetails: UserDetails = UserDetails("", WalletDetails("", "")))

  final class Backend(t: BackendScope[Props, State]) {
    def getLiveCurrencyUpdate() = {
      CoreApi.mobileGetUserDetails().map { userDetails =>
        Json.parse(userDetails).validate[UserDetails].asOpt match {
          case Some(response) =>
            WalletCircuit.dispatch(GetUserDetails(response))
            t.modState(s => s.copy(userDetails = response)).runNow()

          case None => println("Error in parsing user details response")
        }
      }
      CoreApi.mobileGetLivePrices()
        .map(prices =>
          Json.parse(prices)
            .validate[CoinExchange].asEither match {
              case Left(err) => println(err)
              case Right(res) =>
                WalletCircuit.dispatch(GetCurrencies(res))
                t.modState(s => s.copy(
                  coinExchange = res,
                  currencySelected = res
                    .coinExchangeList
                    .find(_.coin.equalsIgnoreCase("ETH"))
                    .get
                    .currencies
                    .find(_.symbol.equalsIgnoreCase(t.state.runNow().currencySelected))
                    .get.symbol))
                .runNow()
            })

    }

    def setCurrencyLocal(currSymbol: String): react.Callback = {
      //      Toastr.info(currSymbol)
      dom.window.localStorage.setItem("currency", currSymbol)
      Callback.empty
    }

    def updateCurrency(): Callback = {
      //      Toastr.info(dom.window.localStorage.getItem("currency"))
      val slctedCurr = if (dom.window.localStorage.getItem("currency") == null) "USD" else dom.window.localStorage.getItem("currency")
      t.modState(s => s.copy(currencySelected = slctedCurr)).runNow()
      Callback.empty
    }

    def componentDidMount(props: Props): Callback = {
      $(".select-currency-info").removeClass("active")
      $(".select-currency-info").first().addClass("active")
      getLiveCurrencyUpdate
      setCurrencyLocal(t.state.runNow().currencySelected)
      Callback.when(!props.proxy().isPending)(props.proxy.dispatchCB((UpdateAccountTokenList())))

    }

    def updateURL(loc: String): Callback = {

      loc match {
        case "SendLoc" => t.props.runNow().router.set(SendLoc).runNow()
        case "HistoryLoc" => t.props.runNow().router.set(HistoryLoc).runNow()
        case "RequestLoc" => t.props.runNow().router.set(RequestLoc).runNow()
      }
      Callback.empty
    }

    def onItemClicked(e: ReactEventFromHtml): react.Callback = {
      e.preventDefault()
      $(".select-currency-info").removeClass("active")
      if (!$(e.target).is(".select-currency-info")) {
        $(e.target).parents(".select-currency-info").addClass("active")
      } else {
        $(e.target).addClass("active")
      }
      Callback.empty
    }

    def updateCurrencyState(e: ReactEventFromInput): react.Callback = {
      val newValue = e.target.value
      setCurrencyLocal(newValue)
      t.modState(s => s.copy(currencySelected = newValue)).runNow()
      Callback.empty
    }

    def render(p: Props, s: State): VdomElement = {
      val coinList = s.coinExchange.coinExchangeList

      def createItem(userERCToken: ERC20ComplientToken) = {
        val coin = coinList.filter(e => e.coin.equalsIgnoreCase(userERCToken.symbol))
          .flatMap(_.currencies.filter(e => e.symbol == s.currencySelected.toUpperCase))

        <.div(
          ^.className := "row select-currency-info", ^.onClick ==> onItemClicked,
          <.div(
            <.div(
              ^.className := "row left-info",
              <.label(^.className := "col-lg-8 col-md-8 col-sm-8 col-xs-4", userERCToken.tokenName),
              <.span(
                ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-4 tokenUSDValue ellipseText",
                WalletJS.getnumberFormat(BigDecimal.apply(userERCToken.balance).setScale(2, BigDecimal.RoundingMode.UP).toString)),
              <.span(^.className := "col-lg-2 col-lg-2 col-md-2 col-sm-2 col-xs-4 symbol", s"${userERCToken.symbol}")),
            coin.map { e =>
              <.div(
                ^.className := "row",
                <.span(
                  ^.className := "col-lg-8 col-md-8 col-sm-8 col-xs-4 ellipseText",
                  s"@ ${e.price} ${e.symbol}"),
                <.span(
                  ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-4 tokenUSDValue ellipseText",
                  WalletJS.getnumberFormat(BigDecimal.apply(e.price.toDouble * userERCToken.balance.toDouble).setScale(2, BigDecimal.RoundingMode.UP).toString())),
                <.span(
                  ^.className := "col-lg-2 col-lg-2 col-md-2 col-sm-2 col-xs-4 symbol",
                  e.symbol))
            }.toVdomArray))
      }
      <.div(^.id := "bodyWallet")(
        <.div(
          ^.className := "wallet-information-bottom",
          <.div(
            ^.className := "wallet-information-total",
            <.div(
              ^.className := "row heading-currency",
              <.div(
                ^.className := "col-lg-8 col-md-8 col-sm-8 col-xs-4 hand-cuure-left",
                <.label("total:")),
              <.div(
                ^.className := "col-lg-4 col-md-4 col-sm-4 col-xs-8 select-currency",
                <.label(
                  ^.className := "ellipseText",
                  p.proxy().render(tokenList =>

                    WalletJS.getnumberFormat(BigDecimal.apply(tokenList.accountTokenDetails.map { token =>
                      val coin = coinList.filter(_.coin.equalsIgnoreCase(token.symbol))
                        .flatMap { e => e.currencies.filter(c => c.symbol.equalsIgnoreCase(s.currencySelected)) }
                      token.balance.toDouble * coin.map(_.price).sum
                    }.sum).setScale(2, BigDecimal.RoundingMode.UP).toString))),
                <.select(^.id := "selectOption", ^.onChange ==> updateCurrencyState)(
                  s.coinExchange.coinExchangeList.filter(e => e.coin.equalsIgnoreCase("ETH")).flatMap(_.currencies
                    .map { currency =>
                      if (s.currencySelected.equalsIgnoreCase(currency.symbol)) {
                        <.option(^.value := currency.symbol, currency.symbol, ^.selected := "selected")
                      } else {
                        <.option(^.value := currency.symbol, currency.symbol)
                      }
                      /* <.option(^.value := currency.symbol, currency.symbol, ^.selected := "selected")
                        .when(s.currencySelected.equalsIgnoreCase(currency.symbol))
                      <.option(^.value := currency.symbol, currency.symbol)
                        .when(!s.currencySelected.equalsIgnoreCase(currency.symbol))*/
                    }).toVdomArray))),
            p.proxy().render(tokenList =>
              <.div(^.className := "currency-info-scrollbar")(
                tokenList.accountTokenDetails.filter(e => e.symbol.equalsIgnoreCase("ETH") || !e.balance.equalsIgnoreCase("0")) reverseMap createItem: _*)),
            p.proxy().renderFailed(ex => <.div()(
              <.label(^.className := "warn-text", "Error while loading available token list"))),
            p.proxy().renderPending(e =>
              <.div()(
                <.img(^.src := "../assets/images/processing-img.svg", ^.className := "loading-img"))))),
        <.div(
          ^.className := "container btnDefault-container homeButtonContainer",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.button(^.`type` := "button", ^.className := "btn btnDefault btn-account-camera goupButton notAlphaV",
                <.i(^.className := "fa fa-camera", VdomAttr("aria-hidden") := "true")),
              <.button(^.`type` := "button", ^.className := "btn btnDefault goupButton", ^.onClick --> updateURL("HistoryLoc") /*p.router.setEH(HistoryLoc)*/ , "Transactions"),
              <.button(^.`type` := "button", ^.className := "btn btnDefault goupButton", ^.onClick --> updateURL("RequestLoc") /* p.router.setEH(RequestLoc)*/ , "Request"),
              <.button(^.`type` := "button", ^.className := "btn btnDefault goupButton", ^.onClick --> updateURL("SendLoc") /*p.router.setEH(SendLoc)*/ , "Send")))))
    }
  }

  val component = ScalaComponent.builder[Props]("AccountView")
    .initialState(State("ETH", CoinExchange(Seq(CurrencyList("", Seq(Currency("", 0, ""))))), UserDetails("", WalletDetails("", "0"))))
    .renderBackend[Backend]
    .componentWillMount(scope => scope.backend.updateCurrency())
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build
  def apply(props: Props) = component(props)
}
