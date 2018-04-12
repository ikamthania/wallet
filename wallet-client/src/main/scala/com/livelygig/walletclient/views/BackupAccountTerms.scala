package com.livelygig.walletclient.views

import com.livelygig.walletclient.router.ApplicationRouter.{ Loc, ViewBackupPhraseLoc }
import japgolly.scalajs.react
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{ <, VdomAttr, VdomElement, ^, _ }
import japgolly.scalajs.react.{ BackendScope, ScalaComponent }

object BackupAccountTerms {

  case class Props(router: RouterCtl[Loc])

  final case class State()

  final class Backend(t: BackendScope[Props, State]) {

    def onBtnClicked(): react.Callback = {
      t.props.flatMap(_.router.set(ViewBackupPhraseLoc))
    }

    def render(p: Props, s: State): VdomElement = {
      <.div(
        <.div(
          ^.className := "wallet-inner-navigation",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
              <.div(
                ^.className := "wallet-information",
                <.h2("Wallet"),
                <.h3("Back up account"))))),
        <.div(
          ^.className := "backupView-main scrollableArea",
          <.div(
            ^.className := "backupView-start",
            <.ul(
              <.li("On the next screen you'll see a 12 word mnemonic phrase that corresponds to your new account."),
              <.li("This is your account’s Backup Phrase."),
              <.li("Later, you can enter this phrase into Ubunda (or other wallets) to re-gain control over your account."),
              <.li("Make sure you are in a private place and not being watched."),
              <.li("Write down the exact Backup Phrase and keep it in a secure location."),
              <.li("Do not take a picture or screenshot, since that might be easily copied."),
              <.li("Anyone with access to your Backup Phrase can gain access to your account's funds."),
              <.li("If you fail to maintain a copy of this phrase or lose access to this wallet, you will permanently lose access to the funds in this account."))),

          <.div(
            ^.className := "backupView-generate",
            <.div(
              ^.className := "container btnDefault-container",
              <.div(
                ^.className := "row",
                <.div(
                  ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                  <.button(
                    ^.id := "btnNextStart",
                    ^.name := "btnNxt",
                    ^.`type` := "button",
                    VdomAttr("data-toggle") := "modal",
                    ^.className := "btn btnDefault goupButton setdefault",
                    ^.onClick --> onBtnClicked(),
                    "Next")))))))
    }
  }

  val component = ScalaComponent.builder[Props]("BackupAccount")
    .initialState(State())
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}