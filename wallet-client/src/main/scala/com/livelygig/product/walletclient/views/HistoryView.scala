package com.livelygig.product.walletclient.views

import com.livelygig.product.shared.models.wallet.{ Transaction, TransactionWithSymbol }
import com.livelygig.product.walletclient.handler.GetAccountHistoryDetails
import com.livelygig.product.walletclient.rootmodel.TransactionRootModel
import com.livelygig.product.walletclient.services.WalletCircuit
import com.livelygig.product.walletclient.views.modals.FilterModal
import diode.AnyAction._
import diode.data.Pot
import diode.react.ModelProxy
import diode.react.ReactPot._
import japgolly.scalajs.react.vdom.html_<^.{ EmptyVdom, ^, _ }
import japgolly.scalajs.react.{ BackendScope, Callback, ScalaComponent }
import moment.Moment
import org.querki.jquery.$
import org.scalajs.dom
import org.scalajs.dom.raw.Element

object HistoryView {

  case class Props(proxy: ModelProxy[Pot[TransactionRootModel]])

  //final case class State()

  final class Backend(t: BackendScope[Props, Unit]) {

    def updateTheme(): Callback = {
      val theme = if (dom.window.localStorage.getItem("theme") == null) "default" else dom.window.localStorage.getItem("theme")
      $("#theme-stylesheet")
        .foreach((ele: Element) =>
          ele
            .setAttribute("href", s"../assets/stylesheets/wallet/themes/wallet-main-theme-${theme}.min.css"))
      Callback.empty
    }

    def componentDidMount(props: Props): Callback = {
      Callback.when(!props.proxy().isPending)(props.proxy.dispatchCB(GetAccountHistoryDetails()))
    }

    def render(p: Props): VdomElement = {
      val userProxy = WalletCircuit.zoom(_.user)
      def createItem(transactionWithSymbol: TransactionWithSymbol) = {
        try {
          val isAmtDeducted = if (transactionWithSymbol.transaction.to.equals(userProxy.value.userDetails.walletDetails.publicKey)) false else true
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-6 col-md-6 col-sm-6 col-xs-6",
              <.label(Moment(s"${transactionWithSymbol.transaction.timeStamp}000", "x").format("llll"))),
            <.div(
              ^.className := "col-lg-6 col-md-6 col-sm-6 col-xs-6 section-right",
              <.label(^.className := "notAlpha", transactionWithSymbol.transaction.value),
              <.span((^.className := "negative").when(isAmtDeducted), (^.className := "positive").when(!isAmtDeducted))(s"${transactionWithSymbol.transaction.value} ${transactionWithSymbol.symbol}")),
            <.p(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12 ellipseText",
              (s"${transactionWithSymbol.transaction.from}").when(!isAmtDeducted),
              (s"${transactionWithSymbol.transaction.to}").when(isAmtDeducted)))
        } catch {
          case e: Exception =>
            println(e)
            EmptyVdom
        }
      }
      //ScalaTags goes here
      <.div(^.id := "bodyWallet")(
        FilterModal.component(FilterModal.Props()),
        <.div(
          ^.className := "payment-history",
          <.div(
            ^.className := "payment-select row",
            <.div(
              ^.className := "col-xs-10 notAlpha",
              <.label("Display currency: "),
              <.select(
                <.option("Rev"),
                <.option("Rev 1"),
                <.option("Rev 2"))),
            <.div(
              ^.className := "col-xs-2 notAlpha",
              <.i(^.className := "fa fa-filter", VdomAttr("aria-hidden") := "true", VdomAttr("data-toggle") := "modal", VdomAttr("data-target") := "#filterModal"))),
          <.div(
            ^.className := "payment-section-bottom",
            <.div(
              ^.className := "payment-section-bottom-head",
              <.div(
                ^.className := "row",
                <.div(
                  ^.className := "col-lg-7 col-md-6 col-sm-7 col-xs-4 his-left",
                  <.h3("Date Time"),
                  <.h3("From / To")),
                <.div(
                  ^.className := "col-lg-5 col-md-6 col-sm-5 col-xs-8 his-right",
                  <.h3("Balance", ^.className := "notAlphaV"),
                  <.h3("Transaction amount")))),
            p.proxy().render(txnList =>

              <.div(^.className := "payment-section-bottom-midd")(
                <.span()(txnList.accountTransactionHistory reverseMap createItem: _*).when(!txnList.accountTransactionHistory.isEmpty),
                <.label(^.className := "warn-text", "No transactions   available").when(txnList.accountTransactionHistory.isEmpty))),
            p.proxy().renderFailed(ex => <.div(^.className := "payment-section-bottom-midd")(
              <.label(^.className := "warn-text", "Error while loading transaction history "))),
            p.proxy().renderPending(e =>
              <.div()(
                <.img(^.src := "../assets/images/processing-img.svg", ^.className := "loading-img"))))),
        <.div(
          ^.className := "container btnDefault-container notAlpha",
          <.h5("Click to view or annotate"),
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",

              <.button(^.className := "btn btnDefault goupButton", "Export"),
              <.button(^.className := "btn btnDefault goupButton", "Share")))))
    }
  }

  val component = ScalaComponent.builder[Props]("HistoryView")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.updateTheme())
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build
  def apply(props: Props) = component(props)

}
