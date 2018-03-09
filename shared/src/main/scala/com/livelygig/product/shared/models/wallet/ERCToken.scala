package com.livelygig.product.shared.models.wallet

import play.api.libs.json.{ Format, Json }

case class ERC20ComplientToken(contractAddress: String, symbol: String, tokenName: String, decimal: Int, balance: String)

object ERC20ComplientToken {
  implicit val format: Format[ERC20ComplientToken] = Json.format
}
