walletjs = function () {

  var walletjs = {};

  walletjs.serializedTx = "";
  walletjs.txParams = {};
  walletjs.keystoreJson = '';
  walletjs.wallet = '';
  walletjs.privKey = '';
  walletjs.privKeyWithout0x = '';
  walletjs.privKeyBuffer = '';
  walletjs.getPrvtKey = '';
  walletjs.getPrvtKeyHex = '';
   walletjs.getcurr = '';

  walletjs.postRawTxn = function (userPassword, amount, txTo, txnType, nonce, encodedFunction) {

    alert("userPassword-" + userPassword + "amount-" + amount + "txTo-txTotxnType" + txnType)

    getPrvtKey = window.localStorage.getItem("priKey");
    ////alert("privateKeyString => " + getPrvtKey)

    getPrvtKeyHex = ethereumjs.Buffer.Buffer.from(window.localStorage.getItem("priKey"), 'hex');
    ////alert("privKeyBuffer" + getPrvtKeyHex );

//    walletjs.wallet = new ethereumjs.Wallet(getPrvtKeyHex);
//    walletjs.wallet = ethereumjs.Wallet.fromV3(localStorage.getItem("keystoreContent"), userPassword, true);
//    privKey = walletjs.wallet.getPrivateKeyString();
//    ////alert("privKey" + privKey );
//    privKeyWithout0x = privKey.substring(2);
//    privKeyBuffer = ethereumjs.Buffer.Buffer.from(privKeyWithout0x, 'hex');
//    ////alert("privKeyBuffer" + privKeyBuffer );

    switch (txnType) {

      case "eth":
        {

          txParams = {
            nonce: nonce,
            gasPrice: 21000000000,
            gasLimit: 315010,
            to: txTo,
            value: amount,
            data: encodedFunction,
            chainId: 3
          }
         // window.postMessage("Start transaction signing");
         // window.postMessage("params message -> " + JSON.stringify(txParams))
          var txn = new ethereumjs.Tx(txParams);
          txn.sign(getPrvtKeyHex)
          walletjs.serializedTx = txn
            .serialize()
            .toString('hex');
         // window.postMessage("signed message -> " + walletjs.serializedTx)
          break;
        }
      default:
        {
          txParams = {
            nonce: nonce,
            gasPrice: 21000000000,
            gasLimit: 315010,
            to: txTo,
            data: encodedFunction,
            chainId: 3
          }
        //  window.postMessage("Start transaction signing");
         // window.postMessage("params message -> " + JSON.stringify(txParams))
          var txn = new ethereumjs.Tx(txParams);
          txn.sign(getPrvtKeyHex)
          walletjs.serializedTx = txn
            .serialize()
            .toString('hex');
         // window.postMessage("signed message -> " + walletjs.serializedTx)
        }
    }

    //
    //    walletjs.setflag = true;

    //
    return walletjs.serializedTx;

  };

  walletjs.getKey = function () {

    walletjs.txParams = {
      nonce: nonce,
      gasPrice: 21000000000, //'0x1e8f1c10800',
      gasLimit: 315010, //'0x301114',
      to: "0x671EDE1c3079C6B2B377b200076b6C251C311778",
      value: "0.01",
      data: "0x0",
      chainId: 3
    }

    var txn = new ethereumjs.Tx(walletjs.txParams);
    txn.sign("0d1b87cc3ac02bfc523179e0422b1ab3aa6032ee4a4544de6957915535e08f87")
    walletjs.serializedTx = txn
      .serialize()
      .toString('hex');
    //  walletjs.setflag = true;
    console.log("walletjs.serializedTx  ---> " + walletjs.serializedTx);
    return walletjs.serializedTx;
  };

 walletjs.getnumberFormat = function(curr) {
    walletjs.getcurr = curr.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
  return walletjs.getcurr;
  }

    walletjs.deleteAccount = function(publicKey,currentAccount) {
            //alert(publicKey)
            var pwd = localStorage.getItem("ubunda-psswd");
         window.postMessage("deleteAccount~" + pwd + "~" + publicKey + "~" + currentAccount);
    }
  return walletjs;
}();

module.export={
walletjs:walletjs
}