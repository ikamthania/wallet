package com.livelygig.product.walletclient.modals

import com.livelygig.product.shared.models.wallet._
import com.livelygig.product.walletclient.facades._
import com.livelygig.product.walletclient.handler.UpdatePassword
import com.livelygig.product.walletclient.router.ApplicationRouter
import com.livelygig.product.walletclient.router.ApplicationRouter.{ AccountLoc, LandingLoc }
import com.livelygig.product.walletclient.services.{ CoreApi, EthereumNodeApi, WalletCircuit }
import japgolly.scalajs.react
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }
import play.api.libs.json.Json
import diode.AnyAction._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

object ConfirmModal {

  case class Props(
    symbol: String = "",
    etherPropsTransaction: EtherTransaction = EtherTransaction("", "0Xabc12ad...", "0.0010", "", 18),
    rc: RouterCtl[ApplicationRouter.Loc],
    ethFees: String,
    txType: String = "")

  final case class State(etherTransaction: EtherTransaction)

  final class Backend(t: BackendScope[Props, State]) {
    Toastr.options.timeOut = 10000; // How long the toast will display without user interaction
    Toastr.options.extendedTimeOut = 100; // How long the toast will display after a user hovers over it
    Toastr.options.closeButton = true
    Toastr.options.positionClass = "toast-top-full-width"

    val ercTokenList = WalletCircuit.zoom(_.ERCToken.get.accountTokenDetails)
    /*
    * get imput password and update EtherTransaction
    * */

    def confirmMessage() = {
      val p = t.props.runNow()
      val s = t.state.runNow()

      <.div(
        ^.className := "confirmContainer",
        <.div(
          ^.className := "messageContainer",
          <.h5(^.className := "amtAddressContainer")(s"Please confirm the send of ${p.etherPropsTransaction.amount} " +
            s"${p.symbol.toUpperCase} + ${p.ethFees} ETH fees to ${p.etherPropsTransaction.receiver} .")),
        <.div(
          <.input(^.id := "txAmount", ^.`type` := "hidden", ^.value := p.etherPropsTransaction.amount, ^.onChange ==> updatePassword, ^.className := ""),
          <.input(^.id := "txTo", ^.`type` := "hidden", ^.value := p.etherPropsTransaction.receiver, ^.onChange ==> updatePassword, ^.className := ""),
          <.input(^.id := "txSymbol", ^.`type` := "hidden", ^.value := p.symbol.toUpperCase, ^.onChange ==> updatePassword, ^.className := ""),
          ^.className := "passwordContainer",
          <.p(s" You are sending from: ${WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo.selectedAddress).value} To confirm, enter your application password."),
          <.input(^.id := "txPassword", ^.`type` := "password", ^.value := s.etherTransaction.password, ^.onChange ==> updatePassword, ^.className := ""),
          <.i(^.className := "fa fa-lock", VdomAttr("aria-hidden") := "true", VdomAttr("data-dismiss") := "modal")))

    }

    def createSharedWalletMessage(txType: String, fee: String, s: State) = {
      <.div(
        ^.className := "confirmContainer",
        <.div(
          ^.className := "messageContainer",
          <.h5(^.className := "amtAddressContainer")(s"Transaction type: ${txType} " +
            s"Fee: ${fee} ETH.")),
        <.div(
          ^.className := "passwordContainer",
          <.p(s"To approve the transaction, enter your application password"),
          <.input(^.id := "txPassword", ^.`type` := "password", ^.value := s.etherTransaction.password, ^.onChange ==> updatePassword, ^.className := ""),
          <.i(^.className := "fa fa-lock", VdomAttr("aria-hidden") := "true", VdomAttr("data-dismiss") := "modal")))
    }

    def updatePassword(e: ReactEventFromInput): react.Callback = {
      val newValue = e.target.value
      val props = t.props.runNow()
      t.modState(s => s.copy(etherTransaction = EtherTransaction(password = newValue, receiver = props.etherPropsTransaction.receiver, amount = props.etherPropsTransaction.amount, props.etherPropsTransaction.txnType, decimal = props.etherPropsTransaction.decimal)))
    }

    /*
    *  check transaction success or failure
    * */

