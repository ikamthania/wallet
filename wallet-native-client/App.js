import React from 'react';
import { StyleSheet, Text, WebView } from 'react-native';

export default class App extends React.Component {
  render() {
    return (
      <WebView
        source={{uri: 'http://10.0.2.2:63376/wallet/'}}
        style={{marginTop: 20}}
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
  },
});
