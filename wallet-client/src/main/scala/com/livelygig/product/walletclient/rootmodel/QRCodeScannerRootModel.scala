package com.livelygig.product.walletclient.rootmodel

case class QRCodeScannerRootModel(pubKey: String) {

  //todo add wallet user name as well.

  def updated(publicKey: String): QRCodeScannerRootModel = {
    QRCodeScannerRootModel(publicKey)

  }
}
