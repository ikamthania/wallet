# Build

## Overview

The entire build is divided into [multiple projects](https://github.com/LivelyGig/wallet/tree/master/project)

The [build.sbt](https://github.com/LivelyGig/wallet/blob/master/build.sbt) file has higher level project declaration.

`WalletClient` is the project which has source for the scalajs-react client.

`Server` is the project which holds sources for the web gateway. It is a layer of communication between the client and the wallet service and in turn Infura and Etherscan.

`WalletApi` is the api declaration for the wallet service endpoint.

`WalletImpl` is the implementation of the service declaration endpoint.

`Shared` the shared models and code which is shared across all projects.

`Documentation` project contains the sources of the documentation.

## Wallet Client

The build setup for this project can be found [here](https://github.com/LivelyGig/wallet/blob/master/project/WalletClient.scala)

This is a typical scala.js project setup.

There are 3 types of dependencies

`libraryDependencies` which are pure scalajs dependencies which are meant to be used only in the scalajs projects.

`ProvidedJs` dependencies for the sources which are directly included in the repository and

`npmDependencies` the npm dependencies which are meant to be downloaded and included in the bundle.

This project uses [scalajs-bundler](https://scalacenter.github.io/scalajs-bundler/) for bundling the modules which in turn uses npm and webpack under the hood.

The build uses `BundlingMode.LibraryOnly()` to get much faster change source and reload workflow. More details can be found [here](https://scalacenter.github.io/scalajs-bundler/reference.html#bundling-mode-library-only).

This mode separates out the bundle into separate bundles for scalajs and the libraries.

These bundles are included in the templates with [sbt-web-scalajs](https://github.com/vmunier/sbt-web-scalajs) plugin. This plugin takes care of copying over the bundles and the source maps.

## WebGateway

The build setup for this project can be found [here](https://github.com/LivelyGig/wallet/blob/master/project/Server.scala)

It has an entry for each scala js project with key `scalaJSProjects := Seq(WalletClient.walletClient)` which is just one in this case. It is a typical play server setup. The Lagom `runAll` command triggers the full opt compilation for the client. To stop that this key `devCommands in scalaJSPipeline += "runAll"` is there to basically tell the `WebScalaJSBundlerPlugin` to consider this command as a dev mode command and trigger the fast opt compilation for the scalajs project.