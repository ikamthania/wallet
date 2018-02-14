package com.livelygig.product.walletclient.views

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, ^, _}

object ManageIdentitiesView {

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
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"
                        )
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.i(^.className := "fa fa-user", VdomAttr("aria-hidden") := "true")
                      )
                    )
                  )
                ),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p(
                          ^.className := "ellipseText",
                          "Ron's produce"
                        )
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.i(^.className := "fa fa-user", VdomAttr("aria-hidden") := "true")
                      )
                    )
                  )
                ),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p(
                          ^.className := "ellipseText",
                          "patrick yash csintalan"
                        )
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.img(^.src := "/assets/images/settings-symbol.png")
                      )
                    )
                  )
                ),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p(
                          ^.className := "ellipseText",
                          "jenny csintalan"
                        )
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.img(^.src := "/assets/images/org.png")
                      )
                    )
                  )
                ),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p("rosette rho")
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.img(^.src := "/assets/images/settings-symbol.png")
                      )
                    )
                  )
                ),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"
                        )
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.img(^.src := "/assets/images/settings-symbol.png")
                      )
                    )
                  )
                ),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993dfb93a"
                        )
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.img(^.src := "/assets/images/settings-symbol.png")
                      )
                    )
                  )
                ),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993dfb93a"
                        )
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.img(^.src := "/assets/images/settings-symbol.png")
                      )
                    )
                  )
                ),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"
                        )
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.img(^.src := "/assets/images/settings-symbol.png")
                      )
                    )
                  )
                ),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993dfb93a"
                        )
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.img(^.src := "/assets/images/org.png")
                      )
                    )
                  )
                ),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p(
                          ^.className := "ellipseText",
                          "0xad7fd6993d35264356425645u346253645ufb93a"
                        )
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.img(^.src := "/assets/images/settings-symbol.png")
                      )
                    )
                  )
                ),
                <.li(
                  <.a(
                    ^.href := "javascript:void(0)",
                    <.div(
                      ^.className := "row",
                      <.div(
                        ^.className := "col-lg-3 col-md-3 co-sm-3 col-xs-3",
                        <.img(^.src := "../assets/images/boy.png")
                      ),
                      <.div(
                        ^.className := "col-lg-7 col-md-7 co-sm-7 col-xs-7",
                        <.p("0xad7fd6993dfb93a")
                      ),
                      <.div(
                        ^.className := "col-lg-2 col-md-2 co-sm-2 col-xs-2 profileslist-settings",
                        <.img(^.src := "/assets/images/org.png")
                      )
                    )
                  )
                )
              )
            )
          )
        ),
        <.div(
          ^.className := "container btnDefault-container",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.button(^.`type` := "button", ^.className := "btn btnDefault btn-identity", "New"),
              <.button(^.`type` := "button", ^.className := "btn btnDefault btn-identity", "Update"),
              <.button(^.`type` := "button", ^.className := "btn btnDefault btn-identity", "Delete"),
              <.button(^.`type` := "button", ^.className := "btn btnDefault btn-identity", "View")
            )
          )
        )
      )
  }

  val component = ScalaComponent.builder[Props]("ManageIdentitiesView")
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}
