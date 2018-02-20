import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
import webscalajs.ScalaJSWeb
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._

import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin


object WalletClient {
  private[this] val clientSettings = Shared.commonSettings ++ Seq(
    name := "wallet-client",
    libraryDependencies ++= WalletClientDependencies.scalajsDependencies.value,
    jsDependencies ++= WalletClientDependencies.jsDependencies.value,
    jsDependencies ++= WalletClientDependencies.provided.value.map(ProvidedJS / _),
    npmDependencies in Compile ++= WalletClientDependencies.npmDependencies.value,
    useYarn := true,
    webpackBundlingMode := BundlingMode.LibraryOnly(),
    // Add a dependency to the expose-loader (which will expose react to the global namespace)
    npmDevDependencies in Compile += "expose-loader" -> "0.7.1",

    // Use a custom config file to export the JS dependencies to the global namespace,
    // as expected by the scalajs-react facade
    webpackConfigFile := Some(baseDirectory.value / "webpack.config.js"),

    // yes, we want to package JS dependencies
    skip in packageJSDependencies := false,
    // use Scala.js provided launcher code to start the client app
    scalaJSUseMainModuleInitializer := true,
    scalaJSStage in Global := FastOptStage
    //    scapegoatIgnoredFiles := Seq(".*/JsonUtils.scala", ".*/JsonSerializers.scala")
  )

  lazy val walletClient = (project in file("wallet-client"))
    .settings(clientSettings: _*)
    .enablePlugins(ScalaJSBundlerPlugin, ScalaJSWeb)
    .dependsOn(Shared.sharedJs)
}