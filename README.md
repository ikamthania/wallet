# Ubunda Wallet Application

This is the main repository where the code for the ubunda wallet application lives.

## Overview

This wallet application. One is a web client which is based on `scalajs-react` library this project is currently in the walletClient directory  and another is a `react-native` application which is currently in wallet-native-client directory. These two are connected with each other with a simple web view component similar to this

```
<WebView
    source={{uri: 'http://10.0.2.2:9000/wallet/'}}
    style={{marginTop: 20}}
/>
```
Where the uri is the url of the running web gateway. It runs on `9000` port usually but at times the error in wallet service can result in web gateway to not load properly resulting in connection refused error. In that case look at terminal for output similar to

```
[info] Service WebGateway listening for HTTP on 0:0:0:0:0:0:0:0:63376
```
and use that port instead.

## Dev environment setup

### Prerequisites

The application uses two build chain. The scala application tool uses [sbt](https://www.scala-sbt.org/) and the react native application uses [npm](https://www.npmjs.com/). [Yarn](https://yarnpkg.com/en/) is also recommended.
For enabling hot reloading across two applications [Watchman](https://facebook.github.io/watchman/) is used. To setup [this blogpost](https://saintcoder.wordpress.com/2017/03/23/how-to-install-facebooks-watchman-on-linux-ubuntu-16-04-lts/) is helpful.
Android sdk and emulators are required. The native app is dependent on [expo toolchain](https://expo.io/). So if you like to run the app on your device while you develop install [expo](https://play.google.com/store/apps/details?id=host.exp.exponent&hl=en) on your device.

### Note

There are some system specific requirement for watchman. [This section](https://facebook.github.io/watchman/docs/install.html#system-specific-preparation) explains it.

## Server

The backend is based on [Lagom framework](https://www.lagomframework.com/documentation/1.4.x/scala/Home.html)

It currently has just one microservice that is wallet service. More services will be added in future.

## Wallet Client

This is a [scalaj-react](https://github.com/japgolly/scalajs-react) application. Which uses [Diode](https://github.com/suzaku-io/diode) for immutable model structure for the application state.
A very good resource to understand how these interact with each other and in general scalajs setup [scalajs-spa-tutorial](https://ochrons.github.io/scalajs-spa-tutorial/en/) is a recommended read.

## Wallet native client

This react native application is based on the expo platform. Head over to the wallet-native-client directory for comprehensive [readme](/wallet-native-client/README.md) of the setup.

## Hot Reloading

Both project has hot reloading enabled, that is if you make changes in wallet-client scala files or any scala file `Lagom` framework is going to take care of triggering the recompilation. Same is the case for the wallet-native-client which has hot reloading based on `watchman`. However there is a disconnect between system, where javascript automatic bundling cannot be done if scala sources change. To solve this issue we can do a workaround. Simply attach a watchman to watch over changes in scala files and trigger hot reloading in react native by adding garbage comment in a file.

Watchman command for unix like systems.

`watchman-make --run 'echo "//" > wallet-native-client/trigger.js' -p '**/*.scala'`

You should have output similar to

```
# Relative to /home/shubham/project/livelygig/wallet
# Changes to files matching **/*.scala will execute `echo "//" > wallet-native-client/trigger.js`
# waiting for changes
```

Hit `CTRL + D` to get out of watchman shell.

You would need to explicitly delete the watchman watch with this command

`watchman watch-del ./`
