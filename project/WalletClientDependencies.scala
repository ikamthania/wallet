import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt._

/**
 * Application settings. Configure the build for your application here.
 * You normally don't have to touch the actual build definition after this.
 */
object WalletClientDependencies {

  /** Dependencies only used by the JS project (note the use of %%% instead of %%) */
  val scalajsDependencies = Def.setting(
    Seq(
      "com.github.japgolly.scalajs-react" %%% "core" % "1.1.0",
      "com.github.japgolly.scalajs-react" %%% "extra" % "1.1.0",
      "org.scala-js" %%% "scalajs-dom" % "0.9.3",
      "io.suzaku" %%% "diode" % "1.1.2",
      "io.suzaku" %%% "diode-react" % "1.1.2",
      "org.querki" %%% "jquery-facade" % "1.2",
      "org.querki" %%% "querki-jsext" % "0.8",
      "ru.pavkin" %%% "scala-js-momentjs" % "0.8.1"))

  /** Dependencies for external JS libs that are bundled into a single .js file according to dependency order */
  val jsDependencies = Def.setting(
    Seq(
      "org.webjars.bower" % "react" % "15.6.1" / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
      "org.webjars.bower" % "react" % "15.6.1" / "react-dom.js" minified "react-dom.min.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM",
      "org.webjars" % "jquery" % "3.2.1" / "jquery.min.js",
      "org.webjars" % "bootstrap" % "3.3.7" / "bootstrap.min.js" dependsOn "jquery.min.js",
      "org.webjars" % "toastr" % "2.1.2" / "2.1.2/toastr.js" dependsOn "jquery.min.js",
      "org.webjars.bower" % "github-com-MyEtherWallet-blockies" % "0.1.0" / "blockies.js",
      "org.webjars.npm"% "github-com-BoxFactura-pulltorefresh-js"%"0.1.11" / "pulltorefresh.min.js"
    ))

  val provided = Def.setting(
    Seq( //    "ethjs.min.js"
    ))
}
