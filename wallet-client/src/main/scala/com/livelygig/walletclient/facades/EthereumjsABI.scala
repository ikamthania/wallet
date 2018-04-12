package com.livelygig.walletclient.facades

import com.livelygig.shared.models.Solidity.SolidityType
import io.scalajs.nodejs.buffer.Buffer

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal("EthereumjsABI")
object EthereumjsABIFacade extends js.Object {
  def rawEncode(types: js.Array[String], values: js.Array[js.Any]): Buffer = js.native
}

object EthereumjsABI {
  def rawEncode(params: Seq[SolidityType]): Buffer = {
    val types: js.Array[String] = params.map(_.typeName).toJSArray
    val values: js.Array[js.Any] = params.map(_.typeMap[js.Any, js.Array[js.Any]](t => t.toString)(new MyCanBuildFrom).fold[js.Any](x => x, y => y)).toJSArray
    EthereumjsABIFacade.rawEncode(types, values)
  }
}

class MyBuilder extends mutable.LazyBuilder[Either[js.Any, js.Array[js.Any]], js.Array[js.Any]] {
  def result: js.Array[js.Any] = {
    (for (part <- parts.flatten) yield part.fold[js.Any](x => x, y => y)).toJSArray
  }
}

class MyCanBuildFrom extends CanBuildFrom[js.Array[js.Any], Either[js.Any, js.Array[js.Any]], js.Array[js.Any]] {
  def apply(from: js.Array[js.Any]): mutable.Builder[Either[js.Any, js.Array[js.Any]], js.Array[js.Any]] = apply()
  def apply(): mutable.Builder[Either[js.Any, js.Array[js.Any]], js.Array[js.Any]] = new MyBuilder
}