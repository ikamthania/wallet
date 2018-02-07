import Dependencies.{SharedDependencies, Wallet}
import com.lightbend.lagom.sbt.LagomImport._
import com.lightbend.lagom.sbt.LagomScala
import sbt.Keys._
import sbt._

object WalletImpl {

  private[this] val dependencies = {
    Seq(
      SharedDependencies.scalaTest,
      lagomScaladslTestKit, Wallet.web3j , Wallet.qrgen
    )
  }

  private[this] val walletImplSettings = Shared.commonSettings ++ Seq(
    name := "walletImpl",
    libraryDependencies ++= dependencies,
    resolvers += Resolver.bintrayRepo("ethereum", "maven")
    //    scapegoatIgnoredFiles := Seq(".*/JsonUtils.scala", ".*/JsonSerializers.scala")
  )


  lazy val walletImpl = (project in file("wallet-impl"))
    .settings(walletImplSettings: _*)
    .settings(lagomForkedTestSettings: _*)
    .enablePlugins(LagomScala)
    .dependsOn(WalletApi.walletApi)
}