package com.livelygig.product.wallet.impl

import com.livelygig.product.shared.models.wallet.{ERC20ComplientToken, EtherTransaction, TransactionWithSymbol}
import com.livelygig.product.wallet.api.models.ValidateWalletFile
import com.livelygig.product.wallet.impl.Utils.{EtherscanUtils, Web3JUtils}
import com.softwaremill.macwire._
import org.scalatest.{BeforeAndAfterAll, Matchers, OptionValues, WordSpec}
import org.web3j.crypto.Credentials
import org.web3j.protocol.core.methods.response.Web3ClientVersion
import play.api.{Configuration, Environment}

import scala.concurrent.Future
/**
  * Created by shubham on 6/4/17.
  */
class Web3JUtilsSpec extends WordSpec with Matchers with BeforeAndAfterAll with OptionValues {
  lazy val env = Environment.simple()
  lazy val config = Configuration.load(env)
  lazy val web3jUtils = wire[Web3JUtils]
  lazy val etherscanUtils=wire[EtherscanUtils]
  val keystore="{'version':3,'id':'ae036d1d-5d12-44b7-9e33-ebc0f7c5ffc3','address':'50c14e127b7dc237de42edf1ed6796b06893efcc','Crypto':{'ciphertext':'1156ea0a18fc72876a21af4d85235c2c52a9f2be1e4e1fff4d5a2d7fa6a7ec6f','cipherparams':{'iv':'b244c61626c6c57b41e162669c1efb78'},'cipher':'aes-128-ctr','kdf':'scrypt','kdfparams':{'dklen':32,'salt':'5ebd0283aa87622edff4181c1c4d354103aacac2e5d3eaa76c258b7c70b7ae81','n':1024,'r':8,'p':1},'mac':'45a4e174f033c7339ad13d89aad0d5a9682e87f96561d729432d32e60806150e'}}"
  val keyStore="{\"version\":3,\"id\":\"ae036d1d-5d12-44b7-9e33-ebc0f7c5ffc3\",\"address\":\"50c14e127b7dc237de42edf1ed6796b06893efcc\",\"Crypto\":{\"ciphertext\":\"1156ea0a18fc72876a21af4d85235c2c52a9f2be1e4e1fff4d5a2d7fa6a7ec6f\",\"cipherparams\":{\"iv\":\"b244c61626c6c57b41e162669c1efb78\"},\"cipher\":\"aes-128-ctr\",\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"salt\":\"5ebd0283aa87622edff4181c1c4d354103aacac2e5d3eaa76c258b7c70b7ae81\",\"n\":1024,\"r\":8,\"p\":1},\"mac\":\"45a4e174f033c7339ad13d89aad0d5a9682e87f96561d729432d32e60806150e\"}}"
  "The ethereum utils" should {
   /* "createNewWallet" in {
      val a=web3jUtils.createNewWallet("123456")
      println(a)
    }*/


//    "getWeb3ClientVersion" should {
//      "give the client version" in {
//        val version =
//          web3jUtils
//            .getWeb3ClientVersion()
//        version shouldBe an[Web3ClientVersion]
//      }
//    }
    "getBalance" should {
      "give the balance of an account" in {
        println(web3jUtils
          .getBalance("0x19aE9E3DF4F7161527c2AdfDE06b66312CD884C3")) //shouldBe an[BigInteger]

      }
    }


    "getTransactionList" should {
      "gives all transactions of an account" in {
        etherscanUtils.getTransactionList("0x83990Ef93FE61166d2DDBe3C2Dc320d766E116AE",Seq(ERC20ComplientToken("0xec5a3314ac5e3ac7fd696d66133aac3a8e6b71ba","bth","Bethereum",5,"0"))) shouldBe an[Future[Seq[TransactionWithSymbol]]]


      }
    }
/*

    "getTokenName" should {
      "gives contract token name" in {
       web3jUtils.getTokenName("0xd060e8492cc5b27d3d5e0c878499cc6b2238fb86","0x50c14E127B7dc237dE42EDf1ed6796B06893eFCc") shouldBe an[String]
      }
    }

    "getTokenSymbol" should {
      "gives contract token total supply" in {
        web3jUtils.getTokenSymbol("0xd060e8492cc5b27d3d5e0c878499cc6b2238fb86","0x50c14E127B7dc237dE42EDf1ed6796B06893eFCc") shouldBe an[String]
      }
    }

    "getTokenTotalSupply" should {
      "gives contract token total supply" in {
         web3jUtils.getTokenTotalSupply("0xd060e8492cc5b27d3d5e0c878499cc6b2238fb86","0x50c14E127B7dc237dE42EDf1ed6796B06893eFCc") shouldBe an[String]
            }
    }

    "getTokenVersion" should {
      "gives contract token total supply" in {
        web3jUtils.getTokenVersion("0xd060e8492cc5b27d3d5e0c878499cc6b2238fb86","0x50c14E127B7dc237dE42EDf1ed6796B06893eFCc") shouldBe an[String]
             }
    }
*/

    "getTokenBalance" should {
      "gives contract token balance" in {
        web3jUtils.getTokenBalance(Seq(ERC20ComplientToken("0xec5a3314ac5e3ac7fd696d66133aac3a8e6b71ba","bth","Bethereum",5,"0")),"0x50c14E127B7dc237dE42EDf1ed6796B06893eFCc") shouldBe an[Future[Seq[ERC20ComplientToken]]]
              }
    }
     "getNetwork" should {
    "gives current network information" in {
      web3jUtils.getNetworkInfo() shouldBe an[String]
    }
  }
     "getTxnHash" should {
    "gives getTxnHash" in {
      println(web3jUtils.sendSignedTransaction("0xf86c808504e3b292008304ce829414c4983b77f37961386ccb9814c498ac5a35387c871999999999999a0029a0914d6ee74606a5f5be782e6e04ac31ca2582579b10d3d1441384310c9bce87e2a05db3ebf5d871570afbb3e8bc9b7cdf07e5df744326e64d9dd425cd56d78e514a")) /*shouldBe an[String]*/
    }
  }
//     "getTdsfxnHash" should {
//    "gives geasdtTxnHash" in {
//      println(web3jUtils.getNonce("0xec5a3314ac5e3ac7fd696d66133aac3a8e6b71ba",EtherTransaction("123456789","0xec5a3314ac5e3ac7fd696d66133aac3a8e6b71ba","1","eth",18) )) /*shouldBe an[String]*/
//    }
//  }
    "getTransactionToken" should {
      "gives token balance from event logs of perticular bloch height" in {
        etherscanUtils.tokenTransactionBalance(ERC20ComplientToken("0xec5a3314ac5e3ac7fd696d66133aac3a8e6b71ba","bth","Bethereum",5,"0"),"2088504") shouldBe an[BigDecimal]
      }
    }

  }


}
