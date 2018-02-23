package com.livelygig.product.shared.models.wallet

import julienrf.json.derived
import play.api.libs.json.{ Format, Json, __ }

sealed trait ERCToken

case class ERC20ComplientToken(contractAddress: String, symbol: String, tokenName: String, decimal: Int, balance: String) extends ERCToken

object ERC20ComplientToken {
  implicit val format: Format[ERC20ComplientToken] = Json.format
}

object ERCToken {
  implicit val format: Format[ERCToken] =
    derived.flat.oformat((__ \ "type").format[String])
}
