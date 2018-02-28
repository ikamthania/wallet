/*
package com.livelygig.product.walletclient.views

import com.livelygig.product.shared.models.wallet.{EtherTransaction, UserDetails, WalletDetails}
import com.livelygig.product.walletclient.services.CoreApi
import Toastr
import japgolly.scalajs.react
import japgolly.scalajs.react.vdom.html_<^.{<, _}
import japgolly.scalajs.react.{Callback, _}
import org.querki.jquery.$
import play.api.libs.json.Json

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js

object TransactionView {

  case class Props()

  final case class State(etherTransaction: EtherTransaction, userUri: String, etherBalance: Double, transactions: Seq[String])

  final class Backend(t: BackendScope[Props, State]) {

    def getTransactionNotification(txnHash: String): Unit = {
      js.timers.setTimeout(5000) {
        CoreApi.getTransactionStatus(txnHash).map { txnResponse =>
          if (txnResponse.matches("0x[a-z-0-9]+")) {
            getEtherBalance()
            Toastr.success(s"$txnResponse block confirmed for transaction $txnHash", Some("Transaction completed!!!"))
          } else
            getTransactionNotification(txnHash)
        }
      }
    }

    def getEtherBalance() = {
      CoreApi.getUserDetails().map {
        user =>
          val walletDetails = Json.parse(user).validate[UserDetails].get
          t.modState(s => s.copy(etherBalance = walletDetails.walletDetails.balance.toDouble)).runNow()
      }
    }

    def componentDidMount() = Callback {
      getEtherBalance()
      //      getNotification()
    }

    def onStateChange(value: String)(e: ReactEventFromInput): react.Callback = {
      val newValue = e.target.value
      value match {
        case "amount" => t.modState(s => s.copy(etherTransaction = s.etherTransaction.copy(amount = newValue)))
        case "password" => t.modState(s => s.copy(etherTransaction = s.etherTransaction.copy(password = newValue)))
        case "receiver" => t.modState(s => s.copy(etherTransaction = s.etherTransaction.copy(receiver = newValue)))
      }
    }
    def balanceValidation(etherBalance: Double): Boolean = {
      t.state.runNow().etherBalance > etherBalance
    }

    def sendTransactionBtn(e: ReactEventFromInput): react.Callback = {
      e.preventDefault()
      $("#sndBtnId").attr("disabled", true)
      js.timers.setTimeout(2000) {
        $("#sndBtnId").attr("disabled", false)
      }
      if (balanceValidation(t.state.runNow().etherTransaction.amount.toDouble)) {
        CoreApi.authenticate.map {
          userUri =>
            CoreApi
              .sendEtherTransaction(t.state.runNow().etherTransaction)
              .map { transactionHashString =>
                if (transactionHashString.matches("0x[a-z-0-9]+"))
                  getTransactionNotification(transactionHashString)
                else
                  Toastr.error(transactionHashString, Some("Encountered error while processing"))
                t.modState(s => s.copy(transactions = s.transactions :+ transactionHashString)).runNow()
              }
        }
      } else { alertPopup("Insufficient funds") }
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
              ^.href := checkTransactionOnEtherScan(txn), "Status"
            )
          } else {
            <.div(^.className := "btn btn-danger pull-right col-md-12 transactions-status-btn", "Error   ")
          }
        )
      )
    }

    def checkTransactionOnEtherScan(txn: String): String = {
      s"https://ropsten.etherscan.io/tx/$txn"
    }

    def alertPopup(message: String) = {
      Toastr.info(message)
    }

    def render(p: Props, s: State): VdomElement =
      <.div()(

        <.div(^.className := "col-md-12 transaction-view-margin transaction-font-size")(
          <.label(s"Ether Balance in WEI : "),
          (s.etherBalance)
        ),

        <.form(^.onSubmit ==> sendTransactionBtn)(

          <.div(
            ^.className := "col-xs-12 col-md-8 form-group transaction-font-size",
            <.label("To Address"),
            <.input(
              ^.className := "form-control",
              VdomAttr("data-error") := "Recipient address required!",
              ^.`type` := "text",
              ^.placeholder := "0x7cB57B5A97eAbe94205C07890BE4c1aD31E486A8",
              ^.required := true,
              ^.defaultValue := s.etherTransaction.receiver,
              ^.onChange ==> onStateChange("receiver")
            )
          ),

          <.div(
            ^.id := "amount",
            ^.className := "col-xs-12 col-md-4 form-group transaction-font-size",
            VdomAttr("data-error") := "Ether amount required!",
            <.label("Amount"),
            <.input(
              ^.className := "form-control",
              ^.`type` := "text",
              ^.placeholder := "Ether amount",
              ^.defaultValue := s.etherTransaction.amount,
              ^.required := true,
              ^.onChange ==> onStateChange("amount")
            )
          ),
          <.div(
            ^.id := "password",
            ^.className := "col-xs-12 col-md-4 form-group transaction-font-size",
            VdomAttr("data-error") := "Password required!",
            <.label("Password"),
            <.input(
              ^.className := "form-control",
              ^.`type` := "password",
              ^.placeholder := "password",
              ^.defaultValue := s.etherTransaction.password,
              ^.required := true,
              ^.onChange ==> onStateChange("password")
            )
          ),

          <.div(^.className := "col-md-4 col-md-offset-8 col-xs-12 ")(
            <.button(
              ^.id := "sndBtnId",
              ^.className := "btn btn-primary col-md-12 col-xs-12",
              ^.tpe := "submit"
            )("Send")
          )
        ),

        <.div(^.className := "col-md-12 col-xs-12")(
          <.label(^.className := "transaction-view-margin transaction-font-size")("Transactions:")
        ),

        <.ul(^.className := "list-group col-md-12  col-xs-12 transactions-list-alignment")(s.transactions reverseMap renderTransaction: _*)
      )
  }

  val component = ScalaComponent.builder[Props]("TransactionView")
    .initialState(State(EtherTransaction("", "", "" /*, ""*/ ), "", 0, Seq()))
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount())
    .build

  def apply(props: Props) = component(props)

}

*/
