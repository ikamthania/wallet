//package com.livelygig.product.walletclient.utils
//
//import com.livelygig.product.shared.models.wallet.{ Account, Vault, VaultData }
//import com.livelygig.product.walletclient.facades.{ HDKey, _ }
//import com.livelygig.product.walletclient.handler.{ AddNewAccount, UpdateVault }
//import com.livelygig.product.walletclient.services.{ CoreApi, EthereumNodeApi, WalletCircuit }
//import play.api.libs.json.Json
//
//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
//import diode.AnyAction._
//import io.scalajs.nodejs.buffer.Buffer
//
//import scala.scalajs.js
//
//object TestApi {
//  def printWorkflow = {
//    val strongPassword = "my$trongP@ssword"
//
//    val mnemonicPhrase = Mnemonic.generateMnemonic()
//    // scalastyle:off
//    println(s"Root model created without vault ${Json.toJson(WalletCircuit.zoomTo(_.appRootModel.appModel).value)})")
//
//    println(s"Creating master private extended key with mnemonic phrase ${mnemonicPhrase}")
//
//    val seed = Mnemonic.mnemonicToSeed("raw congress foam fold true trick fold pen fox ozone cricket cotton")
//    println(s"Seed = ${seed.toString("hex")}")
//
//    val hdKey = HDKey.fromMasterSeed(seed)
//
//    val privateExtendedKey = hdKey.privateExtendedKey
//
//    println(s"Private Extended Key created ${privateExtendedKey}")
//
//    println(s"Encrypting the mnemonic phrase to vault with password ${strongPassword}")
//
//    BrowserPassworder.encrypt(strongPassword, Json.toJson(VaultData(mnemonicPhrase)).toString()).toFuture.map {
//      res =>
//        println("Vault encrypted. Parsing it to data structure")
//        val vault = Json.parse(res).validate[Vault].get
//        println(s"Vault data ${Json.toJson(vault)}")
//        println("Adding vault to root model, local storage should update automatically")
//        WalletCircuit.dispatch(UpdateVault(Some(vault)))
//        println("Checking decryption of the vault")
//        BrowserPassworder.decrypt(strongPassword, Json.toJson(vault).toString()).toFuture.map {
//          decrypt =>
//            println(s"Vault decrypted, here is the mnemonic phrase from vault : ${decrypt.toString}")
//
//            val vaultData = Json.parse(decrypt.toString).validate[VaultData].get
//
//            println("Getting first child key and adding to root model")
//
//            val hdKeyFromMnemonic = HDKey.fromMasterSeed(Mnemonic.mnemonicToSeed(vaultData.mnemonicPhrase))
//            val childone = hdKeyFromMnemonic.deriveChild(0)
//            try {
//              println(s"this is the address : ${EthereumJsUtils.publicToAddress(childone.publicKey).toString("hex")}")
//            } catch {
//              case e: Exception =>
//                println(e.printStackTrace())
//            }
//            println(s"child one public key ${childone.publicKey}")
//            WalletCircuit.dispatch(AddNewAccount(Account(childone.publicKey.toString("hex"), s"Account ${WalletCircuit.zoomTo(_.appRootModel.appModel.data.keyrings.accounts).value.length + 1}")))
//
//            println("Getting second child key and adding to root model")
//            val childtwo = hdKeyFromMnemonic
//              .derive(s"${vaultData.hdDerivePath}/1")
//
//            WalletCircuit.dispatch(AddNewAccount(Account(childtwo.publicKey.toString("hex"), s"Account ${WalletCircuit.zoomTo(_.appRootModel.appModel.data.keyrings.accounts).value.length + 1}")))
//
//            println("Two child account created. Current structure of the accounts in root model")
//
//            println(Json.toJson(WalletCircuit.zoomTo(_.appRootModel.appModel.data.keyrings.accounts).value).toString())
//
//            val selectedAddress = WalletCircuit.zoomTo(_.appRootModel.appModel.data.keyrings.selectedAddress).value
//
//            println(s"Current selected account ${selectedAddress}")
//
//            println("Use this address to sign a transaction")
//
//            println("Get the private key of the selected address")
//
//            println(s"${childtwo.privateKey.toString("hex")}")
//
//            println("Use this public key and private to sign transaction")
//
//            println("get transaction count")
//            EthereumNodeApi.getTransactionCount(s"0x${selectedAddress.dropRight(26)}").map {
//              res =>
//                println(s"Transaction count = ${res}")
//                try {
//                  val myTransaction = new Transaction(res, "21000000000", "315010", "0x3C8035046552c8E2005c1Bee8451874e818ec60E", "0x00")
//                  try {
//                    println(myTransaction.sign(childtwo.privateKey))
//                    println(myTransaction.verifySignature())
//                    println(myTransaction.getUpfrontCost())
//                    println(myTransaction.serialize().toString("hex"))
//
//                    CoreApi.mobileSendSignedTxn(s"0x${myTransaction.serialize().toString("hex")}").map {
//                      res2 =>
//                        println(res2)
//                    }.recover {
//                      case e: Exception => println(e.getMessage())
//                    }
//                  } catch {
//                    case e: Exception => println(e)
//                  }
//                  //                  val tx = new Ethereumjstx(new EthJsTxParams(res, "21000000000", "315010", "0x3C8035046552c8E2005c1Bee8451874e818ec60E", "0x00"))
//                  //                  println(tx)
//                } catch {
//                  case e: Exception => println(e)
//                }
//            }
//        }
//
//    }
//  }
//
//}
