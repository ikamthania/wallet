package com.livelygig.product.walletclient.views

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, ^, _}
import org.querki.jquery.$

object BackupIdentityView {

  case class Props()

  final case class State(isSecure: Boolean = false)

  final class Backend(t: BackendScope[Props, State]) {
    //# todo : Add this to state
    //    var isSecure = false;

    def onChkbClicked() = {
      t.modState(s => s.copy(isSecure = !s.isSecure))
    }

    def onBtnClicked(id: String) = Callback {
      id match {
        case "btnNextStart" => {
          $(s".backupView-start").hide()
          $(s".backupView-generate").show()
          $(s"li:nth-last-child(2)").addClass("passed")
        }
        case "btnNextGenerate" => {
          if (t.state.runNow().isSecure) {
            $(s".backupView-generate").hide()
            $(s".backupView-confirm").show()
            $(s"li:nth-last-child(1)").addClass("passed")
          }
        }
        case "btnNextConfirm" => {

        }
      }
    }

    def render(p: Props): VdomElement =
      <.div(^.id := "bodyWallet")(
        <.div(
          ^.className := "backupIdentity-header",
          <.div(
            ^.className := "col-xs-12",
            <.ul(
              <.li(
                ^.className := "passed",
                <.i(^.className := "fa fa-check", VdomAttr("aria-hidden") := "true"),
                <.p("Start")
              ),
              <.li(
                <.i(^.className := "fa fa-check", VdomAttr("aria-hidden") := "true"),
                <.p("Generate")
              ),
              <.li(
                <.i(^.className := "fa fa-check", VdomAttr("aria-hidden") := "true"),
                <.p("Confirm")
              )
            ),
            <.div(^.className := "line1"),
            <.div(^.className := "line2")
          )
        ),
        <.div(
          ^.className := "backupView-main",
          <.div(
            ^.className := "backupView-start",
            <.p("On the following screen you'll see a set of 12 words that can generate the private keys of your first digital identity on Ubunda as well as accounts it owns. This is your identity’s backup phrase, also known as a mnemonic phrase or master seed phrase, which follows the BIP-44 standard. It can be later entered into any version of Ubunda (and some other applications) in order to restore your digital identity and its owned accounts." +
              "Make sure you are in a private place and not being watched." +
              "Write down the exact Backup Phrase and keep it in a secure location. Do not take a picture or screenshot, since that might be too easily replicated. Do not share the Backup Phrase with anyone else unless you want them to access your credentials and funds. Anyone with access to your Backup Phrase can impersonate you (make claims based on your attributes and credentials) and gain access to your account's funds." +
              "If you fail to maintain a copy of this phrase or lose access to this wallet, you will permanently lose access to all your credentials and funds."),
            <.div(
              ^.className := "btnDefault-container",
              <.button(
                ^.id := "btnNextStart", ^.onClick --> onBtnClicked("btnNextStart"),
                ^.`type` := "button", ^.className := "btn btnDefault setdefault", "Next"
              )
            )
          ),
          <.div(
            ^.className := "backupView-generate",
            <.div(
              ^.className := "generate-section-content",
              <.p("This is your Backup Phrase. Now write it on paper and keep it in a secure place.")
            ),
            <.div(
              ^.className := "generate-section-middle",
              <.h4("your backup phrase"),
              <.div(
                ^.className := "row",
                <.div(
                  ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                  <.p(^.className := "backup-phr-txt", "Lorem Ipsum is simply dummy text of the printing and typesetting industry")
                ),
                <.label(
                  ^.className := "col-lg-3 col-md-4 col-sm-4 col-xs-4",
                  "language"
                ),
                <.select(
                  ^.className := "col-lg-3 col-md-4 col-sm-4 col-xs-4",
                  <.option("English"),
                  <.option("Japanese"),
                  <.option("Korean"),
                  <.option("Spanish"),
                  <.option("Chinese (Simplified)"),
                  <.option("Chinese (Traditional)"),
                  <.option("French"),
                  <.option("Italian")
                )
              )
            ),
            <.div(
              ^.className := "generate-section-bottom",
              <.div(
                ^.className := "btnDefault-container",
                <.form(
                  <.div(
                    ^.className := "checkbox",
                    <.label(
                      <.input(
                        ^.id := "chkbSecure", ^.`type` := "checkbox",
                        ^.onClick --> onChkbClicked()
                      ),
                      "I have copied and stored my backup phrase in a secure place."
                    )
                  )
                ),
                <.button(
                  ^.id := "btnNextGenerate", ^.onClick --> onBtnClicked("btnNextGenerate"),
                  ^.`type` := "button", ^.className := "btn btnDefault setdefault", "Next"
                )
              )
            )
          ),
          <.div(
            ^.className := "backupView-confirm",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-xs-12",
                <.div(
                  ^.className := "confirm-square",
                  <.i(^.className := "fa fa-check-circle-o", VdomAttr("aria-hidden") := "true")
                )
              )
            ),
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-xs-12",
                <.div(
                  ^.className := "confirm-message",
                  <.h3("Your identity is backed up."),
                  <.p("If this app is deleted or you lose access to your account, your identity and associated accounts can be recovered using the Backup Mnemonic Phrase you have just secured.")
                )
              )
            ),
            <.div(
              ^.className := "btnDefault-container",
              <.button(
                ^.id := "btnNextConfirm", ^.onClick --> onBtnClicked("btnNextConfirm"),
                ^.`type` := "button", ^.className := "btn btnDefault setdefault", "Finish"
              )
            )
          )
        )
      )
  }

  val component = ScalaComponent.builder[Props]("BackupIdentityView")
    .initialState(State())
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}
