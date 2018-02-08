package com.livelygig.product.wallet.impl

import com.lightbend.lagom.scaladsl.client.ConfigurationServiceLocatorComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server._
import com.livelygig.product.content.api.WalletService
import com.livelygig.product.wallet.impl.Utils.{EtherscanUtils, Web3JUtils}
import play.api.libs.ws.ahc.AhcWSComponents
import com.softwaremill.macwire._

abstract class WalletApplication(context: LagomApplicationContext)
    extends LagomApplication(context)
    with AhcWSComponents {
  actorSystem
  override lazy val lagomServer = LagomServer.forService(
    bindService[WalletService].to(wire[WalletServiceImpl])
  )
  lazy val ethereumUtils = wire[Web3JUtils]
  lazy val etherscan = wire[EtherscanUtils]
  //wire[KeeperSubscriberForWallet]
}

class WalletApplicationLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext) =
    new WalletApplication(context) with ConfigurationServiceLocatorComponents

  override def loadDevMode(context: LagomApplicationContext) =
    new WalletApplication(context) with LagomDevModeComponents

  override def describeService =
    Some(readDescriptor[WalletService])

}