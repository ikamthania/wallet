
var Transaction = require('ethereumjs-tx/index.js')
exports.getSignTxn=function(priKey,amount,txTo,txnType,nonce,encodedFunction,gasPrice,gasLimit){

    //Ethereum network chainId--->Mainnet-1,Ropsten-3
    // create a blank transaction
    var chainId=3
    var tx = new Transaction(null, chainId) // ropsten Tx EIP155
    tx.to=txTo
    tx.nonce = nonce
    tx.gasPrice =gasPrice// 21000000000
    tx.gasLimit =gasLimit// 315010
    tx.value = amount
    tx.data = encodedFunction
    var pvt=priKey
    var privateKey = new Buffer(pvt, 'hex')
    tx.sign(privateKey)
    return tx.serialize().toString('hex');
}

exports.getnumberFormat = function(curr) {
    var getcurr = curr.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
    return getcurr;
}
