//package com.livelygig.product.walletclient.views
//
//import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.html_<^.{<, ^, _}
//
//object TermsOfServiceView {
//
//  case class Props()
//
//  final case class State()
//
//  object State {
//    def init: State =
//      State()
//  }
//
//  final class Backend(t: BackendScope[Props, Unit]) {
//
//    def render(p: Props): VdomElement =
//      <.div(^.id := "bodyWallet")(
//        <.div(
//          ^.className := "termsofservice-main scrollableArea",
//          <.div(
//            ^.className := "termsofservice-section",
//            <.div(
//              ^.className := "row",
//              <.div(
//                ^.className := "col-xs-12",
//                <.h4("View terms of service"),
//                <.p("Lorem ipsum dolor sit amet, quo cu omnium persecuti deterruisset, populo malorum feugait duo eu. Te mel duis congue audiam, quod alia elitr eos cu. Mei at officiis vulputate intellegat, his dicunt albucius quaerendum eu.Lorem ipsum dolor sit amet, quo cu omnium persecuti deterruisset, populo malorum feugait duo eu. Te mel duis congue audiam, quod alia elitr eos cu. Mei at officiis vulputate intellegat, his dicunt albucius quaerendum eu.")
//              )
//            ),
//            <.div(
//              ^.className := "row",
//              <.div(
//                ^.className := "col-xs-12",
//                <.h4("View privacy policy"),
//                <.p("Lorem ipsum dolor sit amet, quo cu omnium persecuti deterruisset, populo malorum feugait duo eu. Te mel duis congue audiam, quod alia elitr eos cu. Mei at officiis vulputate intellegat, his dicunt albucius quaerendum eu.Lorem ipsum dolor sit amet, quo cu omnium persecuti deterruisset, populo malorum feugait duo eu. Te mel duis congue audiam, quod alia elitr eos cu. Mei at officiis vulputate intellegat, his dicunt albucius quaerendum eu.")
//              )
//            ),
//            <.div(
//              ^.className := "btnDefault-container",
//              <.a(^.className := "btn btnDefault decline", ^.href := "/wallet", "decline"),
//              <.a(^.className := "btn btnDefault", ^.href := "newaccount#/setupAccount", VdomAttr(")") := "", "accept")
//            )
//          )
//        )
//      )
//    //ScalaTags goes here
//  }
//
//  val component = ScalaComponent.builder[Props]("TermsOfServiceView")
//    .renderBackend[Backend]
//    // .componentDidMount(scope => scope.backend.componentDidMount())
//    //.configure(Reusability.shouldComponentUpdate)
//    .build
//  def apply(props: Props) = component(props)
//}
