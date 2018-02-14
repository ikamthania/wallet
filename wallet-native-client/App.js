import React from 'react';
import { StyleSheet, Text, WebView } from 'react-native';
import Config from './config.json';

// see config json and configure it to the running instance of server
var URI = (__DEV__) ? Config.URI : "https://www.ubunda.com"

export default class App extends React.Component {
  render() {
    return (
      <WebView
        source={{uri: URI}}
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
