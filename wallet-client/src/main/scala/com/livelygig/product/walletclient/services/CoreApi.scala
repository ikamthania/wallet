package com.livelygig.product.walletclient.services

import org.scalajs.dom.ext.Ajax
import play.api.libs.json.Json

import scala.concurrent.Future
import scala.scalajs.LinkingInfo
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

/**
 * Created by shubham.k on 22-03-2017.
 */
object CoreApi {
  private val apiVersion = if (LinkingInfo.productionMode) "http://52.32.124.115:9000/v1" else "/v1"

  private val infuraUrl = "https://api.infura.io/v1/jsonrpc/ropsten"

  private def ajaxPost(requestContent: String, apiUrl: String): Future[String] = {
    Ajax.post(
      url = apiUrl,
      data = requestContent,
      headers = Map("Content-Type" -> "application/json;charset=UTF-8" /*, "Csrf-Token" -> AppUtils.getCookie("livelygig-csrf").get*/ )).map(_.responseText)
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

}

