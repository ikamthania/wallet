package com.livelygig.walletclient.services

import com.livelygig.shared.models.wallet.EtherTransaction
import org.scalajs.dom.ext.Ajax
import play.api.libs.json.Json

import scala.concurrent.Future
import scala.scalajs.LinkingInfo
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

/**
 * Created by shubham.k on 22-03-2017.
 */
object CoreApi {
  private val apiVersion = if (LinkingInfo.productionMode) "https://dev4:devops777@ubunda.com/v1" else "/v1"

  private def ajaxPost(requestContent: String, apiUrl: String): Future[String] = {
    Ajax.post(
      url = apiUrl,
      data = requestContent,
      headers = Map("Content-Type" -> "application/jsumjs ton;charset=UTF-8")).map(_.responseText)
  }

  private def ajaxGet(url: String): Future[String] = {
    Ajax.get(
      url = url).map(_.responseText)
  }

  def authenticate = {
    ajaxGet(s"${apiVersion}/auth/")
  }

  def getLang(lang: String) = ajaxGet(s"${apiVersion}/wallet/mobile/language/${lang}")

  def getTransactionStatus(txnHash: String) = ajaxGet(s"${apiVersion}/wallet/mobile/status/${txnHash}")

  def mobileGetAccountDetails(pubKey: String) = ajaxGet(s"${apiVersion}/wallet/mobile/${pubKey}/account/erctoken/details")

  def mobileGetAccountHistoryDetails(pubKey: String) = ajaxGet(s"${apiVersion}/wallet/mobile/${pubKey}/transactions")

  def mobileGetLivePrices() = ajaxGet(s"${apiVersion}/wallet/mobile/coin/prices")

  def mobileGetETHNetInfo() = ajaxGet(s"${apiVersion}/wallet/mobile/ethnet/info")

  def mobileGetLang(lang: String) = ajaxGet(s"${apiVersion}/wallet/mobile/language/${lang}")

  def mobileGetTransactionStatus(txnHash: String) = ajaxGet(s"${apiVersion}/wallet/mobile/status/${txnHash}")

  def mobileSendSignedTxn(signedTxn: String) =
    ajaxPost(Json.stringify(Json.toJson(signedTxn)), s"${apiVersion}/wallet/mobile/signedTxn")
  def mobileGetEncodedFunction(txnInfo: EtherTransaction) =
    ajaxPost(Json.stringify(Json.toJson[EtherTransaction](txnInfo)), s"${apiVersion}/wallet/mobile/encodedfunction")

}

