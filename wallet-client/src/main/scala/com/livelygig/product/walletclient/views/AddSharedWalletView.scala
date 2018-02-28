
package com.livelygig.product.walletclient.views
import com.livelygig.product.shared.models.Contracts.MultiSigWalletWithDailyLimit
import com.livelygig.product.shared.models.Solidity._
import com.livelygig.product.shared.models.wallet.EtherTransaction
import japgolly.scalajs.react
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.querki.jquery.$

import scala.scalajs.js
import japgolly.scalajs.react.extra.router.RouterCtl
import com.livelygig.product.walletclient.router.ApplicationRouter
import com.livelygig.product.walletclient.facades.Bootstrap._
import com.livelygig.product.walletclient.facades.{ EthereumjsABI, EthereumjsUnits }
import com.livelygig.product.walletclient.modals.ConfirmModal
import com.livelygig.product.walletclient.facades.EthereumjsUnits

object AddSharedWalletView {

  final case class Props(rc: RouterCtl[ApplicationRouter.Loc]) {
    @inline def render: VdomElement = component(this)
  }

  final case class State(
    sharedWalletName: String,
    requiredConfirmations: Int,
    dailyLimitEth: BigDecimal,
    ownerName: String,
    ownerAddress: String,
    owners: List[Owner],
    etherTransaction: EtherTransaction)

  final case class Owner(
    name: String,
    address: Address)

  object State {
    def init: State =
      State("", 2, 0, "", "", Nil, EtherTransaction("", "", "", "", 0))
  }

  def onSelectOwner(e: ReactEventFromHtml): react.Callback = {
    e.preventDefault()
    $(".walletData").removeClass("active")
    if (!$(e.target).is(".walletData")) {
      $(e.target).parents(".walletData").addClass("active")
    } else {
      $(e.target).addClass("active")
    }
    Callback.empty
  }

  val OwnerList = ScalaFnComponent[List[Owner]] { props =>
    def createOwner(owner: Owner) =
      <.div(
        ^.className := "row walletData", ^.onClick ==> onSelectOwner,
        <.div(
          ^.className := "col-lg-4 col-md-4 col-sm-4 col-xs-4",
          <.p(^.className := "ownerName ellipseText", owner.name)),
        <.div(
          ^.className := "col-lg-6 col-md-6 col-sm-6 col-xs-6",
          <.p(^.className := "ownerAddress ellipseText", owner.address.toString())),
        <.div(
          ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2",
          <.button(^.`type` := "button", ^.className := "btn btnDefault btnAction ",
            <.i(^.className := "fa fa-times", VdomAttr("aria-hidden") := "true"))))
    <.div(props map createOwner: _*)
  }

  final class Backend(t: BackendScope[Props, State]) {

    def onNameChange(e: ReactEventFromInput) = {
      val newValue = e.target.value
      t.modState(_.copy(sharedWalletName = newValue))
    }

    def onRequiredConfirmationsChange(e: ReactEventFromInput) = {
      val newValue = e.target.valueAsNumber
      t.modState(_.copy(requiredConfirmations = newValue))
    }

    def onDailyLimitETHChange(e: ReactEventFromInput) = {
      val newValue = e.target.value
      t.modState(_.copy(dailyLimitEth = BigDecimal(newValue)))
    }

    def onOwnerNameChange(e: ReactEventFromInput) = {
      val newValue = e.target.value
      t.modState(_.copy(ownerName = newValue))
    }

    def onOwnerAddressChange(e: ReactEventFromInput) = {
      val newValue = e.target.value
      t.modState(_.copy(ownerAddress = newValue))
    }

    def addOwner(e: ReactEventFromInput) = {
      t.modState(
        s =>
          if (s.owners.exists(_.address == s.ownerAddress)) {
            //TODO: display some error, we can't have two owners with the same address
            s
          } else {
            State(
              s.sharedWalletName,
              s.requiredConfirmations,
              s.dailyLimitEth,
              "", "",
              s.owners :+ Owner(s.ownerName, new Address(s.ownerAddress)),
              s.etherTransaction)
          })
    }

    def toggleDropdownArrow(id: String) = Callback {
      $(s"#$id").toggleClass("active")
    }

