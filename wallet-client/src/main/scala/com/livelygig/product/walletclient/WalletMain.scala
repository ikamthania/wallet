package com.livelygig.product.walletclient

import com.livelygig.product.shared.models.wallet.{ Account, Vault, VaultData }
import com.livelygig.product.walletclient.facades._
import com.livelygig.product.walletclient.handler.{ AddNewAccount, UpdateVault }
import com.livelygig.product.walletclient.router.ApplicationRouter
import com.livelygig.product.walletclient.services.{ LocalStorageApi, WalletCircuit }
import com.livelygig.product.walletclient.utils.Bundles
import org.scalajs.dom
import play.api.libs.json.{ JsError, Json }
import diode.AnyAction._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object WalletMain extends Bundles {

  def main(args: Array[String]): Unit = {

    val mnemonic = new Mnemonic()

    val strongPassword = "my$trongP@ssword"

    val mnemonicPhrase = "raw congress foam fold true trick fold pen fox ozone cricket cotton"

    println("Application started, creating initial root model and subscribing to changes")
    LocalStorageApi.subscribeToAppRootChanges()

    println(s"Root model created without vault ${Json.toJson(WalletCircuit.zoomTo(_.appRootModel.appModel).value)})")

    println(s"Creating master private extended key with mnemonic phrase ${mnemonicPhrase}")

    println(s"Private Extended Key created ${HDKey.fromMasterSeed(mnemonic.toSeed(mnemonicPhrase)).publicExtendedKey}")

    println(s"Encrypting the mnemonic phrase to vault with password ${strongPassword}")

    BrowserPassworder.encrypt(strongPassword, Json.toJson(VaultData(mnemonicPhrase)).toString()).toFuture.map {
      res =>
        println("Vault encrypted. Parsing it to data structure")
        val vault = Json.parse(res).validate[Vault].get
        println(s"Vault data ${Json.toJson(vault)}")
        println("Adding vault to root model, local storage should update automatically")
        WalletCircuit.dispatch(UpdateVault(Some(vault)))
        println("Checking decryption of the vault")
        BrowserPassworder.decrypt(strongPassword, Json.toJson(vault).toString()).toFuture.map {
          decrypt =>
            println(s"Vault decrypted, here is the mnemonic phrase from vault : ${decrypt.toString}")

            val vaultData = Json.parse(decrypt.toString).validate[VaultData].get

            println("Getting first child key and adding to root model")
            val childone = HDKey.fromMasterSeed(mnemonic.toSeed(vaultData.mnemonic))
              .derive(s"m/44'/60'/0'/0/0")
            WalletCircuit.dispatch(AddNewAccount(Account(EthereumJsUtils.bufferToHex(childone.publicKey), s"Account ${WalletCircuit.zoomTo(_.appRootModel.appModel.data.keyrings.accounts).value.length + 1}")))

            println("Getting second child key and adding to root model")
            val childtwo = HDKey.fromMasterSeed(mnemonic.toSeed(vaultData.mnemonic))
              .derive(s"m/44'/60'/0'/0/1")

            WalletCircuit.dispatch(AddNewAccount(Account(EthereumJsUtils.bufferToHex(childtwo.publicKey), s"Account ${WalletCircuit.zoomTo(_.appRootModel.appModel.data.keyrings.accounts).value.length + 1}")))

            println("Two child account created. Current structure of the accounts in root model")

            println(Json.toJson(WalletCircuit.zoomTo(_.appRootModel.appModel.data.keyrings.accounts).value).toString())

            val selectedAddress = WalletCircuit.zoomTo(_.appRootModel.appModel.data.keyrings.selectedAddress).value

            println(s"Current selected account ${selectedAddress}")

            println("Use this address to sign a transaction")

            println("Get the private key of the selected address")

            println(s"${EthereumJsUtils.bufferToHex(childtwo.privateKey)}")

            println("Use this public key and private to sign transaction")

        }

    }
    // encrypt mnemonic phase in vault

    // decrypt vault

    // create account one, store it in local storage and get private key

    // create account two, store it in local storage and get private key

    // select account one

    // based on selected account send a transaction by deriving the private key

    // send transaction

    //    println(EthereumJsUtils.bufferToHex(HDKey.fromMasterSeed(mnemonic.toSeed())
    //      .derive(s"m/44'/60'/0'/0").publicKey))
    println(EthereumJsUtils.bufferToHex(HDKey.fromMasterSeed(mnemonic.toSeed("potato actual develop attend client fluid gas prevent artefact thrive broom craft"))
      .derive(s"m/44'/60'/0'/1").publicKey))

    /*println(EthereumJSWallet.fromExtendedPrivateKey("xprv9z9XqHQcsDV3Bx5ynoHu6sAMAQUsFmdQeU9izP8vXymqvow3P8EGZqerWit6d54uZ7P5tebpPJvvEeioYtUfU7Muaw6HWvRFhpMdDwuWKAx").getPrivateKeyString())
    println(EthereumJSWallet.fromExtendedPrivateKey("xprv9z9XqHQcsDV3Bx5ynoHu6sAMAQUsFmdQeU9izP8vXymqvow3P8EGZqerWit6d54uZ7P5tebpPJvvEeioYtUfU7Muaw6HWvRFhpMdDwuWKAx").getPrivateKeyString())

    BrowserPassworder.decrypt("shayank1s990", "{\"data\":\"rIu6cldOqpf0Q8SGaqA/2dXJCAeDlWB9BzS7xMAiLriKTQ/bcioPluPIVFQJMHQ9gaFPKX76NIE8A4Oh68NjCEDPYfnrRCkBOkCKE75DhUQYZ2yGrxfWA4SyqoAt0UlWeFQhKH+Groqi+2DxdeTVS43PDoJs8k7k8Tr93JJYrX1dse3vLJkhKyp1IiKSVLg3eKZwM1C3miDuBZFp/Knhumabs+YHlQUCW/wB7JH9o4vc\",\"iv\":\"Sw82SgmWpcpXq2nxNnnGrQ==\",\"salt\":\"+/4rbZxXHoeV2p+4h3l1tHOORePTNPylCYNGMVca0A8=\"}").toFuture.map { e =>
      println(e.toString)
    }.recover {
      case err: JsError => println(err)
      case e => println(e)
    }*/

    ApplicationRouter.router().renderIntoDOM(dom.document.getElementById("root"))
  }

}