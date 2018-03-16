package com.livelygig.product.walletclient.facades
import io.scalajs.nodejs.buffer.Buffer

import scala.scalajs.js
import scala.scalajs.js.annotation.{ JSImport, JSName }

@js.native
@JSImport("hdkey", JSImport.Namespace)
object HDKey extends js.Object {
  def fromMasterSeed(buffer: Buffer): HDKey.type = js.native
  def derive(path: String): HDKey.type = js.native
  def privateKey: Buffer = js.native
  def publicKey: Buffer = js.native
  def privateExtendedKey: String = js.native
  def publicExtendedKey: String = js.native
}