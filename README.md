## Watchman

We have currently two clients. One is a web client which is based on `scalajs-react` library this project is currently in the walletClient directory  and another is a `react-native` application which is currently in wallet-native-client directory. These two are connected with each other with a simple web view component similar to this

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
