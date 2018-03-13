package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.jQuery
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

object StaticLandingView {
  def onGetStartedClick() = {
    Callback {
      jQuery("#main-splash").addClass("hidden")
      jQuery("#welcome-splash").removeClass("hidden")
      jQuery("#account-management-item").removeClass("active")
      jQuery("#language-settings-item").addClass("active")
    }
  }

  def previousBtnClick() = {
    Callback {
      if (jQuery("#language-settings-item").hasClass("active")) {
        jQuery("#main-splash").removeClass("hidden")
        jQuery("#welcome-splash").addClass("hidden")
        jQuery("#btnPrevious").click()
      }
    }
  }

  def nextBtnClick() = {
    Callback {
      if (jQuery("#account-management-item").hasClass("active")) {
        dom.window.location.href = "#/terms"
      }
    }
  }

  def component = ScalaComponent.static("TermsOfService") {
    <.main(
      ^.className := "wallet-main container",
      <.div(
        ^.className := "wallet-inner container-fluid",
        <.div(
          <.div(^.id := "main-splash", ^.className := "container",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-xs-12",
                <.div(
                  ^.className := "main",
                  <.h3(^.id := "confidential", "LivelyGig confidential"),
                  <.img(^.src := "./assets/images/LivelyGigIcon.png", ^.title := "LivelyGigIcon"),
                  <.h3("Ubunda Suite"),
                  <.div(
                    ^.className := "networkOption",
                    <.p("Network:"),
                    <.select(
                      ^.className := "form-control",
                      <.option("Ropsten"))))),
              <.div(
                ^.className := "container btnDefault-container container-NoBorder",
                <.div(
                  ^.className := "row",
                  <.div(
                    ^.className := "col-xs-12",
                    <.button(^.id := "btnGetStarted", ^.onClick --> onGetStartedClick(), VdomAttr("data-slide") := "next", ^.className := "btn btnDefault goupButton", "Get started")))))),
          <.div(
            ^.className := "container",
            <.div(
              ^.className := "row",
              <.div(^.id := "welcome-splash", ^.className := "welcome-wallet hidden",
                <.div(^.id := "myCarousel", VdomAttr("data-ride") := "carousel", ^.className := "carousel slide", VdomAttr("data-interval") := "false",
                  <.ol(
                    ^.className := "carousel-indicators",
                    <.li(VdomAttr("data-target") := "#myCarousel", VdomAttr("data-slide-to") := "0", ^.className := "active"),
                    <.li(VdomAttr("data-target") := "#myCarousel", VdomAttr("data-slide-to") := "1", ^.className := "")),
                  <.div(
                    ^.className := "carousel-inner scrollableArea",
                    <.div(
                      ^.id := "language-settings-item",
                      ^.className := "item",
                      <.img(^.src := "./assets/images/languageIcon.png"),
                      <.h4("Language"),
                      <.select(
                        ^.className := "form-control",
                        <.option("English"),
                        <.option("Pseudolocalized")),
                      <.img(^.src := "./assets/images/regionIcon.png"),
                      <.h4("Region"),
                      <.select(
                        ^.className := "form-control",
                        <.option("USA"),
                        <.option("Germany"))),
                    <.div(
                      ^.id := "account-management-item",
                      ^.className := "item",
                      <.div(
                        ^.className := "row itemSetup",
                        <.img(^.className := "col-xs-4", ^.src := "./assets/images/dim.PNG"),
                        <.h4("Digital Identity Management"),
                        <.p(
                          """Use Ubunda to create and manage your independent digital identities, to control and share
                                    your credentials, and to issue and verify claims.""")),
                      <.div(
                        ^.className := "row itemSetup dg-wallet",
                        <.img(^.id := "imgRight", ^.className := "col-md-push-10 col-sm-push-9 col-xs-4 col-xs-push-8", ^.src := "./assets/images/dw.PNG"),
                        <.h4(^.className := "col-md-pull-2 col-xs-8 col-xs-pull-4", "Digital Wallet"),
                        <.p(
                          ^.className := "col-md-pull-2 col-sm-pull-2 col-xs-8 col-xs-pull-4",
                          """Create and manage personal and shared accounts to transact on the Ethereum network. Send
                                    and receive payments. View and annotate transactions.""")),
                      <.div(
                        ^.className := "row itemSetup",
                        <.img(^.className := "col-xs-4", ^.src := "./assets/images/kmr.PNG"),
                        <.h4("Key Management and Recovery"),
                        <.p(
                          """Manage and protect your digital identity and accounts using an encrypted key backup and recovery.
                                    Use your keys to digitally sign messages on the network."""))))),
                <.div(
                  ^.className := "container btnDefault-container container-NoBorder",
                  <.div(
                    ^.className := "row",
                    <.div(
                      ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                      <.a(^.id := "btnNext", ^.href := "#myCarousel", ^.onClick --> nextBtnClick(), VdomAttr("data-slide") := "next", ^.className := "btn btnDefault goupButton", "Next"),
                      <.a(^.id := "btnPrevious", ^.onClick --> previousBtnClick(), ^.href := "#myCarousel", VdomAttr("data-slide") := "prev", ^.className := "btn btnDefault goupButton btnPrevious", "Previous"),
                      <.br,
                      <.div(
                        ^.className := "download-apk",
                        <.div(^.className := "app-download", "Ubunda for mobile"),
                        <.a(^.href := "/wallet/app/%20android%20", ^.className := "app-link pull-right", "Android")))))))))))
  }

}
