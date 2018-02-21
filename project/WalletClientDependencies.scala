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
              ))

  val npmDependencies = Def.setting(
    Seq(
      "react"-> "^16.2.0",
      "react-dom"-> "^16.2.0",
      "blockies"-> "^0.0.2",
      "toastr"-> "^2.1.4"
    )
  )

  val provided = Def.setting(
    Seq(
     /* "pseudoloc.js",
      "qrcode.js",
      "signtxn.js"*/
  ))
}
