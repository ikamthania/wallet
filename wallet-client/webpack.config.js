var merge = require('webpack-merge');

// Load the config generated by scalajs-bundler
var config = require('./scalajs.webpack.config');

var commonConfig = require('./common.webpack.config');


// Exported modules (here, React and ReactDOM)
var globalModules = {
  "react": "React",
  "react-dom": "ReactDOM",
  "ethereumjs-abi": "EthereumjsABI",
  "ethereumjs-units": "EthereumjsUnits",
  "toastr" : "Toastr",
  "blockies" : "Blockies",
};

Object.keys(config.entry).forEach(function(key) {
  // Prepend each entry with the globally exposed JS dependencies
  config.entry[key] = Object.keys(globalModules).concat(config.entry[key]);
});

// Globally expose the JS dependencies
config.module.rules = Object.keys(globalModules).map(function (pkg) {
  return {
    test: require.resolve(pkg),
    loader: "expose-loader?" + globalModules[pkg]
  }
});

module.exports = merge(commonConfig, config);