
var Transaction = require('ethereumjs-tx/index.js')
exports.getSignTxn=function(priKey,amount,txTo, nonce,encodedFunction,gasPrice,gasLimit){

    //Ethereum network chainId--->Mainnet-1,Ropsten-3
    // create a blank transaction
    var chainId=3

    var tx = new Transaction(null, chainId) // ropsten Tx EIP155
    tx.to=txTo
    tx.nonce = nonce
    tx.gasPrice =gasPrice// 21000000000
    tx.gasLimit =gasLimit// 315010
    if (encodedFunction != "0x0") {
        tx.data = encodedFunction
    } else {
        tx.value = amount
    }

//  txParams = {
//        nonce: nonce,
//        gasPrice: 21000000000,
//        gasLimit: 315010,
//        to: txTo,
//        data: encodedFunction,
//        chainId: 3,
//        value:0
//      }
//          var tx = new Transaction(txParams) // ropsten Tx EIP155

    var pvt=priKey
    var privateKey = new Buffer(pvt, 'hex')
    tx.sign(privateKey)
    return tx.serialize().toString('hex');
}

exports.getnumberFormat = function(curr) {
    var getcurr = curr.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
    return getcurr;
}
