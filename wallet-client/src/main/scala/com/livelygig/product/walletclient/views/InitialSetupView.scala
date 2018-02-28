package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.modals.getInfoModal
import com.livelygig.product.walletclient.facades.Bootstrap._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }
import org.querki.jquery.$
import org.scalajs.dom

import scala.scalajs.js

object InitialSetupView {

  case class Props()

  class Backend(t: BackendScope[Props, Unit]) {
    //todo : Move this to state
    var rdbSelected = "newId"
    var rdbAdvOption = ""

    def componentDidMount() = Callback {
      $(s"#newId").attr("checked", true)
    }

    def onNextClicked() = Callback {

      rdbSelected match {
        case "newId" => dom.window.location.href = "#/backupIdentity"
        case "existingId" => {
          rdbAdvOption match {
            case "importJson" => {
              $("#getInfoModal").modal(js.Dynamic.literal("backdrop" -> "static", "keyboard" -> true, "show" -> true))
              //todo: get file
            }
            case "pasteJson" => {
              println("pasteJson")
            }
            case "passphrase" => {
              println("passphrase")

            }
            case "privateKey" => {
              println("passphrase")

            }
            //case "registerProvider" => // todo
            //case "registerHardware" => // todo
          }
        }
        //case "restoreApp" => //todo
      }
    }

    def onrdbChecked(id: String) = Callback {

      if (id == "existingId") $(s"input[name='initialIdentifierExisting']").attr("disabled", false)
      else {
        if ($(s"#jsonText").hasClass("in")) $(s"#jsonText").removeClass("in")

        if ($(s"#passphraseText").hasClass("in")) $(s"#passphraseText").removeClass("in")

        if ($(s"#privateKeyText").hasClass("in")) $(s"#privateKeyText").removeClass("in")

        $(s"input[name='initialIdentifierExisting']").attr("disabled", true)
      }
      rdbSelected = id
      $(s"#btnNext").attr("data-target", "")
    }

    def collpaseTextArea(id: String) = Callback {
      //      println(id)

      if ($(s"#jsonText").hasClass("in")) $(s"#jsonText").removeClass("in")

      if ($(s"#passphraseText").hasClass("in")) $(s"#passphraseText").removeClass("in")

      if ($(s"#privateKeyText").hasClass("in")) $(s"#privateKeyText").removeClass("in")

      rdbAdvOption = id

      if (id == "pasteJson" || id == "passphrase") $(s"#btnNext").attr("data-target", "#confirmModal")
      else $(s"#btnNext").attr("data-target", "")
    }

    def onbtnShowAdvancedClicked(id: String) = Callback {
      $(s"#$id").toggleClass("active")
    }

    def render(p: Props): VdomElement =
      <.div(^.id := "bodyWallet")(
        getInfoModal.component(getInfoModal.Props()),
        // ConfirmModal.component(ConfirmModal.Props()),
        <.div(
          ^.className := "initialSetup-main",
          <.form(
            <.div(
              ^.className := "radio",
              <.label(
                <.input(
                  ^.id := "newId",
                  ^.`type` := "radio", ^.name := "initialIdentifier", ^.onClick --> onrdbChecked("newId")),
                "Create a new digital identity"),
              <.p("Identity address has been created for you:"),
              <.p(
                ^.id := "id",
                ^.className := "ellipseText",
                "0x12345678901234567890123456789012345678901234567890")),
            <.button(
              ^.id := "btnAdvOpt", ^.`type` := "button", ^.className := "btn btnAdvOpt",
              VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#advOpt", "Advanced ",
              ^.onClick --> onbtnShowAdvancedClicked("btnAdvOpt"),
              <.i(^.className := "fa fa-chevron-down", VdomAttr("aria-hidden") := "true")),
            <.div(^.id := "advOpt", ^.className := "collapse",
              <.div(
                ^.className := "radio",
                <.label(
                  ^.onClick --> onrdbChecked("existingId"),
                  <.input(^.id := "existingId", ^.`type` := "radio", ^.name := "initialIdentifier"),
                  "Use existing digital identity:")),
              <.div(
                ^.className := "existingIdOptions",
                <.div(
                  ^.className := "radio",
                  <.label(
                    ^.onClick --> collpaseTextArea("importJson"),
                    <.input(^.`type` := "radio", ^.name := "initialIdentifierExisting", ^.disabled := true),
                    "Keystore file (UTC / JSON)")),
                <.div(
                  ^.className := "radio",
                  <.label(
                    ^.onClick --> collpaseTextArea("pasteJson"),
                    <.input(^.`type` := "radio", ^.name := "initialIdentifierExisting", ^.disabled := true,
                      VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#jsonText"),
                    "Keystore text (UTC / JSON)"),
                  <.div(
                    ^.id := "jsonText",
                    ^.className := "collapse",
                    <.textarea(
                      ^.id := "jsonTxt",
                      ^.className := "form-control", ^.rows := 3))),
                <.div(
                  ^.className := "radio",
                  <.label(
                    ^.onClick --> collpaseTextArea("passphrase"),
                    <.input(^.`type` := "radio", ^.name := "initialIdentifierExisting", ^.disabled := true,
                      VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#passphraseText"),
                    "Mnemonic phrase"),
                  <.div(
                    ^.id := "passphraseText",
                    ^.className := "collapse",
                    <.textarea(
                      ^.id := "passphraseTxt",
                      ^.className := "form-control", ^.rows := 3))),
                <.div(
                  ^.className := "radio",
                  <.label(
                    ^.onClick --> collpaseTextArea("privateKey"),
                    <.input(^.`type` := "radio", ^.name := "initialIdentifierExisting", ^.disabled := true,
                      VdomAttr("data-toggle") := "collapse", VdomAttr("data-target") := "#privateKeyText"),
                    "Private key"),
                  <.div(
                    ^.id := "privateKeyText",
                    ^.className := "collapse",
                    <.textarea(
                      ^.id := "privateKey",
                      ^.className := "form-control", ^.rows := 1))),
                <.div(
                  ^.className := "radio",
                  <.label(
                    ^.onClick --> collpaseTextArea("registerProvider"),
                    <.input(^.`type` := "radio", ^.name := "initialIdentifierExisting", ^.disabled := true),
                    "Web3 provider (Mist, Metamask, Parity, etc.)")),
                <.div(
                  ^.className := "radio",
                  <.label(
                    ^.onClick --> collpaseTextArea("registerHardware"),
                    <.input(^.`type` := "radio", ^.name := "initialIdentifierExisting", ^.disabled := true),
                    "Ledger hardware wallet"))),
              <.div(
                ^.className := "radio",
                <.label(
                  ^.onClick --> onrdbChecked("restoreApp"),
                  <.input(
                    ^.id := "restoreApp",
                    ^.`type` := "radio", ^.name := "initialIdentifier"),
                  "Restore entire application from backup")))),
          <.div(
            ^.className := "btnDefault-container",
            <.button(^.id := "btnNext", ^.`type` := "button", ^.className := "btn btnDefault setdefault",
              VdomAttr("data-toggle") := "modal", ^.onClick --> onNextClicked(), "Next"))))
  }

  val component = ScalaComponent.builder[Props]("InitialSetupView")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount())
    .build
  def apply(props: Props) = component(props)
}
