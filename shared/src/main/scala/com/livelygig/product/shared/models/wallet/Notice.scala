package com.livelygig.product.shared.models.wallet

import julienrf.json.derived
import play.api.libs.json.{ Format, JsValue, Json, __ }

sealed trait Notice {
  val read: Boolean
  val version: String = "v1.0"
  val date: String
  val title: String
  val body: JsValue
  val id: Int
}

case class TermsOfServiceNotice(read: Boolean, date: String, title: String, body: JsValue, id: Int) extends Notice

object TermsOfServiceNotice {
  def apply(): TermsOfServiceNotice = TermsOfServiceNotice(false, "", "Terms of Service", body, 1)

  val body = Json.obj {
    "Privacy Policy" -> Json.obj(
      "1. Introduction" -> Json.obj(
        "1.1" -> "LivelyGig recognizes that people value their privacy. This Privacy Policy details important information regarding the collection, use and disclosure of User information collected on the LivelyGig website located at",
        "livelygig_links" -> Json.arr("https://LivelyGig.com/", "https://LivelyGig.com/")))
  }
}

object Notice {
  implicit val format: Format[Notice] = derived.flat.oformat((__ \ "type").format[String])
}
