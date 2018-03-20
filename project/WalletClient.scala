import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
import webscalajs.ScalaJSWeb

import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._


object WalletClient {
  private[this] val clientSettings = Shared.commonSettings ++ Seq(
    name := "wallet-client",
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= WalletClientDependencies.scalajsDependencies.value,
    jsDependencies ++= WalletClientDependencies.jsDependencies.value,
    jsDependencies ++= WalletClientDependencies.provided.value.map(ProvidedJS / _),
    npmDependencies in Compile ++= WalletClientDependencies.npmDependencies.value,
    useYarn := true,
    webpackBundlingMode := BundlingMode.LibraryOnly(),
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",

    // Add a dependency to the expose-loader (which will expose react to the global namespace)
    npmDevDependencies in Compile ++= Seq(
      "webpack-merge" -> "4.1.0",
      "imports-loader" -> "0.7.0",
      "expose-loader" -> "0.7.1"
    ),

    // Use a custom config file to export the JS dependencies to the global namespace,
    // as expected by the scalajs-react facade
    webpackConfigFile := Some(baseDirectory.value / "webpack.config.js"),

    // yes, we want to package JS dependencies
    skip in packageJSDependencies := false,
    // use Scala.js provided launcher code to start the client app
    scalaJSUseMainModuleInitializer := true,
    scalaJSStage in Global := FastOptStage
  )

  lazy val walletClient = (project in file("wallet-client"))
    .settings(clientSettings: _*)
    .enablePlugins(ScalaJSBundlerPlugin, ScalaJSWeb)
    .dependsOn(Shared.sharedJs)
}