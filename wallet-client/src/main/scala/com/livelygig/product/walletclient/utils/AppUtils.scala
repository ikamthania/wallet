package com.livelygig.product.walletclient.utils

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
}
