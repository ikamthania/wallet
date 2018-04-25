package com.livelygig.walletclient.views

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }

object IdentitiesView {

  case class Props()

  //case class State()

  final class Backend(t: BackendScope[Props, Unit]) {

    def render(p: Props): VdomElement =
      <.div(^.id := "bodyWallet")(
        <.div(
          ^.className := "identify-screen-main",
          <.div(
            ^.className := "identify-screen-inner",
            <.div(
              ^.className := "identify-screen-profileslist",
              <.ul(
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"))))),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "Ron's produce"))))),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "patrick yash csintalan"))))),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "jenny csintalan"))))),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "rosette rho"))))),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"))))),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"))))),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"))))),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"))))),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"))))),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"))))),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "/wallet/assets/images/boy.png")),
                      <.div(
                        ^.className := "col-lg-9 col-md-9 co-sm-9 col-xs-9",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"))))))))),
        <.div(
          ^.className := "container btnDefault-container",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.button(^.`type` := "button", ^.className := "btn btnDefault", "Set as default")))))
  }

  val component = ScalaComponent.builder[Props]("IdentitiesView")
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}
