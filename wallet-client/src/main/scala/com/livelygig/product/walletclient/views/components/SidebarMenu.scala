package com.livelygig.product.walletclient.views.components

import com.livelygig.product.walletclient.handler.ChangeLang
import com.livelygig.product.walletclient.router.ApplicationRouter.{Loc, _}
import com.livelygig.product.walletclient.services.{CoreApi, WalletCircuit}
import com.livelygig.product.walletclient.utils.I18N
import diode.AnyAction._
import diode.ModelRO
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^.{^, _}
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import org.querki.jquery.$
import org.scalajs.dom
import org.scalajs.dom.raw.Element

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.util.{Failure, Success}

object SidebarMenu {

  val userDetails = WalletCircuit.zoom(_.user.userDetails)

  case class Props(router: RouterCtl[Loc], currentLoc: Loc)

  case class State(lang: js.Dynamic)

  private abstract class MenuItem(itemLabel: String)

  private case class MenuItemWithSubItems(itemLabel: String, submenuItems: Seq[MenuItem]) extends MenuItem(itemLabel)

  private case class MenuItemWithLocation(itemLabel: String, location: Loc) extends MenuItem(itemLabel)

  private case class MenuItemWithCallback(itemLabel: String, callback: Callback) extends MenuItem(itemLabel)

  private case class MenuItemWithNewTabLink(itemLabel: String, link: String) extends MenuItem(itemLabel)

  def toggleNav(): Callback = {
    $("#mySidenav").toggleClass("fullWidth")
    $("#closebtnContainer").toggleClass("active")
    $("#bodyWallet").toggleClass("blurBackground")
    Callback.empty
  }

  def emptyCallback = Callback.empty

  def changeTheme(themeName: String) = Callback {
    dom.window.localStorage.setItem("theme", themeName)
    toggleNav()
    $("#theme-stylesheet")
      .foreach((ele: Element) =>
        ele
          .setAttribute("href", s"../assets/stylesheets/wallet/themes/wallet-main-theme-${themeName}.min.css"))
  }

  private class Backend(t: BackendScope[Props, State]) {

    def changeLang(lang: String) = {
      Callback(CoreApi.getLang(lang).onComplete {
        case Success(res) => {
          WalletCircuit.dispatch(ChangeLang(JSON.parse(res)))
        }
        case Failure(_) => println(s"failed to load language for ${lang}")
      })
    }
    def signOut() = {
      Callback({
        dom.window.location.href = "./login.html"
      })
    }

