package com.livelygig.product.walletclient.views

import com.definitelyscala.bootstrap.ModalOptionsBackdropString
import com.livelygig.product.shared.models.wallet._
import com.livelygig.product.walletclient.facades.Toastr
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.product.walletclient.handler.{ UpdateAccountTokenList }
import com.livelygig.product.walletclient.modals.ConfirmModal
import com.livelygig.product.walletclient.rootmodel.ERCTokenRootModel
import com.livelygig.product.walletclient.router.ApplicationRouter
import com.livelygig.product.walletclient.services.{ CoreApi, WalletCircuit }
import diode.AnyAction._
import diode.data.Pot
import diode.react.ModelProxy
import diode.react.ReactPot._
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }
import japgolly.scalajs.react.{ Callback, _ }
import org.scalajs.dom
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

object SendView {

  case class Props(proxy: ModelProxy[Pot[ERCTokenRootModel]], rc: RouterCtl[ApplicationRouter.Loc], to: String = "")

  final case class State(etherTransaction: EtherTransaction, userUri: String, etherBalance: String,
    coinSymbol: String, currSymbol: String, ethereumPrice: String,
    coinExchange: Seq[CurrencyList] = Seq(CurrencyList("", Seq(Currency("", 0, "")))),
    appName: String = "", amntInCurr: String = "",
    totalInCurr: String = "0.0", totalInCoin: String = "0.0")

  final class Backend(t: BackendScope[Props, State]) {
    val ethereumFee = 0.015 //get from API
    val accountInfo = WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo).value

    def updateCurrency(): Callback = {
      val slctedCurr = if (dom.window.localStorage.getItem("currency") == null) "USD" else dom.window.localStorage.getItem("currency")
      t.modState(s => s.copy(currSymbol = slctedCurr)).runNow()
      Callback.empty
    }

    def getLiveCurrencyUpdate(): Callback = {
      CoreApi.mobileGetLivePrices()
        .map(prices =>
          Json.parse(prices)
            .validate[CoinExchange].asEither match {
              case Left(err) => println(err)
              case Right(res) =>
                t.modState(s => s.copy(
                  coinExchange = res.coinExchangeList))
                .runNow()
                updateETHPrice(t.state.runNow().coinSymbol)
            })
      Callback.empty
    }

    def updateTokenPrice(currList: Seq[CurrencyList]) = {
      currList.filter(_.coin.equalsIgnoreCase(t.state.runNow().coinSymbol)).map { coin =>
        coin.currencies.filter(_.symbol.equalsIgnoreCase(t.state.runNow().currSymbol)).map { currency =>
          t.modState(s => s.copy(coinExchange = currList, ethereumPrice = currency.price.toString)).runNow()
        }
      }
    }

    def updateETHPrice(slctedCoin: String) = {
      val slctedCurr = t.state.runNow().currSymbol
      val coinExchng = t.state.runNow().coinExchange
      coinExchng.filter(_.coin.equalsIgnoreCase(slctedCoin)).map { coin =>
        coin.currencies.filter(_.symbol.equalsIgnoreCase(slctedCurr)).map { e =>
          t.modState(s => s.copy(ethereumPrice = e.price.toString)).runNow()
          js.timers.setTimeout(1) {
            calAmount("amount", t.state.runNow().etherTransaction.amount)
          }
        }
      }
    }

    Toastr.options.timeOut = 10000; // How long the toast will display without user interaction
    Toastr.options.extendedTimeOut = 100; // How long the toast will display after a user hovers over it
    Toastr.options.closeButton = true
    Toastr.options.positionClass = "toast-top-full-width"
    /*
    * Get ether balance and update etherBalance state
    * */

    def componentDidMount(props: Props): Callback = {
      //      val baseUrl = dom.window.location.href
      //      val updatedUrl = baseUrl.split("#").head
      //      dom.window.location.href = s"${updatedUrl}#/send"
      updateTokenPrice(t.state.runNow().coinExchange)

      var receiver = t.props.runNow().to.split("/").last.toString
      var amount = t.props.runNow().to.split("/").head
      jQuery("#lblDisplayFrom").text(jQuery("#slctAccount option:first").text())
      Callback.when(!props.proxy().isPending)(props.proxy.dispatchCB((UpdateAccountTokenList()))) >>
        CallbackTo {
          t.props.map {
            props =>
              props.proxy.value.map {
                erc20token =>
                  val ethBlnc = erc20token.accountTokenDetails.find(_.symbol == "ETH").getOrElse(ERC20ComplientToken("", "", "", 0, "")).balance
                  t.modState(s => s.copy(
                    etherBalance = ethBlnc,
                    etherTransaction =
                      s.etherTransaction.copy(amount = amount, receiver = receiver))).runNow()
              }
          }
        }

    }

