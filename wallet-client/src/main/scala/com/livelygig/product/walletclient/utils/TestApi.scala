package com.livelygig.product.walletclient.utils

import com.livelygig.product.shared.models.wallet.{ Account, Vault, VaultData }
import com.livelygig.product.walletclient.facades.{ BrowserPassworder, EthereumJsUtils, HDKey, Mnemonic }
import com.livelygig.product.walletclient.handler.{ AddNewAccount, UpdateVault }
import com.livelygig.product.walletclient.services.WalletCircuit
import play.api.libs.json.Json
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import diode.AnyAction._

object TestApi {

  def printWorkflow = {
    val mnemonic = new Mnemonic()

    val strongPassword = "my$trongP@ssword"

    val mnemonicPhrase = mnemonic.toString

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
              .derive(s"${vaultData.hdDerivePath}/0")
            WalletCircuit.dispatch(AddNewAccount(Account(EthereumJsUtils.bufferToHex(childone.publicKey), s"Account ${WalletCircuit.zoomTo(_.appRootModel.appModel.data.keyrings.accounts).value.length + 1}")))

            println("Getting second child key and adding to root model")
            val childtwo = HDKey.fromMasterSeed(mnemonic.toSeed(vaultData.mnemonic))
              .derive(s"${vaultData.hdDerivePath}/1")

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
  }

}
