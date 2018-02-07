package com.livelygig.product.walletclient.views

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, ^, _}
import org.scalajs.dom
import org.querki.jquery.$

object BackupAccountView {

  case class Props()

  final case class State()

  final class Backend(t: BackendScope[Props, Unit]) {
    //#todo : Move this to state
    var isSecure = false;
    var phrase = ""
    var wordsSelected = 0

    def onChkbClicked() = Callback {
      isSecure = !isSecure;
    }

    def onBtnClicked(id: String) = Callback {
      id match {
        case "btnNextStart" => {
          $(s".backupView-start").hide()
          $(s".backupView-generate").show()
        }
        case "btnNextGenerate" => {
          if (isSecure) {
            $(s".backupView-generate").hide()
            $(s".backupView-phrase").show()
          }
        }
        case "btnNextConfirmPhrase" => {
          val confirmPhrase = $(s".backupPhrase-container").children().text()

          if ($.trim(confirmPhrase) == $.trim(phrase.trim())) {
            $(s"#errorMessage").css("visibility", "hidden")
            $(s".backupView-phrase").hide()
            $(s".backupView-confirm").show()
          } else {
            $(s"#errorMessage").css("visibility", "visible")
          }
        }
        case "btnResetPhrase" => {
          $(s".backupView-phrase").hide()
          $(s".backupView-generate").show()
        }
        case "btnNextConfirm" => dom.window.location.href = "#/account"
      }
    }

    def onItemClicked(id: String) = Callback {
      if ($("#" + id).hasClass("disabled")) {
        $("#" + id).removeClass("disabled")
        $(".backupPhrase-container p").remove(":contains(" + id + " )")
        wordsSelected -= 1
      } else if (wordsSelected < 12) {
        val itemText = $("#" + id).text()
        $("#" + id).addClass("disabled")
        $(".backupPhrase-container").append("<p>" + itemText + "</p>")
        wordsSelected += 1
      }
    }

