import com.lightbend.lagom.sbt.LagomImport.lagomScaladslApi
import sbt.Keys._
import sbt._

object WalletApi {
  private[this] val dependencies = {
    Seq(
      lagomScaladslApi
    )
  }

  private[this] val contentSettings = Shared.commonSettings ++ Seq(
    name := "walletApi",
    libraryDependencies ++= Seq(lagomScaladslApi)
    //    scapegoatIgnoredFiles := Seq(".*/JsonUtils.scala", ".*/JsonSerializers.scala")
  )


  lazy val walletApi = (project in file("wallet-api"))
    .settings(contentSettings: _*)
    .dependsOn(Shared.sharedJvm)
}