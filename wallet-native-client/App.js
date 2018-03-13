import React from 'react';
import { StyleSheet, Text, WebView } from 'react-native';
import Config from './config.json';
import QRCodeScanner from './QRCodeScanner';


// see config json and configure it to the running instance of server
var URI = (__DEV__) ? Config.PRODUCTION_URI : Config.PRODUCTION_URI
//"file:///android_asset/walletmain.html"

export default class App extends React.Component {
  render() {
    return (
      <WebView
        source={{uri:URI}}
        onMessage={
        (event)=>
if(event.nativeEvent.data=="camera-roll")
{
<QRCodeScanner/>
}
        }

      />
    );
  }
}




const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  }
});