    /*
    * update etherTransaction state
    * */

    def onFocusChange(inputId: String)(e: ReactEventFromInput): react.Callback = {
      if (jQuery("#" + inputId).value() == "") {
        if (jQuery("#" + inputId + " ~  div").hasClass("maxButtonHidden")) jQuery("#" + inputId + " ~  div").removeClass("maxButtonHidden")
      }

      Callback.empty
    }

    def onBlurChange(inputId: String)(e: ReactEventFromInput): react.Callback = {
      js.timers.setTimeout(1) {
        //if (!jQuery("#" + inputId + " ~  div").hasClass("maxButtonHidden")) jQuery("#" + inputId + " ~  div").addClass("maxButtonHidden")
      }
      Callback.empty
    }

    def onMaxButtonClicked()(e: ReactEventFromInput): react.Callback = {
      val total = if (t.state.runNow().etherBalance == "") "0.0" else t.state.runNow().etherBalance

      val ethPriceInCurr = if (total != "0.0") round(total.toDouble * t.state.runNow().ethereumPrice.toDouble - ethereumFee, 2) else total
      val totalIncoin = round(total.toDouble + ethereumFee, 5)
      val totalInCurr = round(ethPriceInCurr.toDouble + (ethereumFee * t.state.runNow().ethereumPrice.toDouble), 2)

      t.modState(s => s.copy(
        etherTransaction = s.etherTransaction.copy(amount = total),
        amntInCurr = ethPriceInCurr, totalInCoin = totalIncoin, totalInCurr = totalInCurr)).runNow()

      changeInputButtonsVisibility(ethPriceInCurr, "usdTxtValue")
      changeInputButtonsVisibility(totalInCurr, "coinTxtValue")

      Callback.empty
    }

    def onStateChange(value: String)(e: ReactEventFromInput): react.Callback = {
      var newValue = e.target.value
      value match {
        case "amount" => calAmount("amount", newValue)
        case "amountUSD" => calAmount("amountUSD", newValue)
        case "password" => t.modState(s => s.copy(etherTransaction = s.etherTransaction.copy(password = newValue)))
        case "receiver" => t.modState(s => s.copy(etherTransaction = s.etherTransaction.copy(receiver = newValue)))
        case "txnType" =>
          {

            t.props.runNow().proxy.value.get.accountTokenDetails.map { token =>
              newValue match {
                case token.contractAddress =>
                  t.modState(s => s.copy(
                    etherTransaction = s.etherTransaction
                      .copy(txnType = token.contractAddress, decimal = token.decimal),
                    etherBalance = token.balance, coinSymbol = token.symbol.toUpperCase)).runNow()
                  updateETHPrice(token.symbol)

                case "eth" =>
                  t.modState(s => s
                    .copy(
                      etherTransaction = s.etherTransaction.copy(txnType = newValue, decimal = 18),
                      etherBalance = token.balance, coinSymbol = "ETH")).runNow()
                  updateETHPrice("eth")

                case _ =>
                  None
              }
            }
          }
          Callback.empty
      }
    }

    def calAmount(id: String, amount: String): react.Callback = {
      val value = if (amount == ".") toAlphaNumeric("0" + amount) else toAlphaNumeric(amount)
      // val value = if (toAlphaNumeric(amount) == "0") t.props.runNow().to.split("/").head else toAlphaNumeric(amount)
      if (value != "") {
        id match {
          case "amount" =>
            val ethPriceInCurr = round(value.toDouble * t.state.runNow().ethereumPrice.toDouble, 2)
            val totalIncoin = round(value.toDouble + ethereumFee, 5)
            val totalInCurr = round(ethPriceInCurr.toDouble + (ethereumFee * t.state.runNow().ethereumPrice.toDouble), 2)

            t.modState(s => s.copy(
              etherTransaction = s.etherTransaction.copy(amount = value),
              amntInCurr = ethPriceInCurr, totalInCoin = totalIncoin, totalInCurr = totalInCurr)).runNow()

            changeInputButtonsVisibility(ethPriceInCurr, "usdTxtValue")
            changeInputButtonsVisibility(value, "coinTxtValue")

          case "amountUSD" =>
            val priceInETH = if (value != "0") round(value.toDouble / t.state.runNow().ethereumPrice.toDouble, 5)
            else ""
            val totalIncoin = if (value != "0") round(priceInETH.toDouble + ethereumFee, 5)
            else "0.0"
            val totalInCur = if (value != "0") round(value.toDouble + (ethereumFee * t.state.runNow().ethereumPrice.toDouble), 2) else "0.0"

            t.modState(s => s.copy(etherTransaction = s.etherTransaction.copy(amount = priceInETH), amntInCurr = value, totalInCoin = totalIncoin.toString,
              totalInCurr = totalInCur.toString)).runNow()

            changeInputButtonsVisibility(value, "usdTxtValue")
            changeInputButtonsVisibility(priceInETH, "coinTxtValue")
        }
      } else {
        t.modState(s => s.copy(etherTransaction = s.etherTransaction.copy(amount = ""), totalInCoin = "0.0", totalInCurr = "0.0", amntInCurr = "")).runNow()

        changeInputButtonsVisibility("", "usdTxtValue")
        changeInputButtonsVisibility("", "coinTxtValue")
      }
      Callback.empty
    }