    def getTransactionNotification(txnHash: String): Unit = {
      js.timers.setTimeout(5000) {
        CoreApi.getTransactionStatus(txnHash).map { txnResponse =>
          if (txnResponse.matches("0x[a-z-0-9]+")) {
            Toastr.success(s"$txnResponse block confirmed for transaction $txnHash", Some("Transaction completed!!!"))
          } else
            getTransactionNotification(txnHash)
        }
      }
    }

    /*
    * check balance before send it to another account
    * */

    /*
    *   initiate send ether transaction
    * */

    def sendTransaction(e: ReactEventFromHtml): react.Callback = Callback {
      VaultGaurd.decryptVault(t.state.runNow().etherTransaction.password).map { e =>
        WalletCircuit.dispatch(UpdatePassword(t.state.runNow().etherTransaction.password))
        val accountInfo = WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo).value
        val hdKey = HDKey.fromExtendedKey(e.privateExtendedKey)
        val slctedAccntIndex = accountInfo.accounts.indexWhere(_.address == accountInfo.selectedAddress)
        val prvKey = hdKey.derive(s"${e.hdDerivePath}/${slctedAccntIndex}").privateKey.toString("hex")
        EthereumNodeApi.getTransactionCount(s"0x${accountInfo.selectedAddress}").map {
          res =>
            val nonce = (Json.parse(res) \ "result").as[String]
            val etherTxn = t.state.runNow().etherTransaction.copy(password = "")
            val signedTxn = WalletJS.getSignTxn(
              prvKey,
              s"0x${BigDecimal.apply(EthereumjsUnits.convert(etherTxn.amount, "eth", "wei")).toBigInt().toString(16)}", etherTxn.receiver,
              etherTxn.txnType, nonce, "0x0", "0x4E3B29200", "0x3D0900")

            if (signedTxn != "") {
              CoreApi
                .mobileSendSignedTxn(s"0x${signedTxn}")
                .map { transactionHashString =>
                  if (transactionHashString.matches("0x[a-z-0-9]+")) {
                    Toastr.info(s"Transaction sent. Transaction reference no. is $transactionHashString")
                    getTransactionNotification(transactionHashString)
                    t.props.runNow().rc.set(AccountLoc).runNow()
                  } else {
                    Toastr.error(transactionHashString)
                    Callback.empty
                  }
                }
            } else {

              Toastr.info("Please try again....")
            }
        }
      }.recover {
        case e: Exception =>
          println(e)
          Toastr.error("Wrong password!!!!")
      }
    }

    def render(p: Props, s: State): VdomElement =
      <.div(
        <.div(^.className := "modal fade", ^.id := "confirmModal", ^.role := "dialog", VdomAttr("aria-labelledby") := "exampleModalLabel", VdomAttr("aria-hidden") := "true",
          <.div(^.className := "modal-dialog", ^.role := "document",
            <.div(
              ^.className := "modal-content",
              <.div(
                ^.className := "modal-header",
                <.div(
                  ^.className := "row",
                  <.h5(^.className := "modal-title col-xs-6", ^.id := "exampleModalLabel", "Confirm"),
                  <.button(^.`type` := "button", ^.className := "close col-xs-1 col-xs-offset-5", VdomAttr("data-dismiss") := "modal", VdomAttr("aria-label") := "Close",
                    <.span(VdomAttr("aria-hidden") := "true", "×")))),
              <.div(
                ^.className := "modal-body",
                <.div(
                  ^.className := "container",
                  <.div(
                    ^.className := "row",
                    if (p.txType == "") confirmMessage()
                    else createSharedWalletMessage(p.txType, p.ethFees, s))),
                <.div(
                  ^.className := "modal-footer",
                  <.button(^.id := "txConfirmBtn", ^.`type` := "button", ^.className := "btn btnDefault", ^.onClick ==> sendTransaction, VdomAttr("data-dismiss") := "modal", "Confirm")))))))
  }

  val component = ScalaComponent.builder[Props]("ConfirmModal")
    .initialState(State(EtherTransaction("", "", "", "eth", 0)))
    .renderBackend[Backend]
    .componentWillReceiveProps(scope => scope.setState(State(etherTransaction = scope.nextProps.etherPropsTransaction)))
    .build
  def apply(props: Props) = component(props)
}

