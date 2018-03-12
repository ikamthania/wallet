package com.livelygig.product.walletclient.components

import com.livelygig.product.walletclient.facades.Blockies
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.product.walletclient.handler.ChangeLang
import com.livelygig.product.walletclient.router.ApplicationRouter.{ Loc, _ }
import com.livelygig.product.walletclient.services.{ CoreApi, WalletCircuit }
import com.livelygig.product.walletclient.utils.I18N
import diode.AnyAction._
import diode.ModelRO
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.{ Resolution, RouterCtl }
import japgolly.scalajs.react.vdom.html_<^.{ ^, _ }
import japgolly.scalajs.react.{ BackendScope, Callback, ScalaComponent, _ }
import org.scalajs.dom

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.util.{ Failure, Success }

object Sidebar {

  val userDetails = WalletCircuit.zoom(_.user.userDetails)

  case class Props(c: RouterCtl[Loc], r: Resolution[Loc])

  case class State(lang: js.Dynamic = WalletCircuit.zoom(_.i18n.language).value, ethNetInfo: String = "", walletPublicKey: String = "")

  def getHeaderName(currentLoc: Loc, state: State) = {
    currentLoc match {
      case AccountLoc => state.lang.selectDynamic("HOME").toString
      case SendLoc => state.lang.selectDynamic("SEND").toString
      case HistoryLoc => state.lang.selectDynamic("HISTORY").toString
      case RequestLoc => state.lang.selectDynamic("REQUEST").toString
      case IdentitiesLoc => state.lang.selectDynamic("IDENTITIES").toString
      case ManageIdentitiesLoc => state.lang.selectDynamic("MANAGE").toString
      case NotificationLoc => state.lang.selectDynamic("NOTIFICATIONS").toString
      case InitialSetupLoc => state.lang.selectDynamic("INITIAL_SETUP").toString
      case BackupIdentityLoc => state.lang.selectDynamic("BACKUP_IDENTITY").toString
      case BackupAccountLoc => state.lang.selectDynamic("BACKUP_ACCOUNT").toString
      case SetupAccountLoc => state.lang.selectDynamic("SETUP_ACCOUNT").toString
      case TermsOfServiceLoc => state.lang.selectDynamic("TERMS_OF_SERVICE").toString
      case PopulateQRCodeLoc("") => state.lang.selectDynamic("SEND").toString
      case AllAccountsLoc => state.lang.selectDynamic("ALL_ACCOUNTS").toString
      case AddSharedWalletLoc => state.lang.selectDynamic("ADD_SHARED_WALLET").toString
      case _ => "Send"
    }
  }

  final class Backend(t: BackendScope[Props, State]) {
    def getETHNetInfo() = {
      CoreApi.mobileGetETHNetInfo().map(
        netInfo =>
          t.modState(s => s.copy(ethNetInfo = netInfo)).runNow())
    }

    def isSimpleHeader(page: Loc): Boolean = {
      Seq(InitialSetupLoc, BackupIdentityLoc, BackupAccountLoc, SetupAccountLoc, TermsOfServiceLoc)
        .contains(page)
    }

    def changeLang(lang: String): react.Callback = {
      CoreApi.getLang(lang).onComplete {
        case Success(res) => {
          WalletCircuit.dispatch(ChangeLang(JSON.parse(res)))
        }
        case Failure(_) => println(s"failed to load language for ${lang}")
      }
      Callback.empty
    }

