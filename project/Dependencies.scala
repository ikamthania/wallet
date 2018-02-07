import sbt._

object Dependencies {

  object Cache {
    val ehCache = "net.sf.ehcache" % "ehcache-core" % "2.6.11"
  }

  object Logging {
    val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.21"
  }

  object Play {
    private[this] val version = "2.5.18"
    val playLib = "com.typesafe.play" %% "play" % version
    val playFilters = play.sbt.PlayImport.filters
    val playWs = play.sbt.PlayImport.ws
    val playTest = "com.typesafe.play" %% "play-test" % version % "test"
    val playMailer = "com.typesafe.play" %% "play-mailer" % "5.0.0"
    val playBootstrap = "com.adrianhurt" % "play-bootstrap_2.11" % "1.1-P25-B3"
    val playWebjars = "org.webjars" %% "webjars-play" % "2.6.2"
    val scalajsScripts = "com.vmunier" %% "scalajs-scripts" % "1.0.0"
  }

  object Akka {
    private[this] val version = "2.5.3"
    val actor = "com.typesafe.akka" %% "akka-actor" % version
    val remote = "com.typesafe.akka" %% "akka-remote" % version
    val logging = "com.typesafe.akka" %% "akka-slf4j" % version
    val cluster = "com.typesafe.akka" %% "akka-cluster" % version
    val clusterMetrics = "com.typesafe.akka" %% "akka-cluster-metrics" % version
    val clusterTools = "com.typesafe.akka" %% "akka-cluster-tools" % version
    val testkit = "com.typesafe.akka" %% "akka-testkit" % version % "test"
  }

  object Authentication {
    private[this] val version = "4.0.0"
    val silhouette = "com.mohiva" %% "play-silhouette" % version
    val hasher = "com.mohiva" %% "play-silhouette-password-bcrypt" % version
    val persistence = "com.mohiva" %% "play-silhouette-persistence" % version
    val crypto = "com.mohiva" %% "play-silhouette-crypto-jca" % version
  }

  object SharedDependencies {
    val playJsonVersion = "2.6.6"
    val macwireVersion = "2.3.0"
    val derivedCodecsVersion = "3.3"
    val shapelessVersion = "2.3.2"
    val scalaTestVersion = "3.0.4"
    val enumeratumVersion = "1.5.11"
    val playEnumeratum = "com.beachape" %% "enumeratum-play-json" % enumeratumVersion
    val derivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % derivedCodecsVersion
    val shapeless = "com.chuusai" %% "shapeless" % shapelessVersion
    val macwire = "com.softwaremill.macwire" %% "macros" % macwireVersion
    val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
    val ficus = "com.iheart" %% "ficus" % "1.4.1"
  }

  object WebJars {
    val fontAwesome = "org.webjars" % "font-awesome" % "4.7.0"
    val validator = "org.webjars.npm" % "github-com-1000hz-bootstrap-validator" % "0.11.5"
    val selectize = "org.webjars" % "selectize.js" % "0.12.4"
    val datepicker = "org.webjars" % "bootstrap-datepicker" % "1.7.1"
    val toastr = "org.webjars" % "toastr" % "2.1.2"
    val blockies = "org.webjars.bower" % "github-com-MyEtherWallet-blockies" % "0.1.0"

  }

  object Metrics {
    val metrics = "nl.grons" %% "metrics-scala" % "3.5.5"
    val jvm = "io.dropwizard.metrics" % "metrics-jvm" % "3.1.2"
    val ehcache = "io.dropwizard.metrics" % "metrics-ehcache" % "3.1.2" intransitive ()
    val healthChecks = "io.dropwizard.metrics" % "metrics-healthchecks" % "3.1.2" intransitive ()
    val json = "io.dropwizard.metrics" % "metrics-json" % "3.1.2"
    val jettyServlet = "org.eclipse.jetty" % "jetty-servlet" % "9.3.11.v20160721"
    val servlets = "io.dropwizard.metrics" % "metrics-servlets" % "3.1.2" intransitive ()
    val graphite = "io.dropwizard.metrics" % "metrics-graphite" % "3.1.2" intransitive ()


  }

  object Utils {
    val scapegoatVersion = "1.3.0"
    val commonsIo = "commons-io" % "commons-io" % "2.6"
    val crypto = "xyz.wiedenhoeft" %% "scalacrypt" % "0.4.0"
    val scrImage = "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.8"
  }

  object Testing {
    val gatlingCore = "io.gatling" % "gatling-test-framework" % "2.1.7" % "test"
    val gatlingCharts = "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.1.7" % "test"
  }

  object Wallet {
    val ethereumj = "org.ethereum" % "ethereumj-core" % "1.5.0-RELEASE"
    val web3j = "org.web3j" % "core" % "2.3.1"
    val qrgen = "net.glxn" % "qrgen" % "1.4"
    val webcam  = "com.github.sarxos" % "webcam-capture" % "0.3.11"
  }


}
