package com.livelygig.walletclient.services

import org.scalajs.dom.ext.Ajax
import play.api.libs.json.{ Format, Json }

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

/**
 * Created by shubham.k on 22-03-2017.
 */

case class EthereumData(jsonrpc: String = "2.0", method: String, params: List[String], id: Int = 1)

object EthereumData {
  implicit val format: Format[EthereumData] = Json.format
}

object EthereumNodeApi {
  private val infuraUrl = "https://api.infura.io/v1/jsonrpc/ropsten"

  private def ajaxPost(apiUrl: String, requestContent: String): Future[String] = {
    Ajax.post(
      url = apiUrl,
      data = requestContent,
      headers = Map("Content-Type" -> "application/json;charset=UTF-8")).map(_.responseText)
  }

  private def ajaxGet(url: String): Future[String] = {
    Ajax.get(
      url = url).map(_.responseText)
  }

  def getTransactionCount(publicAddress: String): Future[String] = {
    val params = List(publicAddress, "pending")
    ajaxGet(s"${infuraUrl}/eth_getTransactionCount?params=${Json.toJson(params).toString()}")
  }
  def getBlockByNumber(): Future[String] = {
    //    val params = List("latest", true)
    ajaxGet(s"${infuraUrl}/eth_getBlockByNumber?params=[%22pending%22,true]")
  }

  def sendRawTransaction(rawTransaction: String): Future[String] = {
    ajaxPost(infuraUrl, Json.toJson(EthereumData(method = "eth_sendRawTransaction", params = List(rawTransaction))).toString())
  }
}

