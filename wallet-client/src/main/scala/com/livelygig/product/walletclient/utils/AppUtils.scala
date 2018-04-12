package com.livelygig.product.walletclient.utils

import com.livelygig.product.walletclient.facades.QRCode
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.product.walletclient.services.WalletCircuit
import japgolly.scalajs.react.Callback
import org.scalajs.dom

/**
 * Created by shubham.k on 09-03-2017.
 */
object AppUtils {
  def getCookie(name: String): Option[String] = {
    val parts = dom.document.cookie.split("; ")
    val mapstring = parts.map(_.split("=")).map(arr => arr(0) -> arr(1)).toMap
    mapstring.get(name)
  }
  lazy val errorMessage = "We having trouble in loading results at this time. Please try after sometime"

  def getQRCode(): QRCode = {
    val qrCode = new QRCode("3", "L")
    qrCode.addData(WalletCircuit.zoomTo(_.appRootModel.appModel.data.accountInfo.selectedAddress).value)
    qrCode.make()
    qrCode
  }

}
