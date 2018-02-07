import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
import webscalajs.ScalaJSWeb

object WalletClient {
  private[this] val clientSettings = Shared.commonSettings ++ Seq(
    name := "wallet-client",
    libraryDependencies ++= WalletClientDependencies.scalajsDependencies.value,
    jsDependencies ++= WalletClientDependencies.jsDependencies.value,
    jsDependencies ++= WalletClientDependencies.provided.value.map(ProvidedJS / _),

    // yes, we want to package JS dependencies
    skip in packageJSDependencies := false,
    // use Scala.js provided launcher code to start the client app
    scalaJSUseMainModuleInitializer := true,
    scalaJSStage in Global := FastOptStage
    //    scapegoatIgnoredFiles := Seq(".*/JsonUtils.scala", ".*/JsonSerializers.scala")
  )

  lazy val walletClient = (project in file("wallet-client"))
    .settings(clientSettings: _*)
    .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
    .dependsOn(Shared.sharedJs)
}