    def updateMenuItemState(state: js.Dynamic) = {
      //testing items
      val test1SubmenuItem = MenuItemWithCallback(state.selectDynamic("TEST1").toString, emptyCallback)
      val test2SubmenuItem = MenuItemWithCallback(state.selectDynamic("TEST2").toString, emptyCallback)
      //Identity submenu items
      val indentitiesprofileSubmenuItem = MenuItemWithLocation(state.selectDynamic("SELECT").toString, IdentitiesLoc)
      val identitiesmanageSubmenuItem = MenuItemWithLocation(state.selectDynamic("MANAGE").toString, ManageIdentitiesLoc)
      //Identities submenu items
      val identityHomeSubmenuItem = MenuItemWithCallback(state.selectDynamic("HOME").toString, emptyCallback)
      val identityPortifolioSubmenuItem = MenuItemWithCallback(state.selectDynamic("PORTIFOLIO").toString, emptyCallback)
      val identityAccountsSubmenuItem = MenuItemWithCallback(state.selectDynamic("ACCOUNTS").toString, emptyCallback)
      val identityUpdateSubmenuItem = MenuItemWithCallback(state.selectDynamic("UPDATE").toString, emptyCallback)
      val identityAttributesSubmenuItem = MenuItemWithCallback(state.selectDynamic("ATTRIBUTES").toString, emptyCallback)
      val identityCredentialsSubmenuItem = MenuItemWithCallback(state.selectDynamic("CREDENTIALS").toString, emptyCallback)
      val identityConnectionsSubmenuItem = MenuItemWithCallback(state.selectDynamic("CONNECTIONS").toString, emptyCallback)
      val identityTransactionSubmenuItem = MenuItemWithCallback(state.selectDynamic("TRANSACTIONS").toString, emptyCallback)
      //Account submenu items
      val accountHomeSubmenuItem = MenuItemWithLocation(state.selectDynamic("HOME").toString, AccountLoc)
      val accountSendSubmenuItem = MenuItemWithLocation(state.selectDynamic("SEND").toString, SendLoc)
      val accountRequestSubmenuItem = MenuItemWithLocation(state.selectDynamic("REQUEST").toString, RequestLoc)
      val accountViewRequestsSubmenuItem = MenuItemWithCallback(state.selectDynamic("VIEW_REQUESTS").toString, emptyCallback)
      val accountTransactionHistorySubmenuItem = MenuItemWithLocation(state.selectDynamic("TRANSACTION_HISTORY").toString, HistoryLoc)
      val accountBackUpSubmenuItem = MenuItemWithCallback(state.selectDynamic("BACK_UP").toString, emptyCallback)
      val accountConfigureSubmenuItem = MenuItemWithCallback(state.selectDynamic("CONFIGURE").toString, emptyCallback)
      val accountExchangeSubmenuItem = MenuItemWithSubItems(state.selectDynamic("EXCHANGE").toString, Seq(test1SubmenuItem, test2SubmenuItem))
      val allAccountsHomeSubmenuItem = MenuItemWithLocation(state.selectDynamic("VIEW_ACCOUNTS").toString, AllAccountsLoc)
      val multiSigHomeSubmenuItem = MenuItemWithLocation(state.selectDynamic("MULTI_SIG_WALLET").toString, MultisigHomeLoc)
      val AddTokenSubmenuItem = MenuItemWithLocation(state.selectDynamic("ADD_TOKENS").toString, AddTokenLoc)
      val AddSharedWalletHomeSubmenuItem = MenuItemWithLocation(state.selectDynamic("ADD_SHARED_WALLET").toString, AddSharedWalletLoc)
      //Seetings
      //Themes
      val darkThemeSubmenuItem = MenuItemWithCallback(state.selectDynamic("DARK_THEME").toString, changeTheme("default"))
      val lightThemeSubmenuItem = MenuItemWithCallback(state.selectDynamic("LIGHT_THEME").toString, changeTheme("light"))
      val seetingsDisplaySettingSubmenuItem = MenuItemWithSubItems(state.selectDynamic("DISPLAY_THEME").toString, Seq(darkThemeSubmenuItem, lightThemeSubmenuItem))

      val settingApprovalsSubmenuItem = MenuItemWithCallback(state.selectDynamic("APPROVALS").toString, emptyCallback)
      val settingBackupApplicationApprovalsSubmenuItem = MenuItemWithCallback(state.selectDynamic("BACKUP_APPLICATION").toString, emptyCallback)
      val settingResetApplicationApprovalsSubmenuItem = MenuItemWithCallback(state.selectDynamic("RESET_APPLICATION").toString, emptyCallback)
      //Language
      val en_us_languageSubmenuItem = MenuItemWithCallback(state.selectDynamic("ENGLISH").toString, changeLang(I18N.Lang.en_us))
      val fr_languageSubmenuItem = MenuItemWithCallback(state.selectDynamic("FRENCH").toString, changeLang(I18N.Lang.fr))
      val pseudo_languageSubmenuItem = MenuItemWithCallback(state.selectDynamic("PSUEDO_LOCALIZATION").toString, changeLang(I18N.Lang.pseudo_loc))
      val settingsLanguageSubmenuitem = MenuItemWithSubItems(state.selectDynamic("LANGUAGE").toString, Seq(en_us_languageSubmenuItem, fr_languageSubmenuItem, pseudo_languageSubmenuItem))

      val settingManageIntegrationsSubmenuItem = MenuItemWithCallback(state.selectDynamic("MANAGE_INTEGRATIONS").toString, emptyCallback)
      val settinTokenUnitsDecimalsSubmenuItem = MenuItemWithCallback(state.selectDynamic("TOKEN_UNITS_DECIMALS").toString, emptyCallback)
      val settinDisplayedGovernmentCurrenciesSubmenuItem = MenuItemWithCallback(state.selectDynamic("DISPLAYED_GOVERNMENT_CURRENCIES").toString, emptyCallback)
      val settinApplicationLockingSubmenuItem = MenuItemWithCallback(state.selectDynamic("APPLICATION_LOCKING").toString, emptyCallback)
      val settinManageExchangesSubmenuItem = MenuItemWithCallback(state.selectDynamic("MANAGE_EXCHANGES").toString, emptyCallback)
      val settinSelectBlockchainNetworkSubmenuItem = MenuItemWithCallback(state.selectDynamic("SELECT_BLOCKCHAIN_NETWORK").toString, emptyCallback)

      //Help & Support
      val helpsupportgOverviewSubmenuItem = MenuItemWithCallback(state.selectDynamic("OVERVIEW").toString, emptyCallback)
      val helpsupportSendFeedbackSubmenuItem = MenuItemWithCallback(state.selectDynamic("SEND_FEEDBACK").toString, emptyCallback)
      val helpsupportBulletinSubmenuItem = MenuItemWithCallback(state.selectDynamic("BULLETIN").toString, emptyCallback)
      //About
      val aboutCopyrightSubmenuItem = MenuItemWithCallback(state.selectDynamic("COPYRIGHT").toString, emptyCallback)
      val aboutPrivacyPolicySubmenuItem = MenuItemWithCallback(state.selectDynamic("PRIVACY_POLICY").toString, emptyCallback)
      val aboutTermsConditionsSubmenuItem = MenuItemWithCallback(state.selectDynamic("TERMS_CONDITION").toString, emptyCallback)

      val hiddenMenuItems = Seq(
        MenuItemWithSubItems(s"${state.selectDynamic("ACCOUNT_SAVINGS").toString} : ${userDetails.value.alias}", Seq(accountHomeSubmenuItem, accountSendSubmenuItem, accountRequestSubmenuItem,
          accountTransactionHistorySubmenuItem, allAccountsHomeSubmenuItem, multiSigHomeSubmenuItem, AddTokenSubmenuItem, AddSharedWalletHomeSubmenuItem /*, accountBackUpSubmenuItem*/ )),
        MenuItemWithSubItems(state.selectDynamic("SETTINGS").toString, Seq(seetingsDisplaySettingSubmenuItem, settingsLanguageSubmenuitem /*, settinTokenUnitsDecimalsSubmenuItem*/ )),
        //MenuItemWithSubItems(state.selectDynamic("HELP_SUPPORT").toString, Seq(helpsupportgOverviewSubmenuItem, helpsupportBulletinSubmenuItem)),
        MenuItemWithSubItems(state.selectDynamic("ABOUT").toString, Seq( /*aboutCopyrightSubmenuItem, */ aboutPrivacyPolicySubmenuItem, aboutTermsConditionsSubmenuItem)),
        MenuItemWithCallback(state.selectDynamic("SIGN_OUT").toString, signOut())
      )

      val fullMenuItems = Seq(
        MenuItemWithSubItems(state.selectDynamic("IDENTITIES").toString, Seq(indentitiesprofileSubmenuItem, identitiesmanageSubmenuItem)),
        MenuItemWithSubItems(state.selectDynamic("IDENTITY").toString, Seq(identityHomeSubmenuItem, identityPortifolioSubmenuItem,
          identityAccountsSubmenuItem, identityUpdateSubmenuItem, identityAttributesSubmenuItem, identityCredentialsSubmenuItem, identityConnectionsSubmenuItem,
          identityTransactionSubmenuItem)),
        MenuItemWithSubItems(s"${state.selectDynamic("ACCOUNT_SAVINGS").toString} : ${userDetails.value.alias}", Seq(accountHomeSubmenuItem, accountSendSubmenuItem, accountRequestSubmenuItem,
          accountViewRequestsSubmenuItem, accountTransactionHistorySubmenuItem, accountBackUpSubmenuItem, accountConfigureSubmenuItem,
          accountExchangeSubmenuItem)),
        MenuItemWithCallback(state.selectDynamic("CONTACTS").toString, emptyCallback),
        //        MenuItemWithCallback(state.selectDynamic("CONNECTIONS").toString, emptyCallback),
        MenuItemWithSubItems(state.selectDynamic("MESSAGES").toString, Seq(test1SubmenuItem, test2SubmenuItem)),
        MenuItemWithSubItems(state.selectDynamic("CHANNELS").toString, Seq(test1SubmenuItem, test2SubmenuItem)),
        MenuItemWithCallback(state.selectDynamic("TIMESTAMPS").toString, emptyCallback),
        MenuItemWithCallback(state.selectDynamic("CONTENT").toString, emptyCallback),
        MenuItemWithLocation(state.selectDynamic("NOTIFICATIONS").toString, NotificationLoc),
        MenuItemWithSubItems(state.selectDynamic("SETTINGS").toString, Seq(seetingsDisplaySettingSubmenuItem, settingApprovalsSubmenuItem, settingBackupApplicationApprovalsSubmenuItem,
          settingResetApplicationApprovalsSubmenuItem, settingsLanguageSubmenuitem, settingManageIntegrationsSubmenuItem, settinTokenUnitsDecimalsSubmenuItem,
          settinDisplayedGovernmentCurrenciesSubmenuItem, settinApplicationLockingSubmenuItem, settinManageExchangesSubmenuItem, settinSelectBlockchainNetworkSubmenuItem)),

        MenuItemWithSubItems(state.selectDynamic("HELP_SUPPORT").toString, Seq(helpsupportgOverviewSubmenuItem, helpsupportSendFeedbackSubmenuItem,
          helpsupportBulletinSubmenuItem)),
        MenuItemWithSubItems(state.selectDynamic("ABOUT").toString, Seq(aboutCopyrightSubmenuItem, aboutPrivacyPolicySubmenuItem, aboutTermsConditionsSubmenuItem)),
        MenuItemWithCallback(state.selectDynamic("DIRECTORY").toString, emptyCallback),
        MenuItemWithCallback(state.selectDynamic("SHARE_UBUNDA").toString, emptyCallback),
        MenuItemWithSubItems(state.selectDynamic("TEST").toString, Seq(test1SubmenuItem, test2SubmenuItem)),
        MenuItemWithCallback(state.selectDynamic("SIGN_OUT").toString, signOut())
      )

      val isAlphaVersion = true

      if (isAlphaVersion) hiddenMenuItems
      else fullMenuItems
    }