    def updateLang(reader: ModelRO[js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def onSideBarMenuClicked(e: ReactEventFromHtml): react.Callback = {
      val cw = e.target.clientWidth
      val w = jQuery("#mySidenav").width()
      if (cw == w) toggleNav()
      react.Callback.empty
    }

    def userProfileImg = {

      val userDetails = WalletCircuit.zoom(_.user.userDetails)

      // todo fix blockies
      val blockies = Blockies.create(js.Dynamic.literal(size = 15, scale = 3, seed = dom.window.localStorage.getItem("pubKey")))
      //Blockies.create(js.Dynamic.literal(size = 15, scale = 3, seed = dom.window.localStorage.getItem("pubKey")))
      jQuery("#userProfileImg").append(blockies)
    }

    def componentDidMount(): Callback = {
      userProfileImg
      //      getNotification
      changeLang(I18N.Lang.en_us)
      WalletCircuit.subscribe(WalletCircuit.zoom(_.i18n.language))(e => updateLang(e))
      getETHNetInfo()
      Callback.empty
    }

    def toggleNav(): Callback = {
      jQuery("#mySidenav").toggleClass("fullWidth")
      jQuery("#closebtnContainer").toggleClass("active")
      jQuery("#bodyWallet").toggleClass("blurBackground")

      Callback.empty
    }
    /*   def getNotification(): Unit = {
      js.timers.setTimeout(10000) {
        CoreApi.getNotification().map { response =>
          Toastr.info(response, Some("New Notification"))
          // getNotification()
        }
      }
    }*/
    def render(props: Props, state: State): VdomElement = {
      val currentPage = props.r.page
      if (isSimpleHeader(currentPage)) {
        <.div()(
          <.div(
            ^.className := "wallet-inner-navigation",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2"),
              <.div(
                ^.className := "col-lg-8 col-md-8 col-sm-8 col-xs-8",
                <.div(
                  ^.className := "wallet-information",
                  <.a(^.href := "javascript:void()", ^.className := "back-to"),
                  <.p(state.ethNetInfo),
                  <.h2(state.lang.selectDynamic("WALLET").toString),
                  <.h3(getHeaderName(props.r.page, state)))),
              <.div(
                ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2"))))
      } else if (currentPage == NotificationLoc || currentPage == AllAccountsLoc || currentPage == AddSharedWalletLoc) {
        <.div()(
          <.div(
            ^.className := "wallet-inner-navigation",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2",
                <.span(^.className := "togglebtn", ^.onClick --> toggleNav, "☰"),
                <.div(^.id := "mySidenav", ^.className := "sidenav", ^.onClick ==> onSideBarMenuClicked,
                  <.div(
                    ^.id := "closebtnContainer",
                    <.span(^.className := "closebtn", ^.onClick --> toggleNav, "×")),
                  <.ul(
                    ^.id := "menu",
                    SidebarMenu(props.c, props.r.page)))),
              <.div(
                ^.className := "col-lg-8 col-md-8 col-sm-8 col-xs-8",
                <.div(
                  ^.className := "wallet-information",
                  <.a(^.href := "javascript:void()", ^.className := "back-to"),
                  <.p(state.ethNetInfo),
                  <.h2(state.lang.selectDynamic("WALLET").toString),
                  <.span(
                    ^.className := "wallet-page",
                    <.h3(getHeaderName(props.r.page, state))))),
              <.div(
                ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2",
                <.div(
                  ^.className := "wallet-user-icon ",
                  /* <.a(
                    <.i(^.className := "fa fa-bell-o", VdomAttr("aria-hidden") := "true")),*/

                  <.div(^.id := "userProfileImg", ^.className := "img-userIcon"))))))
      } else if (currentPage == IdentitiesLoc || currentPage == ManageIdentitiesLoc) {
        var headerName = "Select"
        if (currentPage == ManageIdentitiesLoc) headerName = getHeaderName(props.r.page, state)
        <.div()(
          <.div(
            ^.className := "wallet-inner-navigation",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2",
                <.span(^.className := "togglebtn", ^.onClick --> toggleNav, "☰"),
                <.div(^.id := "mySidenav", ^.className := "sidenav", ^.onClick ==> onSideBarMenuClicked,
                  <.div(
                    ^.id := "closebtnContainer",
                    <.span(^.className := "closebtn", ^.onClick --> toggleNav, "×")),
                  <.ul(
                    ^.id := "menu",
                    SidebarMenu(props.c, props.r.page)))),
              <.div(
                ^.className := "col-lg-8 col-md-8 col-sm-8 col-xs-8",
                <.div(
                  ^.className := "wallet-information",
                  <.a(^.href := "javascript:void()", ^.className := "back-to"),
                  <.p(state.ethNetInfo),
                  <.h2(state.lang.selectDynamic("WALLET").toString),
                  <.span(
                    ^.className := "wallet-page",
                    <.h3(state.lang.selectDynamic(headerName.toUpperCase()).toString), <.i(
                      ^.className := "fa fa-arrow-right",
                      VdomAttr("aria-hidden") := "true"),
                    <.h3(state.lang.selectDynamic("IDENTITIES").toString),
                    <.i(
                      ^.className := "fa fa-arrow-right",
                      VdomAttr("aria-hidden") := "true"),
                    <.h3("username")))),
              <.div(
                ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2",
                <.div(
                  ^.className := "wallet-user-icon ",
                  /* <.a(
                    <.i(^.className := "fa fa-bell-o", VdomAttr("aria-hidden") := "true")),
*/
                  <.div(^.id := "userProfileImg", ^.className := "img-userIcon"))))))
      } else {
        <.div()(
          <.div(
            ^.className := "wallet-inner-navigation",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2",
                <.span(^.className := "togglebtn", ^.onClick --> toggleNav, "☰"),
                <.div(^.id := "mySidenav", ^.className := "sidenav", ^.onClick ==> onSideBarMenuClicked,
                  <.div(
                    ^.id := "closebtnContainer",
                    <.span(^.className := "closebtn", ^.onClick --> toggleNav, "×")),
                  <.ul(
                    ^.id := "menu",
                    SidebarMenu(props.c, props.r.page)))),
              <.div(
                ^.className := "col-lg-8 col-md-8 col-sm-8 col-xs-8",
                <.div(
                  ^.className := "wallet-information",
                  <.a(^.href := "javascript:void()", ^.className := "back-to"),
                  <.p(state.ethNetInfo),
                  <.h2(state.lang.selectDynamic("WALLET").toString),
                  <.span(
                    ^.className := "wallet-page",
                    <.h3(state.lang.selectDynamic("ACCOUNT").toString), <.i(
                      ^.className := "fa fa-arrow-right",
                      VdomAttr("aria-hidden") := "true"),
                    <.h3(userDetails.value.alias), <.i(
                      ^.className := "fa fa-arrow-right",
                      VdomAttr("aria-hidden") := "true"), {
                      <.h3(getHeaderName(props.r.page, state))
                    }))),
              <.div(
                ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2",
                <.div(
                  ^.className := "wallet-user-icon ", /*
                  <.a(
                    ^.href := "#/notification",
                    <.i(^.className := "fa fa-bell-o", VdomAttr("aria-hidden") := "true")),*/
                  <.div(^.id := "userProfileImg", ^.className := "img-userIcon"))))))
      }
    }
  }
  val component = ScalaComponent.builder[Props]("Header")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount())
    .build
}

