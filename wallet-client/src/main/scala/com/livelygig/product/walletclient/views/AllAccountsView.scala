package com.livelygig.product.walletclient.views
import com.livelygig.product.shared.models.wallet.{ Account, AccountInfo }
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.product.walletclient.router.ApplicationRouter.{ AddNewAccountLoc, LandingLoc, Loc }
import diode.data.Pot
import diode.react.ModelProxy
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }
import japgolly.scalajs.react.{ BackendScope, Callback, ScalaComponent, _ }
import org.scalajs.dom
import play.api.libs.json.{ JsValue, Json }

object AllAccountsView {

  case class Props(router: RouterCtl[Loc], proxy: ModelProxy[AccountInfo])

  case class State()

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
      val baseUrl = dom.window.location.href
      val updatedUrl = baseUrl.split("/walletmain.html").head
      dom.window.location.href = s"${updatedUrl}/setup.html?q=addAccount"

      Callback.empty
    }

    def onSelectAccountClicked(): Callback = {
      jQuery(".selected").removeClass("selected")

      jQuery(".active").addClass("selected")
      jQuery(".active").removeClass("active")

      updateURL("AccountLoc")

      Callback.empty
    }

    def onItemClicked(getpriKey: String, getpubKey: String)(e: ReactEventFromHtml): react.Callback = {
      e.preventDefault()
      if (getpubKey.contains("0x")) {
        dom.window.localStorage.setItem("pubKey", getpubKey)
        dom.window.localStorage.setItem("priKey", getpriKey)
      } else {
        dom.window.localStorage.setItem("pubKey", s"0x$getpubKey")
        dom.window.localStorage.setItem("priKey", getpriKey)
      }
      jQuery("li").removeClass("active")
      if (!jQuery(e.target).is("li")) {
        jQuery(e.target).parents("li").addClass("active")
      } else jQuery(e.target).addClass("active")
      Callback.empty
    }

    def componentDidMount(): Callback = {
      /*jQuery("#accountList li").first().addClass("selected")

      val keystoreContent = dom.window.localStorage.getItem("keystoreData")
      //      val keystoreContent = "[{\"keystorePvtKey\":\"john\",\"keystorePubKey\":\"0x13427c61c0b4391a54b6405cc3bb014d38887f4a\" , \"timestamp\":\"Fri, 02 Feb 2018 06:07:37 GMT\"},{\"keystorePvtKey\":\"john\",\"keystorePubKey\":\"0xec71c074ea5573ddf1a7767773bbd324cfa971d2\", \"timestamp\":\"Fri, 02 Feb 2018 06:07:37 GMT\"}]"
      //      val data = keystoreContent.toArray

      val json: JsValue = Json.parse(keystoreContent)
      //      Toastr.info(json.toString())
      //      val pubKey = (json \ "keystorePubKey").as[String]
      val pubKey = (json \\ "keystorePubKey").map(_.as[String])

      val priKey = (json \\ "keystorePvtKey").map(_.as[String])

      val timestamp = (json \\ "timestamp").map(_.as[String])

      //      pubKey.map(e => Toastr.info(e))

      val keystoredata = priKey zip pubKey zip timestamp*/

      //      t.modState(s => s.copy(data = keystoredata))

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
                    (^.className := "selected").when(account.address == p.proxy.value.selectedAddress),
                    ^.key := account.address,
                    ^.onClick ==> onItemClicked("keystoreKey._1._1", "keystoreKey._1._2"),
                    <.a(
                      ^.href := "javascript:void(0)",
                      <.div(
                        ^.className := "row",
                        <.div(^.className := "col-lg-4 col-md-4 col-sm-4 col-xs-3")(
                          <.p(
                            ^.id := "keystoreKey._1._2")(
                              account.accountName)),
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
              <.button(^.`type` := "button", ^.className := "btn btnDefault accountBtn goupButton notAlphaV", "Delete", ^.onClick --> updateURL("")),
              <.button(^.`type` := "button", ^.className := "btn btnDefault accountBtn goupButton notAlphaV", "Configure", ^.onClick --> updateURL("")),
              <.button(^.`type` := "button", ^.className := "btn btnDefault accountBtn goupButton", "Add", ^.onClick --> setSetupScreenView()),
              <.button(^.`type` := "button", ^.className := "btn btnDefault accountBtn goupButton", "Select", ^.onClick --> onSelectAccountClicked())))))
  }

  val component = ScalaComponent.builder[Props]("AllAccountsView")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount())
    .build
  def apply(props: Props) = component(props)
}
