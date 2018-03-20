package com.livelygig.product.walletclient.utils

import com.livelygig.product.shared.models.wallet.{ Account, Vault, VaultData }
import com.livelygig.product.walletclient.facades.{ HDKey, _ }
import com.livelygig.product.walletclient.handler.{ AddNewAccount, UpdateVault }
import com.livelygig.product.walletclient.services.{ CoreApi, EthereumNodeApi, WalletCircuit }
import diode.AnyAction._
import io.scalajs.nodejs.buffer.Buffer
import play.api.libs.json.Json
import play.api.libs.functional.syntax._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object TestApi2 {
  def printWorkflow = {
    val strongPassword = "my$trongP@ssword"
    println(Mnemonic.generateMnemonic())

    val mnemonicPhrase = "raw congress foam fold true trick fold pen fox ozone cricket cotton"
    // scalastyle:off
    println(s"Root model created without vault ${Json.toJson(WalletCircuit.zoomTo(_.appRootModel.appModel).value)})")

    println(s"Creating master private extended key with mnemonic phrase ${mnemonicPhrase}")

    val seed = Mnemonic.mnemonicToSeed("raw congress foam fold true trick fold pen fox ozone cricket cotton")
    println(s"Seed = ${seed.toString("hex")}")

    val hdKey = HDKey.fromMasterSeed(seed)

    val privateExtendedKey = hdKey.privateExtendedKey

    println(s"Private Extended Key created ${privateExtendedKey}")

    val hdKeyFromExtended = HDKey.fromExtendedKey(hdKey.privateExtendedKey)

    val child1 = hdKey.derive("m/44'/60'/0'/0/0")
    val child2 = hdKey.derive("m/44'/60'/0'/0/1")
    println(s"Checking validity for child1 private key ${EthereumJsUtils.isValidPrivate(child1.privateKey)}")
    println(s"child1 private key ${child1.privateKey.toString("hex")}")
    println(s"child2 private key ${child2.privateKey.toString("hex")}")
    println(s"Getting child1 address ${EthereumJsUtils.privateToAddress(child1.privateKey).toString("hex")}")
    println(s"Getting child2 address ${EthereumJsUtils.privateToAddress(child2.privateKey).toString("hex")}")

    val child1Address = EthereumJsUtils.privateToAddress(child1.privateKey).toString("hex")
    val child2Address = EthereumJsUtils.privateToAddress(child2.privateKey).toString("hex")

    EthereumNodeApi.getTransactionCount(s"0x${child1Address}").map {
      res =>
        val res2 = (Json.parse(res) \ "result")
        try {
          println(EthereumjsUnits.convert("0.01", "eth", "wei"))
        } catch {
          case e: Exception => e.printStackTrace()
        }
        println(res2)
        val myTransaction = new Transaction(res, "0x1e8f1c10800", "0x301114", s"0x${child2Address}", s"0x${EthereumjsUnits.convert("0.1", "eth", "wei")}")
        myTransaction.sign(child1.privateKey)
        println(myTransaction.serialize().toString("hex"))
        println(s"Verifying the sender : ${myTransaction.getSenderAddress().toString("hex")}")
        CoreApi.mobileSendSignedTxn(s"0x${myTransaction.serialize().toString("hex")}").map {
          res2 =>
            println(res2)
        }.recover {
          case e: Exception => println(e.getMessage())
        }
    }

  }
}

