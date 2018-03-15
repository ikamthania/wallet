import React from 'react';
import { StyleSheet, Text, WebView } from 'react-native';
import Config from './config.json';
import QRCodeScanner from './QRCodeScanner';


// see config json and configure it to the running instance of server
var URI = (__DEV__) ? Config.PRODUCTION_URI : Config.PRODUCTION_URI
//"file:///android_asset/walletmain.html"

export default class App extends React.Component {

constructor(props) {
    super(props);
    this.myWebView =null;

    this.state = {
    webViewRender:true
      }
  }

  render() {
  let renderView = null;
  if(this.webViewRender==true){
   renderView=  <WebView
                     source={{uri:URI}}
                     ref={webview => { this.myWebView = webview; }}
                     onMessage={this.onMessage.bind(this)}
                   />
  }else {renderView=<QRCodeScanner/>}
return(
      renderView
)
}

  onMessage(event){
    alert("In react-native component msg received--> "+event.nativeEvent.data)
   this.myWebView.postMessage("msg from react-native component");
   this.setState({webViewRender:false})

    }
  setState(scanTxt){
      this.setState({webViewRender:true})

    alert("qrcode msg received--> "+scanTxt)
   this.myWebView.postMessage(scanTxt);


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
