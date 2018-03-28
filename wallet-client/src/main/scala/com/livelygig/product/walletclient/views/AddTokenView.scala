
package com.livelygig.product.walletclient.views
// import com.livelygig.product.walletclient.facades.Bootstrap._
import com.livelygig.product.walletclient.modals.AddTokenModal
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }
import japgolly.scalajs.react.{ Callback, _ }

object AddTokenView {

  final case class Props() {
    @inline def render: VdomElement = component(this)
  }

  final case class State(tokenList: List[Token])

  final case class Token(
    index: Int,
    name: String,
    address: String,
    decimal: Int,
    isVisible: Boolean)

  object State {
    val tokenList = List[Token](
      Token(1, "RHOC", "0x8409218421sdgs213tg", 8, false),
      Token(2, "RHOC", "0x8409218421sdgs213tg", 8, true),
      Token(3, "RHOC", "0x8409218421sdgs213tg", 8, true),
      Token(4, "RHOC", "0x8409218421sdgs213tg", 8, false))
    def init: State =
      State(tokenList)
  }

  final class Backend(t: BackendScope[Props, State]) {

    def onTokenChecked(index: Int)(e: ReactEventFromInput) = {
      val newValue = e.target.checked

      val tknLst = t.state.runNow().tokenList
      val resultList = tknLst.map(tkn => {
        if (tkn.index == index) {
          tkn.copy(isVisible = newValue)
        } else {
          tkn
        }
      })

      t.modState(_.copy(tokenList = resultList)).runNow()

      Callback.empty
    }

    def renderTokenList(): VdomElement = {
      def createTokenList(tkn: Token) =
        <.div(
          ^.className := "item",
          <.div(
            ^.className := "col-lg-3 col-md-3 col-sm-3 col-xs-3",
            <.p(
              ^.className := "ellipseText",
              tkn.name)),
          <.div(
            ^.className := "col-lg-4 col-md-4 col-sm-4 col-xs-4",
            <.p(
              ^.className := "ellipseText",
              tkn.address)),
          <.div(
            ^.className := "col-lg-3 col-md-3 col-sm-3 col-xs-3",
            <.p(
              ^.className := "ellipseText",
              tkn.decimal)),
          <.div(
            ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2",
            <.input(^.`type` := "checkbox", ^.checked := tkn.isVisible, ^.onChange ==> onTokenChecked(tkn.index))),
          <.div(^.className := "clearfix"))
      <.div(
        ^.className := "row tokenListItems",
        <.div(
          t.state.runNow().tokenList map createTokenList: _*))
    }

    // def addToken(e: ReactEventFromInput)(token: Token) = {
    //   t.modState(
    //     s => State(
    //       s.tokenList :+ Token(token.name, token.address),
    //     )
    //   )
    // }

    def showAddTokenModal(): Callback = {
      //      jQuery("#addTokenModal").modal(js.Dynamic.literal("backdrop" -> "static", "keyboard" -> true, "show" -> true))

      Callback.empty
    }

    def render(p: Props, state: State): VdomElement =
      <.div(^.id := "bodyWallet")(
        AddTokenModal.component(AddTokenModal.Props()),
        <.div(
          ^.className := "row main-token-view scrollableArea",
          <.div(
            ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12 tokenHeaderContainer",
            <.h4("Show and hide tokens")),
          <.div(
            ^.className := "tokenHeaders",
            <.div(
              ^.className := "col-lg-3 col-md-3 col-sm-3 col-xs-3",
              <.p("Name")),
            <.div(
              ^.className := "col-lg-4 col-md-4 col-sm-4 col-xs-4",
              <.p("Address")),
            <.div(
              ^.className := "col-lg-3 col-md-3 col-sm-3 col-xs-3",
              <.p("Decimals")),
            <.div(
              ^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2",
              <.p("Visible")),
            <.div(^.className := "clearfix")),
          renderTokenList()),
        <.div(
          ^.className := "container btnDefault-container buttonContainerLeftAlign",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.button(^.`type` := "button", ^.className := "btn btnDefault goupButton ", "Add", ^.onClick --> showAddTokenModal()),
              <.button(^.`type` := "button", ^.className := "btn btnDefault goupButton ", "Done", ^.onClick --> showAddTokenModal())))))
  }

  val component = ScalaComponent.builder[Props]("AddTokenView")
    .initialState(State.init)
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}
