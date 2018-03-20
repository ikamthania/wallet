
var Transaction = require('ethereumjs-tx/index.js')
exports.postRawTxn=function(userPassword,amount,txTo,txnType,nonce,encodedFunction){

//Ethereum network chainId--->Mainnet-1,Ropsten-3
// create a blank transaction
var chainId=3
var tx = new Transaction(null, chainId) // ropsten Tx EIP155

console.log("Params--->"+txTo+" "+" "+amount+" "+nonce+" "+encodedFunction+" ")

 switch (txnType) {

      case "eth":
        {
        tx.to=txTo
        tx.nonce = nonce
        tx.gasPrice = 21000000000
        tx.gasLimit = 315010
        tx.value = amount
        tx.data = encodedFunction
        var pvt=window.localStorage.getItem("priKey")
        var privateKey = new Buffer(pvt, 'hex')
        tx.sign(privateKey)
          break;
        }
      default:
        {
                 tx.nonce = nonce
                 tx.gasPrice = 21000000000
                 tx.gasLimit = 3057640
                 tx.data = encodedFunction
                 var pvt=window.localStorage.getItem("priKey")
                 var privateKey = new Buffer(pvt, 'hex')
                 tx.sign(privateKey)
                   break;
          }

    }
    console.log("Signed txn--->"+tx.serialize().toString('hex'));
return tx.serialize().toString('hex');
}

  exports.getKey = function () {

    var txParams = {
      nonce: nonce,
      gasPrice: 21000000000, //'0x1e8f1c10800',
      gasLimit: 315010, //'0x301114',
      to: "0x671EDE1c3079C6B2B377b200076b6C251C311778",
      value: "0.01",
      data: "0x0",
      chainId: 3
    }

    var txn = new ethereumjs.Tx(txParams);
    txn.sign("0d1b87cc3ac02bfc523179e0422b1ab3aa6032ee4a4544de6957915535e08f87")
    var signedTxn = txn
      .serialize()
      .toString('hex');
    //  walletjs.setflag = true;
    console.log("walletjs.serializedTx  ---> " + signedTxn);
    return signedTxn;
  };

 exports.getnumberFormat = function(curr) {
   var getcurr = curr.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
  return getcurr;
  }

    exports.deleteAccount = function(publicKey,currentAccount) {
            //alert(publicKey)
            var pwd = localStorage.getItem("ubunda-psswd");
         window.postMessage("deleteAccount~" + pwd + "~" + publicKey + "~" + currentAccount);
    }
