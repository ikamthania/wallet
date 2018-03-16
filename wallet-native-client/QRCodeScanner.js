import React from 'react';
import { Image, StatusBar, StyleSheet, TouchableOpacity, View,WebView,Text } from 'react-native';
import Camera from 'react-native-camera';
import Config from './config.json';


var URI = (__DEV__) ? Config.LOCAL_URI : Config.PRODUCTION_URI

export default class QRCodeScanner extends React.Component {


  constructor(props) {
    super(props);
this.myWebView=null;
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
      scannerView:true
    };
  }

  takePicture = () => {
    if (this.camera) {
      this.camera
        .capture()
        .then(data => console.log(data))
        .catch(err => console.error(err));
    }
  };

  onBarCodeRead(scanResult) {
    alert(scanResult.data);
 this.setState({scannerView:false});
 //todo post message after webview loaded .Add time delay for same.
this.myWebView.postMessage(scanResult.data)
  }

render() {
let renderView=null;


 if(this.state.scannerView){
                  renderView=   <Camera
                                                                        ref={cam => {
                                                                          this.camera = cam;
                                                                        }}
                                                                        style={styles.preview}
                                                                        aspect={this.state.camera.aspect}
                                                                        captureTarget={this.state.camera.captureTarget}
                                                                        type={this.state.camera.type}
                                                                        flashMode={this.state.camera.flashMode}
                                                                        onFocusChanged={() => {}}
                                                                        onZoomChanged={() => {}}
                                                                        defaultTouchToFocus
                                                                        mirrorImage={false}
                                                                        cropToPreview={false}
                                                                        permissionDialogTitle="Sample title"
                                                                        permissionDialogMessage="Sample dialog message"
                                                                        barcodeFinderVisible={this.state.camera.barcodeFinderVisible}
                                                                        barcodeFinderWidth={220}
                                                                        barcodeFinderHeight={200}
                                                                        barcodeFinderBorderColor="red"
                                                                        barcodeFinderBorderWidth={2}
                                                                        onBarCodeRead={this.onBarCodeRead.bind(this)}

                                                                      />
                   }else
                    {
                renderView =    <WebView
                                                                              source={{uri:URI+"#/send"}}
                                                                              ref={webview => { this.myWebView = webview; }}

                                                                            />
                    }
  return(
  renderView
)



  }
 }


const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  preview: {
    flex: 1,
    justifyContent: 'flex-end',
    alignItems: 'center'
  },
  overlay: {
    position: 'absolute',
    padding: 16,
    right: 0,
    left: 0,
    alignItems: 'center',
  },
  topOverlay: {
    top: 0,
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  bottomOverlay: {
    bottom: 0,
    backgroundColor: 'rgba(0,0,0,0.4)',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
  },
  captureButton: {
    padding: 15,
    backgroundColor: 'white',
    borderRadius: 40,
  },
  typeButton: {
    padding: 5,
  },
  flashButton: {
    padding: 5,
  },
  buttonsSpace: {
    width: 10,
  },
});
