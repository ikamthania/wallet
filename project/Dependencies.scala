import sbt._

object Dependencies {

  object SharedDependencies {
    val playJsonVersion = "2.6.8"
    val macwireVersion = "2.3.0"
    val derivedCodecsVersion = "4.0.0"
    val scalaTestVersion = "3.0.4"
    val derivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % derivedCodecsVersion
    val macwire = "com.softwaremill.macwire" %% "macros" % macwireVersion
    val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
    val ficus = "com.iheart" %% "ficus" % "1.4.1"
  }


  object PlaySpecific {
    val playWebjars = "org.webjars" %% "webjars-play" % "2.6.3"
  }

  object Wallet {
    val web3j = "org.web3j" % "core" % "2.3.1"
  }


}
