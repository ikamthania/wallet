package com.livelygig.walletclient.facades
import io.scalajs.nodejs.buffer.Buffer

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("hdkey", JSImport.Namespace)
object HDKey extends js.Object {
  def fromMasterSeed(buffer: Buffer): HDKey.type = js.native
  def fromExtendedKey(base58key: String): HDKey.type = js.native
  def derive(path: String): HDKey.type = js.native
  def deriveChild(index: Int): HDKey.type = js.native
  def privateKey: Buffer = js.native
  def privateExtendedKey: Buffer = js.native
  def publicExtendedKey: Buffer = js.native
}