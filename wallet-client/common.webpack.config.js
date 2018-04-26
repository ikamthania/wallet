var webpack = require('webpack');
var UglifyJsPlugin = require('uglifyjs-webpack-plugin');
module.exports = {
    plugins: [
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery" // Bootstrap.js uses global jQuery internally
        }),
        new UglifyJsPlugin({ parallel: true })
    ]
};