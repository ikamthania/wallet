scalacOptions ++= Seq( "-unchecked", "-deprecation" )

// repository for Typesafe plugins
resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += Resolver.sonatypeRepo("snapshots")


// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.18")

// SBT-Web plugins
addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.1.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.4")

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.2")

// Scala.js
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.20")

//addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.0.6")

addSbtPlugin("ch.epfl.scala" % "sbt-web-scalajs-bundler" % "0.10.0")

// Dependency Resolution
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-M15-7")

// Code Quality
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0") // scalastyle

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0") // dependencyGraph

// dependencyUpdates
// dependencyUpdates: show a list of project dependencies that can be updated,
// dependencyUpdatesReport: writes a list of project dependencies to a file.
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0") // scalariformFormat

// Lagom
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.3.10")
