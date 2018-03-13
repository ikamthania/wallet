import React from 'react';
import Camera from 'react-native-camera';
import { StyleSheet, Text } from 'react-native';


class QRCodeScanner extends React.Component {

constructor(props) {
    super(props);
    this.camera = null;
    this.state = {
      camera: {
        aspect: Camera.constants.Aspect.fill,
        captureTarget: Camera.constants.CaptureTarget.cameraRoll,
        type: Camera.constants.Type.back,
        orientation: Camera.constants.Orientation.auto,
        flashMode: Camera.constants.FlashMode.auto,
        barcodeFinderVisible: true
      }
    };
  }
  render() {
    return (
      <View  style={styles.containerHeight}>
                  <Camera
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
                   barcodeFinderVisible={this.state.camera.barcodeFinderVisible}
                   barcodeFinderWidth={220}
                   barcodeFinderHeight={200}
                   barcodeFinderBorderColor="red"
                   barcodeFinderBorderWidth={2}
                   onBarCodeRead={this.onBarCodeRead.bind(this)}
                 />
                 <View style={[styles.overlay, styles.topOverlay]}>
                   <Text style={styles.scanScreenMessage}>"Please scan the barcode."</Text>
                 </View>
               </View>
    );
  }

   takePicture() {
      const options = {};

      this.camera.capture({metadata: options})
        .then((data) => console.log(data))
        .catch(err => console.error(err));
    }

    defaultStyles() {
      return {
        container: {
          flex: 1
        },
        preview: {
          flex: 1,
          justifyContent: 'flex-end',
          alignItems: 'center',
          height : 1000,
          backgroundColor: 'black',
        },
        containerHeight : {
             height : 700
        },
        overlay: {
          position: 'absolute',
          padding: 16,
          right: 0,
          left: 0,
          alignItems: 'center'
        },
        topOverlay: {
          top: 0,
          flex: 1,
          flexDirection: 'row',
          justifyContent: 'space-between',
          alignItems: 'center'
        },
        bottomOverlay: {
          bottom: 0,
          backgroundColor: 'rgba(0,0,0,3.195)',
          flexDirection: 'row',
          justifyContent: 'center',
          alignItems: 'center'
        },
        enterBarcodeManualButton: {
          padding: 15,
          backgroundColor: 'white',
          borderRadius: 40
        },
        scanScreenMessage: {
          fontSize: 14,
          color: 'white',
          textAlign: 'center',
          alignItems: 'center',
          justifyContent: 'center'
        }
      };
    }

}


const styless = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5FCFF',
  },

   preview: {
    flex: 1,
    justifyContent: 'flex-end',
    alignItems: 'center'
  },
  capture: {
    flex: 0,
    backgroundColor: '#fff',
    borderRadius: 5,
    color: '#000',
    padding: 10,
    margin: 40
  }
});

