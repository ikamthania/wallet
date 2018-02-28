package com.livelygig.product.walletclient.views

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{ <, ^, _ }
import org.querki.jquery.$
import org.scalajs.dom

object SetUpAccountView {

  case class Props()

  //implicit val reusabilityProps: Reusability[Props] =
  //  Reusability.caseClass

  final case class State()

  object State {
    def init: State =
      State()

  }

  final class Backend(t: BackendScope[Props, Unit]) {
    var rdbSelected = "newId";
    var rdbAdvOption = "";

    def componentDidMount() = Callback {
      $(s"#newId").attr("checked", true)
    }

    def onNextClicked() = Callback {
      rdbSelected match {
        case "newId" => dom.window.location.href = "#/backupAccount"
        case "existingId" => {
          rdbAdvOption match {
            case "importJson" => {
              $(s"#iptJsonFile").click()
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
      if (id == "newId" || id == "restoreApp") {
        $(s"#btnNext").text("Next")
        $(s"input[name='initialAccountExisting']").attr("disabled", true)
      } else if (id == "existingId") $(s"input[name='initialAccountExisting']").attr("disabled", false)
      else {
        $(s"#btnNext").text("Done")

        if ($(s"#jsonText").hasClass("in")) $(s"#jsonText").removeClass("in")

        if ($(s"#passphraseText").hasClass("in")) $(s"#passphraseText").removeClass("in")

        if ($(s"#privateKeyText").hasClass("in")) $(s"#privateKeyText").removeClass("in")
      }
      this.rdbSelected = id;
      $(s"#btnNext").attr("data-target", "")
    }

    def collpaseTextArea(id: String) = Callback {
      println(id)

      if ($(s"#jsonText").hasClass("in")) $(s"#jsonText").removeClass("in")

      if ($(s"#passphraseText").hasClass("in")) $(s"#passphraseText").removeClass("in")

      if ($(s"#privateKeyText").hasClass("in")) $(s"#privateKeyText").removeClass("in")

      rdbAdvOption = id;

      if (id == "pasteJson" || id == "passphrase") $(s"#btnNext").attr("data-target", "#confirmModal")
      else $(s"#btnNext").attr("data-target", "")
    }

    def onbtnShowAdvancedClicked(id: String) = Callback {
      $(s"#$id").toggleClass("active")

      if ($(s"#$id").hasClass("active")) {
        $(s"#btnNext").text("Done")
        $(s"input[name='initialAccount']").removeAttr("checked")
        $(s"#existingId").attr("checked", true)
        $(s"input[name='initialAccountExisting']").attr("disabled", false)
      } else {
        // $(s"#existingId").attr("checked", false)
        $(s"#existingId").removeAttr("checked")
        $(s"input[name='initialAccountExisting']").attr("disabled", true)
      }
    }

    def render(p: Props): VdomElement =
      <.div(^.id := "bodyWallet")(
        //  ConfirmModal.component(ConfirmModal.Props()),
        <.div(
          ^.className := "initialSetup-main",
          <.form(
            <.div(
              ^.className := "radio",
              <.label(
                <.input(
                  ^.id := "newId",
                  ^.`type` := "radio", ^.name := "initialAccount", ^.onClick --> onrdbChecked("newId")),
                "New account")),
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
                  <.input(^.id := "existingId", ^.`type` := "radio", ^.name := "initialAccount"),
                  "Use existing account:")),
              <.div(
                ^.className := "existingIdOptions",
                <.div(
                  ^.className := "radio",
                  <.input(^.id := "iptJsonFile", ^.name := "jsonFile", ^.`type` := "file"),
                  <.label(
                    ^.onClick --> collpaseTextArea("importJson"),
                    <.input(^.`type` := "radio", ^.name := "initialAccountExisting", ^.disabled := true),
                    "Keystore file (UTC / JSON)")),
                <.div(
                  ^.className := "radio",
                  <.label(
                    ^.onClick --> collpaseTextArea("pasteJson"),
                    <.input(^.`type` := "radio", ^.name := "initialAccountExisting", ^.disabled := true,
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
                    <.input(^.`type` := "radio", ^.name := "initialAccountExisting", ^.disabled := true,
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
                    <.input(^.`type` := "radio", ^.name := "initialAccountExisting", ^.disabled := true,
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
                    <.input(^.`type` := "radio", ^.name := "initialAccountExisting", ^.disabled := true),
                    "Web3 provider (Mist, Metamask, Parity, etc.)")),
                <.div(
                  ^.className := "radio",
                  <.label(
                    ^.onClick --> collpaseTextArea("registerHardware"),
                    <.input(^.`type` := "radio", ^.name := "initialAccountExisting", ^.disabled := true),
                    "Ledger hardware wallet"))),
              <.div(
                ^.className := "radio",
                <.label(
                  ^.onClick --> onrdbChecked("restoreApp"),
                  <.input(
                    ^.id := "restoreApp",
                    ^.`type` := "radio", ^.name := "initialAccount"),
                  "Restore entire application from backup")))),
          <.div(
            ^.className := "btnDefault-container",
            <.button(^.id := "btnNext", ^.`type` := "button", ^.className := "btn btnDefault setdefault",
              VdomAttr("data-toggle") := "modal", ^.onClick --> onNextClicked(), "Next"))))
    //ScalaTags goes here
  }

  val component = ScalaComponent.builder[Props]("SetUpAccountView")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount())
    //.configure(Reusability.shouldComponentUpdate)
    .build
  def apply(props: Props) = component(props)
}