    def round(value: Double, places: Int): String = {
      BigDecimal.apply(value).setScale(places, BigDecimal.RoundingMode.CEILING).toString()
    }

    def toAlphaNumeric(value: String): String = {
      val numPattern = "^[0-9]+(?:\\.[0-9]{0,5})?$".r
      val isValid = numPattern.findFirstIn(value)
      var str = value

      if (isValid == None) str = value.take(value.length - 1)

      str
    }

    def changeInputButtonsVisibility(value: String, inputId: String): Callback = {
      if (value != "") {
        if (!jQuery("#" + inputId + " ~  div").hasClass("maxButtonHidden")) jQuery("#" + inputId + " ~  div").addClass("maxButtonHidden")

        if (jQuery("#" + inputId + " +  i").hasClass("eraseButtonHidden")) jQuery("#" + inputId + " +  i").removeClass("eraseButtonHidden")
      } else {
        if (!jQuery("#" + inputId + " +  i").hasClass("eraseButtonHidden")) jQuery("#" + inputId + " +  i").addClass("eraseButtonHidden")

        if (!jQuery("#" + inputId + " ~  div").hasClass("maxButtonHidden")) jQuery("#" + inputId + " ~  div").addClass("maxButtonHidden")
      }

      Callback.empty
    }

    def fieldsValidation(): Boolean = {
      val ethTransaction = t.state.runNow().etherTransaction
      if (!ethTransaction.receiver.isEmpty) {
        if (!ethTransaction.amount.isEmpty) {
          val etherBalance = BigDecimal.apply(t.state.runNow().etherBalance)
          val inputAmount = BigDecimal.apply(t.state.runNow().etherTransaction.amount)
          t.state.runNow().etherTransaction.txnType match {
            case "eth" => {
              if (etherBalance > inputAmount) true else {
                Toastr.error("Ether balance is not sufficient to make transaction"); false
              }
            }
            case e =>
              val token = t.props.runNow().proxy.value.get.accountTokenDetails.find(_.contractAddress.equalsIgnoreCase(e))
              val tokenBalance = BigDecimal.apply(token.get.balance)
              if (tokenBalance > inputAmount) {
                if (inputAmount > BigDecimal.apply("0")) {
                  if (token.get.decimal >= inputAmount.scale)
                    true
                  else {
                    Toastr.error(s"Decimal must be less or equal to ${token.get.decimal}")
                    false
                  }
                } else {
                  Toastr.error("Ether balance is not sufficient to make token transaction")
                  false
                }
              } else {
                Toastr.error(s"Insufficient ${token.get.tokenName} tokens available to make current transaction")
                false
              }
          }
        } else {
          Toastr.error(s"Please provide amount you want to transfer to ${ethTransaction.receiver} public address")
          false
        }
      } else {
        Toastr.error("Please provide recipient's public key")
        false
      }
    }

    /*
    * opens a modal popup
    * */
    def sendTransaction(p: Props): Callback = {
      if (fieldsValidation()) {
        Callback {
          import com.livelygig.product.walletclient.facades.bootstrap.Bootstrap.bundle._
          val options = js.Object().asInstanceOf[ModalOptionsBackdropString]
          options.show = true
          options.keyboard = true
          options.backdrop = "static"
          jQuery("#confirmModal").modal(options)
        }
      } else {
        Callback.empty
      }
    }

    def onQRCodeClick(): Callback = {
      val amount = t.state.runNow().etherTransaction.amount
      dom.window.navigator.appVersion.contains("Android") match {
        case true => dom.window.location.href = s"#/captureqrnative/${amount}"
        //        case true => dom.window.location.href = "#/captureqrnative"
        case false => {
          val baseUrl = dom.window.location.href
          val updatedUrl = baseUrl.split("#").head
          dom.window.location.href = s"${updatedUrl}/captureQRCode"
        }
      }
      Callback.empty
    }

