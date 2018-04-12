import Dependencies.SharedDependencies
//import com.sksamuel.scapegoat.sbt.ScapegoatSbtPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
import webscalajs.ScalaJSWeb

object Shared {
  val projectId = "ubundawallet"
  val projectName = "UbundaWallet"

  lazy val commonSettings = Seq(
    version := Shared.Versions.app,
    scalaVersion := Shared.Versions.scala,
    organization := "Livelygig",
    scalacOptions ++= Seq(
      "-encoding", "UTF-8", "-feature", "-deprecation", "-unchecked", "–Xcheck-null" /*, "-Xfatal-warnings"*/ , "-Xlint", "-Ywarn-unused:locals,privates",
      "-Ywarn-adapted-args", "-Ywarn-dead-code", "-Ywarn-inaccessible", "-Ywarn-nullary-override", "-Ywarn-numeric-widen", "-language:higherKinds",
      "-language:implicitConversions"
    ),
    scalacOptions in Test ++= Seq("-Yrangepos"),

    publishMavenStyle := false,

    // Prevent Scaladoc
    publishArtifact in(Compile, packageDoc) := false,
    publishArtifact in packageDoc := false,
    sources in(Compile, doc) := Seq.empty

  )

  object Versions {
    val app = "0.3"
    val scala = "2.12.4"
  }

  def withProjects(p: Project, includes: Seq[Project]) = includes.foldLeft(p) { (proj, inc) =>
    proj.aggregate(inc).dependsOn(inc)
  }

  lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).settings(commonSettings: _*).settings(
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % SharedDependencies.scalaTestVersion % "test",
      "org.julienrf" %%% "play-json-derived-codecs" % SharedDependencies.derivedCodecsVersion
    )
  )
    .jvmSettings(
      libraryDependencies ++= Seq(
        SharedDependencies.macwire,
        SharedDependencies.ficus
      )
    )
    .jsSettings(
      libraryDependencies ++= Seq(
        "com.typesafe.play" %%% "play-json" % SharedDependencies.playJsonVersion,
        "ru.pavkin" %%% "scala-js-momentjs" % "0.9.1"
      )
    )
    .jsConfigure(_ enablePlugins ScalaJSWeb)


  lazy val sharedJs = shared.js
  lazy val sharedJvm = shared.jvm

}