    def toggleDropdownArrow(id: String, liId: String) = Callback {
      if (!$(s"#$id").hasClass("active")) { ($(s"#$id").toggleClass("active")) }
      $(s"#$liId").toggleClass("openSubmenu")
    }

    def isChildCurrentLocation(submenuItems: Seq[MenuItem]): Boolean = {
      submenuItems.find {
        submenuItem =>
          submenuItem match {
            case m: MenuItemWithLocation if (m.location == t.props.runNow().currentLoc) => true
            case _ => false
          }
      }.isDefined
    }

    def mounted(props: Props): Callback = {
      WalletCircuit.subscribe(WalletCircuit.zoom(_.i18n.language))(e => updateLang(e))
      Callback.empty
    }

    def updateLang(reader: ModelRO[js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def createItem(menuItem: MenuItem): VdomNode = {
      menuItem match {
        case mc: MenuItemWithCallback =>
          <.li(<.a(mc.itemLabel, ^.onClick --> mc.callback))
        case ml: MenuItemWithLocation => {
          <.li(^.id := ml.itemLabel, ^.onClick --> toggleNav())(
            (^.className := "active").when(ml.location == t.props.runNow().currentLoc),
            t.props.runNow().router.link(ml.location)(ml.itemLabel)
          )
        }
        case ms: MenuItemWithSubItems =>
          var id = ms.itemLabel.toLowerCase.replaceAll("\\[|\\]|\\&|\\: | ", "_")

          <.li(
            (^.className := "openSubmenu").when(isChildCurrentLocation(ms.submenuItems)),
            ^.id := id,
            <.a(
              ^.id := s"${id}-1",
              ^.onClick --> toggleDropdownArrow(s"${id}-1", id),
              VdomAttr("data-toggle") := "collapse",
              VdomAttr("data-target") := s"#${id}-2",
              VdomAttr("aria-expanded") := "false", ^.className := "off", ms.itemLabel,
              <.i(^.className := "fa fa-chevron-down", VdomAttr("aria-hidden") := "true")
            ),
            <.ul(^.className := "nav collapse", (^.className := "in").when(isChildCurrentLocation(ms.submenuItems)),
              ^.id := s"${id}-2", ^.role := "menu", VdomAttr("aria-labelledby") := s"${id}-1")(ms.submenuItems map createItem: _*)
          )
        case _ => <.div()
      }
    }
    def render(props: Props, state: State) = {
      <.div(^.className := "menuToggle-inner")(updateMenuItemState(state.lang) map createItem: _*)
    }
  }

  private val component = ScalaComponent.builder[Props]("SideBarMenu")
    .initialState(State(WalletCircuit.zoom(_.i18n.language).value))
    .renderBackend[Backend]
    .componentWillMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(ctl: RouterCtl[Loc], currentLoc: Loc): VdomElement =
    component(Props(ctl, currentLoc))
}
