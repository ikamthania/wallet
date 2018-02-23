
lazy val sharedJs = Shared.sharedJs

lazy val walletClient = WalletClient.walletClient

lazy val sharedJvm = Shared.sharedJvm

lazy val webGateway = Server.webGateway

lazy val walletApi = WalletApi.walletApi

lazy val walletImpl = WalletImpl.walletImpl

lagomKafkaEnabled in ThisBuild := false
lagomCassandraEnabled in ThisBuild := false

// loads the Play server project at sbt startup


onLoad in Global ~= (_ andThen ("project webGateway" :: _))
