package com.livelygig.product.walletclient.services

import com.livelygig.product.shared.models.wallet.EtherTransaction
import com.livelygig.product.walletclient.utils.AppUtils
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.window
import play.api.libs.json.Json

import scala.scalajs.LinkingInfo
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

/**
 * Created by shubham.k on 22-03-2017.
 */
object CoreApi {
  private val apiVersion = if (LinkingInfo.productionMode) "http://192.168.1.100:63376/v1" else "/v1"
  private var userUriForApi: Option[String] = None
  private val urlPath = dom.window.location.pathname
  private val pattern = "(0x[0-9A-Za-z]+)".r
  private val publicKey = window.localStorage.getItem("pubKey") // pattern.findFirstIn(urlPath).getOrElse("")
  //  window.alert(publicKey)

  private def ajaxPost(requestContent: String, apiUrl: String): Future[String] = {
    Ajax.post(
      url = apiUrl,
      data = requestContent,
      headers = Map("Content-Type" -> "application/json;charset=UTF-8" /*, "Csrf-Token" -> AppUtils.getCookie("livelygig-csrf").get*/ )).map(_.responseText)
  }

  private def ajaxGet(url: String): Future[String] = {
    Ajax.get(
      url = url
    /**
     * ,
     * headers = Map("Csrf-Token" -> AppUtils.getCookie("livelygig-csrf").get)
     */
    ).map(_.responseText)
  }

  def authenticate = {
    ajaxGet(s"${apiVersion}/auth/")
  }

  def getUserProfileOfCurrentUser() = ajaxGet(s"${apiVersion}/user${window.location.pathname}/profile")

  def sendEtherTransaction(etherTransaction: EtherTransaction) = {
    ajaxPost(Json.stringify(Json.toJson[EtherTransaction](etherTransaction)), s"${apiVersion}/wallet/transaction")
  }

  //def getEtherBalance() = ajaxGet(s"${apiVersion}/wallet/balance")

  // def getAccountDetails() = ajaxGet(s"${apiVersion}/wallet/accountdetails")
  def getUserDetails() = ajaxGet(s"${apiVersion}/wallet/mobile/account/details")

  def getAccountDetails() = ajaxGet(s"${apiVersion}/wallet/account/erctoken/details")

  def getAccountHistoryDetails() = ajaxGet(s"${apiVersion}/wallet/transactions")

  def getLang(lang: String) = ajaxGet(s"${apiVersion}/wallet/mobile/language/${lang}")

  def getTransactionStatus(txnHash: String) = ajaxGet(s"${apiVersion}/wallet/mobile/status/${txnHash}")

  def getNotification() = ajaxGet(s"${apiVersion}/wallet/mobile/notifications")

  def getQRCode() = ajaxGet(s"${apiVersion}/wallet/wallet/qrcode")

  def signOut() = ajaxPost("{}", s"${apiVersion}/wallet/auth/signout")

  def getETHNetInfo() = ajaxGet(s"${apiVersion}/wallet/ethnet/info")

  def captureQRCode() = ajaxGet(s"${apiVersion}/wallet/qrcode")

  def getLivePrices() = ajaxGet(s"${apiVersion}/wallet/coin/prices")

  //  API path Call for Mobile-App

  def mobileGetUserDetails() = ajaxGet(s"${apiVersion}/wallet/mobile/${dom.window.localStorage.getItem("pubKey")}/account/details")

  def mobileGetAccountDetails() = ajaxGet(s"${apiVersion}/wallet/mobile/${dom.window.localStorage.getItem("pubKey")}/account/erctoken/details")

  def mobileGetAccountHistoryDetails() = ajaxGet(s"${apiVersion}/wallet/mobile/${dom.window.localStorage.getItem("pubKey")}/transactions")

  def mobileGetLivePrices() = ajaxGet(s"${apiVersion}/wallet/mobile/coin/prices")

  def mobileGetETHNetInfo() = ajaxGet(s"${apiVersion}/wallet/mobile/ethnet/info")

  def mobileGetLang(lang: String) = ajaxGet(s"${apiVersion}/wallet/mobile/language/${lang}")

  def mobileGetTransactionStatus(txnHash: String) = ajaxGet(s"${apiVersion}/wallet/mobile/status/${txnHash}")

  def mobileGetNonce(txnInfo: EtherTransaction) =
    ajaxPost(Json.stringify(Json.toJson[EtherTransaction](txnInfo)), s"${apiVersion}/wallet/mobile/${dom.window.localStorage.getItem("pubKey")}/nonce")

  def mobileSendSignedTxn(signedTxn: String) =
    ajaxPost(Json.stringify(Json.toJson(signedTxn)), s"${apiVersion}/wallet/mobile/signedTxn")
}