    def onSelectAccountChange(e: ReactEventFromInput): react.Callback = {
      jQuery("#lblDisplayFrom").text(e.target.value)
      Callback.empty
    }

    def renderTransaction(txn: String) = {
      <.li(^.className := "list-group-item list-group-item-info transactions-history-list")(
        <.div(^.className := "col-md-8 col-xs-8 transactions-message-div transaction-font-size")(txn),
        <.div(^.className := "col-xs-4 col-md-4")(
          if (txn.matches("0x[a-z-0-9]+")) {
            <.a(
              ^.className := "btn btn-success pull-right col-md-12 transactions-status-btn",
              ^.target := "_blank",
              ^.href := checkTransactionOnEtherScan(txn), "Status")
          } else {
            <.div(^.className := "btn btn-danger pull-right col-md-12 transactions-status-btn", "Error   ")
          }))
    }

    def checkTransactionOnEtherScan(txn: String): String = {
      s"https://ropsten.etherscan.io/tx/$txn"
    }

    def createDropDownItem(tokenSymbol: ERC20ComplientToken) = {
      <.option(^.value := tokenSymbol.contractAddress, tokenSymbol.tokenName)
    }

    /*
    * toggle user information block
    * */

    def toggleDropdownArrow(id: String) = Callback {
      jQuery(s"#$id").toggleClass("active")
    }

    def onEraseButtonClicked()(e: ReactEventFromInput): react.Callback = {
      calAmount("", "")

      Callback.empty
    }