    def render(p: Props): VdomElement = {
      val words = Array(
        "abandon",
        "afford",
        "afraid",
        "again",
        "age",
        "agent",
        "agree",
        "ahead",
        "aim",
        "air",
        "airport",
        "aisle",
        "alarm",
        "album",
        "alcohol",
        "alert",
        "alien",
        "all",
        "alley",
        "allow",
        "almost",
        "alone",
        "alpha",
        "already",
        "also",
        "alter",
        "always",
        "amateur",
        "amazing",
        "among",
        "amount",
        "amused",
        "analyst",
        "anchor",
        "ancient",
        "anger",
        "angle",
        "arrow",
        "art",
        "artefact",
        "artist",
        "artwork",
        "ask",
        "aspect",
        "balcony",
        "ball",
        "bamboo",
        "banana",
        "banner",
        "bar",
        "barely",
        "bargain",
        "barrel",
        "base",
        "basic",
        "basket",
        "battle",
        "beach",
        "bean",
        "beauty",
        "because",
        "become",
        "beef",
        "before",
        "begin",
        "bomb",
        "bone",
        "bonus",
        "book",
        "boost",
        "border",
        "boring",
        "borrow",
        "boss",
        "bottom",
        "bounce",
        "box",
        "boy",
        "bracket",
        "brain",
        "brand",
        "brass",
        "brave",
        "bread",
        "breeze",
        "brick",
        "bridge",
        "brief",
        "bright",
        "bring",
        "brisk",
        "broccoli",
        "broken",
        "bronze",
        "broom",
        "brother",
        "brown",
        "brush",
        "canal",
        "cancel",
        "candy",
        "cannon",
        "canoe",
        "canvas",
        "canyon",
        "capable",
        "capital",
        "captain",
        "car",
        "carbon",
        "card",
        "cargo",
        "carpet",
        "carry",
        "cart",
        "case",
        "cash",
        "casino",
        "castle",
        "casual",
        "cat",
        "catalog",
        "catch",
        "category",
        "cattle",
        "caught",
        "cause",
        "caution",
        "cave",
        "ceiling",
        "celery",
        "cement",
        "census",
        "century",
        "cereal",
        "certain",
        "chair",
        "chalk",
        "champion",
        "change",
        "chaos",
        "chapter",
        "charge",
        "chase",
        "chat",
        "cheap",
        "check",
        "cheese",
        "chef",
        "cousin",
        "cover",
        "coyote",
        "crack",
        "cradle",
        "craft",
        "cram",
        "crane",
        "crash",
        "crater",
        "crawl",
        "crazy",
        "cream",
        "credit",
        "creek",
        "crew",
        "cricket",
        "crime",
        "crisp",
        "critic",
        "crop",
        "cross",
        "crouch",
        "crowd",
        "crucial",
        "cruel",
        "cruise",
        "crumble",
        "crunch",
        "crush",
        "delay",
        "deliver",
        "demand",
        "demise",
        "denial",
        "dentist",
        "deny",
        "depart",
        "depend",
        "deposit",
        "depth",
        "deputy"
      )
      def generateMnemonicPhrase(numbersOfWords: Int) = {
        val rnd = scala.util.Random

        for (i <- 1 to numbersOfWords) phrase += words(rnd.nextInt(words.length)) + " "

        <.p(^.className := "backup-phr-txt", phrase.trim)
      }

      def generateListOfWords() = {
        val rnd = scala.util.Random
        var warr = phrase.split(" ")

        //+ 6 random words
        for (i <- 0 to 5) warr :+= words(rnd.nextInt(words.length))

        shuffleArray(warr)

        warr

      }

      def shuffleArray(arr: Array[String]) = {
        val rnd = scala.util.Random
        for (i <- (1 to arr.length - 1).reverse) {
          var j = rnd.nextInt(i + 1)
          var tmp = arr(i)
          arr(i) = arr(j)
          arr(j) = tmp
        }
      }

      <.div(^.id := "bodyWallet")(
        <.div(
          ^.className := "backupView-main scrollableArea",
          <.div(
            ^.className := "backupView-start",
            <.p("- On the next screen you'll see 12 words that correspond to your first account."),
            <.p("- This is your account’s Backup Phrase."),
            <.p("- Later, you can enter it into Ubunda (or other wallets) to re-gain control over your account."),
            <.p("- Make sure you are in a private place and not being watched."),
            <.p("- Write down the exact Backup Phrase and keep it in a secure location."),
            <.p("- Do not take a picture or screenshot, since that might be easily copied."),
            <.p("- Anyone with access to your Backup Phrase can gain access to your account's funds."),
            <.p("- If you fail to maintain a copy of this phrase or lose access to this wallet, you will permanently lose access to the funds in this account."),
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
              <.p("Backup Phrase")
            ),
            <.div(
              ^.className := "generate-section-middle",
              <.div(
                ^.className := "mnemonicLangage",
                <.label(
                  ^.className := "col-lg-3 col-md-4 col-sm-4 col-xs-5",
                  "Display in:"
                ),
                <.select(
                  ^.className := "col-lg-3 col-md-4 col-sm-4 col-xs-3",
                  <.option("English"),
                  <.option("Japanese"),
                  <.option("Korean"),
                  <.option("Spanish"),
                  <.option("Chinese (Simplified)"),
                  <.option("Chinese (Traditional)"),
                  <.option("French"),
                  <.option("Italian")
                )
              ),
              <.div(
                ^.className := "row",
                <.div(
                  ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12", generateMnemonicPhrase(12)
                ),

                <.form(
                  ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
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
                )
              )
            ),
            <.div(
              ^.className := "generate-section-bottom",
              <.div(
                ^.className := "btnDefault-container",
                <.button(
                  ^.id := "btnNextGenerate", ^.onClick --> onBtnClicked("btnNextGenerate"),
                  ^.`type` := "button", ^.className := "btn btnDefault setdefault", "Next"
                )
              )
            )
          ),
          <.div(
            ^.className := "backupView-phrase",
            <.div(
              ^.className := "row ",
              <.div(
                ^.className := "col-xs-12",
                <.h3("Confirm your backup phrase"),
                <.div(
                  ^.className := "row backupPhrase-section",
                  <.div(
                    ^.className := "col-xs-12 backupPhrase-container"
                  )
                )
              )
            ),
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-xs-12 wordsList",
                <.p(^.id := "errorMessage", "Invalid phrase"),
                <.ul(
                  generateListOfWords().map(word =>
                  <.li(^.id := word, ^.onClick --> onItemClicked(word), word + " ")).toVdomArray
                )
              ),
              <.div(
                ^.className := "btnDefault-container",
                <.button(
                  ^.id := "btnResetPhrase", ^.onClick --> onBtnClicked("btnResetPhrase"),
                  ^.`type` := "button", ^.className := "btn btnDefault setdefault", "Reset phrase"
                ),
                <.button(
                  ^.id := "btnNextConfirmPhrase", ^.onClick --> onBtnClicked("btnNextConfirmPhrase"),
                  ^.`type` := "button", ^.className := "btn btnDefault setdefault", "Confirm"
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
                  <.h3("Your account is backed up."),
                  <.p("If this app is deleted or you lose access to your account, your account can be recovered using the Backup Mnemonic Phrase you have just secured.")
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
  }

  val component = ScalaComponent.builder[Props]("BackupAccountView")
    .renderBackend[Backend]
    // .componentDidMount(scope => scope.backend.componentDidMount())
    //.configure(Reusability.shouldComponentUpdate)
    .build
  def apply(props: Props) = component(props)
}
