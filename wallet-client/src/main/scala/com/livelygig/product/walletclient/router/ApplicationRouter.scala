package com.livelygig.product.walletclient.router

/**
 * Created by shubham.k on 16-03-2017.
 */

import com.livelygig.product.walletclient.services.WalletCircuit
import com.livelygig.product.walletclient.views._
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.Implicits._

// scalastyle:off
object ApplicationRouter {

  sealed trait Loc

  case object LandingLoc extends Loc
  case object TermsOfServicesLoc extends Loc
  case object SetupLoc extends Loc
  case object SetupRegisterLoc extends Loc

  case object SetupAccountLoc extends Loc
  case object LoginLoc extends Loc

  case object PersonasLoc extends Loc
  case object ManageLoc extends Loc
  case object ProfilesLoc extends Loc

  case object PortfolioLoc extends Loc
  case object Test1Loc extends Loc
  case object Test2Loc extends Loc

  case object AccountLoc extends Loc
  case object HomeLoc extends Loc
  case object SendLoc extends Loc
  case object RequestLoc extends Loc
  // case object TransactionLoc extends Loc

  case object HistoryLoc extends Loc

  case object IdentitiesLoc extends Loc

  case object ManageIdentitiesLoc extends Loc

  case object NotificationLoc extends Loc

  case object InitialSetupLoc extends Loc

  case object BackupIdentityLoc extends Loc
  case object BackupAccountLoc extends Loc

  case object TermsOfServiceLoc extends Loc

  case object AddNewAccountLoc extends Loc
  case object AllAccountsLoc extends Loc
  case object AddSharedWalletLoc extends Loc
  case object MultisigHomeLoc extends Loc
  case object AddTokenLoc extends Loc

  case object TestLoc extends Loc

  case object ViewBackupPhraseLoc extends Loc
  case object ConfirmBackupPhraseLoc extends Loc
  case object ConfirmedBackupPhraseLoc extends Loc

  case class PopulateQRCodeLoc(to: String) extends Loc

  private val walletaccountProxy = WalletCircuit.connect(_.ERCToken)
  private val walletuserProxy = WalletCircuit.connect(_.user)
  private val wallethistoryProxy = WalletCircuit.connect(_.transaction)
  // configure the router
  private val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._
    (staticRoute(root, LandingLoc) ~> render(LandingView.component())
      | staticRoute(s"#/terms", TermsOfServiceLoc) ~> render(TermsOfServiceView.component())
      | staticRoute(s"#/account", AccountLoc) ~> renderR(ctl => walletaccountProxy(proxy => AccountView.component(AccountView.Props(proxy, ctl))))
      | staticRoute(s"#/setup", SetupLoc) ~> renderR(ctl => SetupView.component(SetupView.Props(ctl)))
      | staticRoute(s"#/login", LoginLoc) ~> renderR(ctl => LoginView.component(LoginView.Props(ctl)))
      | staticRoute(s"#/backup", BackupAccountLoc) ~> renderR(ctl => BackupAccountTerms.component(BackupAccountTerms.Props(ctl)))
      | staticRoute(s"#/backup/storephrase", ViewBackupPhraseLoc) ~> renderR(ctl => ViewBackupPhrase.component(ViewBackupPhrase.Props(ctl)))
      | staticRoute(s"#/backup/putphrase", ConfirmBackupPhraseLoc) ~> renderR(ctl => ConfirmBakupPhrase.component(ConfirmBakupPhrase.Props(ctl)))
      | staticRoute(s"#/backup/confirm", ConfirmedBackupPhraseLoc) ~> renderR(ctl => ConfirmedBackupPhrase.component(ConfirmedBackupPhrase.Props(ctl)))
      | staticRoute(s"#/setup/register", SetupRegisterLoc) ~> renderR(ctl => SetupRegisterView.component(SetupRegisterView.Props(ctl)))
      | staticRoute(s"#/send", SendLoc) ~> renderR(ctl => walletaccountProxy(proxy => SendView.component(SendView.Props(proxy, ctl, ""))))
      | staticRoute(s"#/request", RequestLoc) ~> renderR(ctl => RequestView(RequestView.Props(ctl)))
      | staticRoute(s"#/history", HistoryLoc) ~> renderR(ctl => wallethistoryProxy(proxy => HistoryView.component(HistoryView.Props(proxy))))
      | staticRoute(s"#/manageIdentities", ManageIdentitiesLoc) ~> renderR(ctl => ManageIdentitiesView(ManageIdentitiesView.Props()))
      | staticRoute(s"#/identities", IdentitiesLoc) ~> renderR(ctl => IdentitiesView(IdentitiesView.Props()))
      | staticRoute(s"#/notification", NotificationLoc) ~> renderR(ctl => NotificationView(NotificationView.Props()))
      //      | staticRoute(s"#/termsOfService", TermsOfServiceLoc) ~> renderR(ctl => TermsOfServiceView(TermsOfServiceView.Props()))
      | staticRoute(s"#/allAccounts", AllAccountsLoc) ~> renderR(ctl => AllAccountsView(AllAccountsView.Props(ctl)))
      | staticRoute(s"#/addSharedWallet", AddSharedWalletLoc) ~> renderR(ctl => AddSharedWalletView.component(AddSharedWalletView.Props(ctl)))
      | staticRoute(s"#/multisig", MultisigHomeLoc) ~> renderR(ctl => walletaccountProxy(proxy => MultisigHomeView.component(MultisigHomeView.Props(proxy, ctl))))
      | staticRoute(s"#/addToken", AddTokenLoc) ~> renderR(ctl => AddTokenView(AddTokenView.Props()))
      | staticRoute(s"#/test", TestLoc) ~> renderR(ctl => TestView(TestView.Props()))
      | dynamicRouteCT(s"#/send" / remainingPath.caseClass[PopulateQRCodeLoc]) ~> dynRenderR((loc, ctl) =>
        walletaccountProxy(proxy => SendView.component(SendView.Props(proxy, ctl, s"${loc.to}")))))
      .notFound(redirectToPage(LandingLoc)(Redirect.Replace))

  }.renderWith(MainLayout.layout _)
  val router = Router(BaseUrl.until_#, routerConfig)
}
