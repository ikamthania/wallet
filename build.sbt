
lazy val sharedJs = Shared.sharedJs

lazy val walletClient = WalletClient.walletClient

lazy val sharedJvm = Shared.sharedJvm

lazy val webGateway = Server.webGateway

lazy val walletApi = WalletApi.walletApi

lazy val walletImpl = WalletImpl.walletImpl

lazy val doc = Documentation.doc

lagomKafkaEnabled in ThisBuild := false
lagomCassandraEnabled in ThisBuild := false
lagomServiceGatewayPort in ThisBuild := 9001

// loads the Play server project at sbt startup
onLoad in Global ~= (_ andThen ("project webGateway" :: _))
