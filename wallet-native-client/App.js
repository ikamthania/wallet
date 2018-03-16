import React from 'react';
import { StyleSheet, Text, WebView,View } from 'react-native';
import Config from './config.json';
import QRCodeScanner from './QRCodeScanner';
import Camera from 'react-native-camera';


// see config json and configure it to the running instance of server
var URI = (__DEV__) ? Config.LOCAL_URI : Config.PRODUCTION_URI
//"file:///android_asset/walletmain.html"

export default class App extends React.Component {

constructor(props) {
    super(props);
    this.myWebView =null;
    this.camera = null;

    this.state = {
      camera: {
            aspect: Camera.constants.Aspect.fill,
            captureTarget: Camera.constants.CaptureTarget.cameraRoll,
            type: Camera.constants.Type.back,
            orientation: Camera.constants.Orientation.auto,
            flashMode: Camera.constants.FlashMode.auto,
            barcodeFinderVisible: true,

          },
    webViewRender:true,
      }
  }

  callBackScanner=(qrCodeData)=>{

  alert("Received data from QRCode --"+qrCodeData)
  }


onNavigationStateChange(webViewState){
  // this.setState({currentUrlState : webViewState.url});

var patt1 = /(0x[0-9A-Za-z]+)/g;
console.warn("URL => " + webViewState.url);

var urlSplit =[];
  var tabId = '';
  var nres = [];

  var ur = webViewState.url;
   var a=ur.search(patt1);

  if(ur.indexOf("#")!= -1){
   urlSplit = ur.split("#/");
  tabId = ur.split("#/").pop();
  nres = tabId.split("/")  ;
}
    if(nres[0]== "captureqrnative"){
     targetUrl = urlSplit[0] + "#/send/" + nres[1] + "/";
    this.setState({ webViewRender:false});
  }
}
  onBarCodeRead(scanResult) {
    alert(scanResult.data);
  }
  render() {
  let renderView=null;


 if(this.state.webViewRender){
                  renderView=    <WebView
                                      source={{uri:URI}}
                                      ref={webview => { this.myWebView = webview; }}
                                      onMessage={this.onMessage.bind(this)}
                                      callbackFromParent={this.callBackScanner.bind(this)}
                                      onNavigationStateChange={this.onNavigationStateChange.bind(this)}

                                    />
                   }else
                    {
                renderView =  <QRCodeScanner onRef={ref => (this.child = ref)}/>
                    }
  return(
  renderView
)
}

  onMessage(event){
          this.setState({webViewRender:false})
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
