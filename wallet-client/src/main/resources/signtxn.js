
var Transaction = require('ethereumjs-tx/index.js')
exports.getSignTxn=function(priKey,amount,txTo, nonce,encodedFunction,gasPrice,gasLimit){

    //Ethereum network chainId--->Mainnet-1,Ropsten-3
    var chainId=3
    var tx = new Transaction(null, chainId) // ropsten Tx EIP155
    tx.to=txTo
    tx.nonce = nonce
    tx.gasPrice =gasPrice
    tx.gasLimit =gasLimit
    if (encodedFunction != "0x0") {
        tx.data = encodedFunction
    } else {
        tx.value = amount
    }

    var pvt=priKey
    var privateKey = new Buffer(pvt, 'hex')
    tx.sign(privateKey)
    return tx.serialize().toString('hex');
}

// what the heck is this ?
exports.getnumberFormat = function(curr) {
    var getcurr = curr.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
    return getcurr;
}
