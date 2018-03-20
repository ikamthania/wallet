package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.facades.{ EthereumJSWallet, HDKey, Mnemonic }
import com.livelygig.product.walletclient.router.ApplicationRouter.{ Loc }
import com.livelygig.product.walletclient.services.WalletCircuit
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{ BackendScope, Callback, ScalaComponent }
import scala.util.Random

//object ConfirmBakupPhrase {
//
//  case class Props(router: RouterCtl[Loc])
//
//  final case class State(phraseSelection: Seq[String],
//                         phraseSelected: Seq[String],
//                         isValidPhrase: Boolean, showLoader: Boolean,
//                         phraseWords :Seq[String]= WalletCircuit.zoom(_.user.userDetails.phrase).value)
//
//
//  final class Backend(t: BackendScope[Props, State]) {
//
//    def componentDidMount(props: Props): Callback = {
//      t.modState(s => s.copy(phraseSelection = t.state.runNow().phraseWords)).runNow()
//      Callback.empty
//    }
//
//    lazy val getAccountNumber = {
//      val wallets = WalletCircuit.zoomTo(_.appRootModel.appModel.data.keyrings.accounts).value
//      if (wallets.nonEmpty){
//        wallets.length -1
//      } else {
//        0
//      }
//    }
//
//    def onBtnClicked(): react.Callback = {
//      if (t.state.runNow().phraseWords.equals(t.state.runNow().phraseSelected.filter(_.nonEmpty))) {
//        val mnemonic=new Mnemonic()
//        val addrNode =HDKey.fromMasterSeed(mnemonic.toSeed(t.state.runNow()
//          .phraseWords.mkString(" ")))
//          .derive(s"m/44'/60'/0'/${getAccountNumber}")
//        // store vault and update the root model
//        val ethereumJSWallet = EthereumJSWallet.fromExtendedPrivateKey(addrNode.publicExtendedKey)
////        val newWallet = Wallet(ethereumJSWallet.getAddressString(), addrNode.privateExtendedKey, addrNode.publicExtendedKey)
////        WalletCircuit.dispatch(AddNewAccount(Account(newWallet.publicKey, s"Account ${getAccountNumber + 1}")))
//        /*BrowserPassworder.addWalletToVault(newWallet, t.props.runNow().password).map{
//          _ =>
//            t.props.flatMap(_.router.set(ConfirmedBackupPhraseLoc)).runNow()
//        }*/
//        t.modState(s => s.copy(showLoader = true))
//
//      } else {
//        //        todo add validator for mnemonic phrase
//        t.modState(s => s.copy(isValidPhrase = true))
//      }
//    }
//
//    def generateWordList(e: String):VdomElement = {
//
//      <.li(^.onClick-->generateWordListSelected(e),e)
//    }
//
//    def generateWordListSelected(e: String) = {
//      if (t.state.runNow().phraseSelected.contains(e)){
//      val selected = t.state.runNow().phraseSelection :+ e
//      val diselect = t.state.runNow().phraseSelected.filterNot(_.equals(e))
//      t.modState(s => s.copy(phraseSelected = diselect, phraseSelection = selected))
//    }
//      else if(t.state.runNow().phraseSelection.contains(e)){
//      val selected = t.state.runNow().phraseSelected :+ e
//      val diselect = t.state.runNow().phraseSelection.filterNot(_.equals(e))
//      t.modState(s => s.copy(phraseSelected = selected, phraseSelection = diselect))
//
//    } else {
//        Callback.empty
//      }
//    }
//
//
//    def render(p: Props, s: State): VdomElement = {
//      <.div(
//        <.div(^.className := "wallet-inner-navigation",
//          <.div(^.className := "row",
//            <.div(^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
//              <.div(^.className := "wallet-information",
//                <.h2("Wallet"),
//                <.h3("Back up account")
//              )
//            )
//          )
//        ),
//      <.div(
//        ^.className := "backupView-phrase",
//        <.div(
//          ^.className := "row",
//          <.div(
//            ^.className := "col-xs-12",
//            <.h3("Confirm your back-up phrase"),
//            <.div(
//              ^.className := "row backupPhrase-section",
//              <.ul(
//                ^.id := "phrase-container ",
//                ^.className := "col-xs-12 backupPhrase-container mnemonic-phrase-box")(s.phraseSelected.filter(_.nonEmpty) map generateWordList: _*)),
//            if (s.isValidPhrase) {
//              <.div(^.className := "alert alert-danger alert-dismissible fade in",
//                <.a(^.href := "#", ^.className := "close", VdomAttr("data-dismiss") := "alert", VdomAttr("aria-label") := "close","×"),
//                <.strong("Mnemonic-phrase not matched!!!"),
//              )
//            } else
//              <.div())),
//        <.div(
//          ^.className := "row",
//          <.div(
//            ^.className := "col-xs-12 wordsList",
//            <.ul(
//              ^.id := "mnemonic-list")(Random.shuffle(s.phraseSelection.filter(_.nonEmpty)) map generateWordList: _*))),
//
//        <.div(
//          ^.className := "container btnDefault-container",
//          <.div(
//            ^.className := "row",
//            <.div(
//              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
//              <.button(
//                ^.id := "btnNextStart",
//                ^.name := "btnNxt",
//                ^.`type` := "button",
//                VdomAttr("data-toggle") := "modal",
//                ^.className := "btn btnDefault goupButton setdefault",
//                ^.onClick --> onBtnClicked(),
//                "Next"))))))
//    }
//  }
//
//  val component = ScalaComponent.builder[Props]("ConfirmBakupPhrase")
//    .initialState(State(Seq(""),Seq(""), false, false))
//    .renderBackend[Backend]
//    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
//    .build
//
//  def apply(props: Props) = component(props)
//}