    def createSharedWallet(): Callback = {
      t.modState(
        s => {
          val ownerAddresses: Array[Address] = new Array(s.owners.map(_.address))
          val required = new Uint(s.requiredConfirmations)
          val dailyLimit = new Uint(EthereumjsUnits.convert(s.dailyLimitEth.toString, "eth", "wei"))
          val multiSigWallet = new MultiSigWalletWithDailyLimit(ownerAddresses, required, dailyLimit)
          val encodedConstructor: String = EthereumjsABI.rawEncode(multiSigWallet.constructorArgs).toString()

          $("#confirmModal").modal(js.Dynamic.literal("backdrop" -> "static", "keyboard" -> true, "show" -> true))
          s.copy(etherTransaction = EtherTransaction("", "", "", MultiSigWalletWithDailyLimit.BYTE_CODE + encodedConstructor, 0))
        })
    }

    def render(p: Props, state: State): VdomElement =
      <.div(^.id := "bodyWallet")(
        ConfirmModal.component(ConfirmModal.Props("ETH", state.etherTransaction, p.rc, "0.01", "Create Shared Wallet")),
        <.div(
          ^.className := "addSharedWalletMain scrollableArea row",
          <.div(
            ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
            <.h4("Shared wallet name"),
            <.input.text(
              ^.value := state.sharedWalletName,
              ^.onChange ==> onNameChange,
              ^.className := "form-control ellipseText",
              VdomAttr("data-error") := "Wallet name is required!",
              ^.required := true)),
          <.div(
            ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
            <.h4("Required confirmations"),
            <.input.number(
              ^.value := state.requiredConfirmations,
              ^.onChange ==> onRequiredConfirmationsChange,
              ^.className := "form-control ellipseText",
              VdomAttr("data-error") := "Number of confirmation is required!",
              ^.required := true)),
          <.div(
            ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
            <.h4("Daily limit (ETH)"),
            <.input.number(
              ^.value := state.dailyLimitEth.toString,
              ^.onChange ==> onDailyLimitETHChange,
              ^.placeholder := "0 (unlimited)",
              ^.className := "form-control ellipseText",
              VdomAttr("data-error") := "Daily limit is required!",
              ^.required := true)),
          <.div(
            ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
            <.h4("Add owner")),
          <.div(
            ^.className := "col-lg-4 col-md-4 col-sm-4 col-xs-4",
            <.input.text(
              ^.value := state.ownerName,
              ^.onChange ==> onOwnerNameChange,
              ^.placeholder := "Name",
              ^.className := "form-control ellipseText",
              VdomAttr("data-error") := "Name is required!",
              ^.required := true)),
          <.div(
            ^.className := "col-lg-6 col-md-6 col-sm-6 col-xs-6",
            <.div(
              ^.className := "addressSection",
              <.input.text(
                ^.value := state.ownerAddress,
                ^.onChange ==> onOwnerAddressChange,
                ^.placeholder := "Address",
                ^.className := "form-control ellipseText",
                VdomAttr("data-error") := "Address is required!",
                ^.required := true),
              <.i(^.className := "fa fa-qrcode", VdomAttr("aria-hidden") := "true"))),
          <.div(
            ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2",
            <.button(
              ^.onClick ==> addOwner,
              ^.className := "btn btnDefault btnAction active",
              <.i(^.className := "fa fa-plus", VdomAttr("aria-hidden") := "true"))),
          <.div(
            ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
            <.div(
              ^.className := "ownersList",
              <.h4(^.id := "oList", "Owners "),
              <.div(
                ^.id := "ownerListSection",
                <.div(
                  ^.className := "row",
                  <.div(
                    ^.className := "col-lg-4 col-md-4 col-sm-4 col-xs-4",
                    <.p(^.className := "ellipseText", "Name")),
                  <.div(
                    ^.className := "col-lg-6 col-md-6 col-sm-6 col-xs-6",
                    <.p(^.className := "ellipseText", "Address")),
                  <.div(
                    ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2")),
                OwnerList(state.owners))))),
        <.div(
          ^.className := "container btnDefault-container",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.button(^.`type` := "button", ^.className := "btn btnDefault", "Create", ^.onClick --> createSharedWallet())))))
  }

  val component = ScalaComponent.builder[Props]("AddSharedWalletView")
    .initialState(State.init)
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}
