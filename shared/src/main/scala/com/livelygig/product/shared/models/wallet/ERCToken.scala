package com.livelygig.product.shared.models.wallet

import play.api.libs.json.{ Format, Json }

case class TokenDetails(contractAddress: String, symbol: String, tokenName: String, decimal: Int, balance: String)

object TokenDetails {
  implicit val format: Format[TokenDetails] = Json.format
}