    def render(p: Props, s: State): VdomElement =
      <.div(^.id := "bodyWallet")(
        ConfirmModal.component(ConfirmModal.Props(s.coinSymbol, s.etherTransaction, p.rc, ethereumFee.toString, "")),
        ^.className := "sendView",
        <.div(^.className := "scrollableArea")(
          <.div(
            ^.className := "row",
            <.h4(^.id := "headerFrom", ^.className := "col-xs-11 ellipseText", VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#fromOpt", "From ", ^.onClick --> toggleDropdownArrow("headerFrom"),
              <.i(^.className := "fa fa-chevron-down", VdomAttr("aria-hidden") := "true"),
              <.label(
                ^.id := "lblDisplayFrom")),
            <.div(
              ^.id := "fromOpt",
              ^.className := "col-xs-12 accountInfoFrom collapse",
              <.div(
                ^.className := "accountItem",
                <.label("Identity: "),
                <.select()(
                  accountInfo.accounts.map(e =>
                    <.option(e.accountName)).toTagMod
                //                  <.option(s"${accountInfo.accounts.find(_.address == accountInfo.selectedAddress).get.accountName}")
                )),
              <.div(
                ^.className := "accountItem",
                <.label("Account: "),
                <.div(
                  ^.id := "slctAccount", ^.className := "ellipseText",
                  ^.onChange ==> onSelectAccountChange,
                  s"0x${accountInfo.selectedAddress}")),
              <.div(
                ^.className := "accountItem",
                <.label("Token: "),
                t.props.runNow().proxy().render(e =>
                  <.select(^.onChange ==> onStateChange("txnType"))(
                    e.accountTokenDetails.filter(e => e.balance.equalsIgnoreCase("ETH") || e.balance > "0").reverseMap { ercToken =>
                      <.option(^.id := ercToken.contractAddress, ^.key := ercToken.contractAddress, ^.value := ercToken.contractAddress,
                        ercToken.tokenName)
                    }.toVdomArray))),
              <.div(
                ^.className := "accountSpendable",
                <.label("Spendable: "),
                <.div(
                  ^.className := "accountSpendableResult",
                  <.p(s"${s.etherBalance} ${s.coinSymbol}"))),
              <.div(
                ^.className := "accountSpendable",
                <.div(
                  ^.className := "accountSpendableResult",
                  s.coinExchange.filter(_.coin.equalsIgnoreCase(s.coinSymbol)).map { coin =>
                    coin.currencies.filter(_.symbol.equalsIgnoreCase(s.currSymbol)).map { e =>
                      <.p(s"${s.coinSymbol} = ${e.price} ${e.symbol}")
                    }.toVdomArray
                  }.toVdomArray)))),
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12 accountTo",
              <.h4("To"),
              <.input(^.`type` := "text", ^.placeholder := "Address",
                ^.className := "form-control ellipseText",
                VdomAttr("data-error") := "Recipient address required!",
                ^.required := true,
                ^.defaultValue := {
                  if (p.to != "") p.to.split("/").last.toString else s.etherTransaction.receiver
                },
                ^.onChange ==> onStateChange("receiver")),
              <.a(^.onClick --> onQRCodeClick, <.i(^.id := "captureQRCode", ^.className := "fa fa-qrcode", VdomAttr("aria-hidden") := "true")))),
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12 accountAmount",
              <.h4("Amount"),
              <.div(
                ^.className := "accountAmountItem",
                <.img(^.src := "/assets/images/ethereumIcon.png"),
                <.input(^.id := "coinTxtValue", ^.`type` := "text", ^.className := "form-control",
                  ^.required := true,
                  ^.value := s.etherTransaction.amount,
                  ^.onChange ==> onStateChange("amount"),
                  ^.onFocus ==> onFocusChange("coinTxtValue"),
                  ^.onBlur ==> onBlurChange("coinTxtValue")),
                <.i(^.className := "fa fa-times xicon btc eraseButtonHidden", VdomAttr("aria-hidden") := "true", ^.onClick ==> onEraseButtonClicked()),
                <.div(
                  ^.className := "maxButton maxButtonHidden",
                  ^.onClick ==> onMaxButtonClicked(),
                  <.p("MAX", ^.onClick ==> onMaxButtonClicked())),
                <.h5(s.coinSymbol)),
              <.div(
                ^.className := "accountAmountItem accountAmountItem-aprox",
                <.h4("≈"),
                <.i(^.className := "currency-icn")(s.coinExchange.filter(_.coin.equalsIgnoreCase(s.coinSymbol)).map { e =>

                  e.currencies.filter(_.symbol.equalsIgnoreCase(s.currSymbol)).map { e => e.icon }.toVdomArray
                }.toVdomArray) /*<.i(^.className := "fa fa-usd", VdomAttr("aria-hidden") := "true")*/ ,
                <.input(^.id := "usdTxtValue", ^.`type` := "text", ^.className := "form-control", ^.value := s.amntInCurr,
                  ^.onChange ==> onStateChange("amountUSD")),
                <.i(^.className := "fa fa-times xicon eraseButtonHidden", VdomAttr("aria-hidden") := "true", ^.onClick ==> onEraseButtonClicked()),
                <.h5(s.currSymbol))),
            <.div(
              ^.className := "col-xs-12 details",
              <.h4(^.id := "detailsmain", VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#detailGroup", "Details ", ^.onClick --> toggleDropdownArrow("detailsmain"),
                <.i(^.className := "fa fa-chevron-down", VdomAttr("aria-hidden") := "true")),
              <.div(^.id := "detailGroup", ^.className := "collapse",
                <.table(
                  <.tbody(
                    <.tr(
                      <.td("Fee"),
                      <.td("≈"),
                      <.td(
                        ^.className := "fee",
                        {
                          ethereumFee
                        }),
                      <.td("ETH")),
                    <.tr(
                      <.td(
                        <.p("Total")),
                      <.td(
                        <.p("≈")),
                      <.td(
                        <.p(^.id := "ethTotal", {
                          s.totalInCoin
                        })),
                      <.td(
                        <.p({
                          s.coinSymbol
                        }))),
                    <.tr(
                      <.td(),
                      <.td(
                        <.p("≈")),
                      <.td(
                        <.p(^.id := "usdTotal", {
                          s.totalInCurr
                        })),
                      <.td(
                        <.p({
                          s.currSymbol
                        }))))))),
            <.div(
              ^.className := "accountAmount passwordSection",
              <.h4("Password"),
              <.div(
                ^.className := "accountAmountItem",
                <.i(^.className := "fa fa-lock", VdomAttr("aria-hidden") := "true"),
                <.input(^.`type` := "text", ^.className := "form-control", ^.defaultValue := s.etherTransaction.password,
                  ^.required := true,
                  ^.onChange ==> onStateChange("password")),
                <.i(^.className := "fa fa-times xicon btc", VdomAttr("aria-hidden") := "true"))))),
        <.div(
          ^.className := "container btnDefault-container container-NoBorder",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.button(^.id := "sndBtnId", ^.className := "btn btnDefault", ^.tpe := "button", ^.onClick --> sendTransaction(p), VdomAttr("aria-hidden") := "true", "Send")))))
  }

  val component = ScalaComponent.builder[Props]("SendView")
    .initialState(State(EtherTransaction("", "", "", "eth", 0), "", "0", "ETH", "USD", "0"))
    .renderBackend[Backend]
    .componentWillMount(scope => scope.backend.updateCurrency())
    .componentDidMount(scope => scope.backend.getLiveCurrencyUpdate())
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}

