var walletjs= function () {

              var walletjs = {};

              walletjs.serializedTx = "";
              walletjs.txParams = {};
              walletjs.keystoreJson = '';
              walletjs.wallet = '';
              walletjs.privKey = '';
              walletjs.privKeyWithout0x = '';
              walletjs.privKeyBuffer = '';
              walletjs.getcurr = '';

              walletjs.postRawTxn = function (userPassword, amount, txTo, txnType, nonce, encodedFunction) {

                console.log("userPassword-" + userPassword + "amount-" + amount + "txTo-txTotxnType" + txnType)
                window.postMessage("signTx~" + userPassword + "~" + amount + "~" + txTo + "~" + txnType);

                //         window.document.addEventListener('message', function(e) {
                // walletjs.keystoreJson = JSON.parse(e.data);
                walletjs.keystoreJson = JSON.parse(localStorage.getItem("keystoreContent"));
                window.postMessage("txnType= " + txnType + "---- nonce=> " + nonce + "encodedFunction => " + encodedFunction);
                //                                      walletjs.pubAdd = '0x' +
                // walletjs.keystoreJson.address;
                walletjs.wallet = ethereumjs
                  .Wallet
                  .fromV3(localStorage.getItem("keystoreContent"), userPassword, true);
                //                                      walletjs.privKey =
                // walletjs.wallet.getPrivateKeyString(); walletjs.privKeyWithout0x =
                // walletjs.privKey.substring(2);                   walletjs.privKeyBuffer =
                // ethereumjs.Buffer.Buffer.from(walletjs.privKeyWithout0x, 'hex');
                privKey = walletjs
                  .wallet
                  .getPrivateKeyString();
                privKeyWithout0x = privKey.substring(2);
                privKeyBuffer = ethereumjs
                  .Buffer
                  .Buffer
                  .from(privKeyWithout0x, 'hex');

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
                      window.postMessage("Start transaction signing");
                      window.postMessage("params message -> " + JSON.stringify(txParams))
                      var txn = new ethereumjs.Tx(txParams);
                      txn.sign(privKeyBuffer)
                      walletjs.serializedTx = txn
                        .serialize()
                        .toString('hex');
                      window.postMessage("signed message -> " + walletjs.serializedTx)
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
                      window.postMessage("Start transaction signing");
                      window.postMessage("params message -> " + JSON.stringify(txParams))
                      var txn = new ethereumjs.Tx(txParams);
                      txn.sign(privKeyBuffer)
                      walletjs.serializedTx = txn
                        .serialize()
                        .toString('hex');
                      window.postMessage("signed message -> " + walletjs.serializedTx)
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

              return walletjs;
            }();
module.exports = {
walletJs:walletjs
}

