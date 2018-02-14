package com.livelygig.product.shared.models.wallet

import play.api.libs.json.{ Format, Json }

case class EtherTransaction(password: String, receiver: String, amount: String, txnType: String, decimal: Int)

case class Transaction(blockNumber: String, timeStamp: String, hash: String, nonce: String, blockHash: String,
  transactionIndex: String, from: String, to: String, value: String, gas: String,
  gasPrice: String, isError: String, input: String, contractAddress: String,
  cumulativeGasUsed: String, gasUsed: String, confirmations: String)

case class TransactionWithSymbol(transaction: Transaction, symbol: String)

object EtherTransaction {
  implicit val format = Json.format[EtherTransaction]
}

object TransactionWithSymbol {
  implicit val format = Json.format[TransactionWithSymbol]
}

case class AccountBalanceDetails(coinType: String, amount: String, coinAmtUSD: String, coinAmtIND: String)

object AccountBalanceDetails {
  implicit val format = Json.format[AccountBalanceDetails]
}

object Transaction {
  implicit val format: Format[Transaction] = Json.format
}

case class WalletDetails(publicKey: String, balance: String)

object WalletDetails {
  implicit val format = Json.format[WalletDetails]
}

case class UserDetails(alias: String, walletDetails: WalletDetails)

object UserDetails {
  implicit val format = Json.format[UserDetails]
}

case class QrCode(qrcode: String)

object QrCode {
  implicit val format = Json.format[QrCode]
}
/*

case class Currencies(ETH: Double, USD: Double, EUR: Double, IND: Double)

object Currencies {
  implicit val format = Json.format[Currencies]
}
*/

case class Currency(symbol: String, price: Double, icon: String)

object Currency {
  implicit val format: Format[Currency] = Json.format
}

case class CurrencyList(coin: String, currencies: Seq[Currency])

object CurrencyList {
  implicit val format: Format[CurrencyList] = Json.format
}

case class CoinExchange(coinExchangeList: Seq[CurrencyList])

object CoinExchange {
  implicit val format: Format[CoinExchange] = Json.format
}

case class KeyStoreDataList(privateKey: String, publicKey: String)

object KeyStoreDataList {
  implicit val format: Format[KeyStoreDataList] = Json.format
}

case class KeyStoreData(keystoreDataList: Seq[KeyStoreDataList])

object KeyStoreData {
  implicit val format: Format[KeyStoreData] = Json.format
}

case class KeyStoreContent(keystorePvtKey: String, keystorePubKey: String)

object KeyStoreContent {
  implicit val format: Format[KeyStoreContent] = Json.format
}

case class KeyStoreContentList(keystorecontent: Seq[KeyStoreContent])

object KeyStoreContentList {
  implicit val format: Format[KeyStoreContentList] = Json.format
}

case class SignedTxnParams(nonce: String, encodedFunction: String, amntInWei: String)

object SignedTxnParams {
  implicit val format: Format[SignedTxnParams] = Json.format
}