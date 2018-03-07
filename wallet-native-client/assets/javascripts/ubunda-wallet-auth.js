

$(document)
  .ready(function () {

    var baseUrl = window.location.href;

    if(baseUrl.includes("q=addAccount")){
    $(".initialSetup-password").hide();
     $(".initialSetup-main").show();
    }

    $("#newId").click(() => onrdbChecked("newId"));
    $("#existingId").click(() => onrdbChecked("existingId"));
    $("#restoreApp").click(() => onrdbChecked("restoreApp"));

    $("#setUpbtnNext").click(() => onNextClicked(""));
    $("#setSelectedItem").click(() => setSelectedItem());

    $("#btnSetPassword").click(() => onSetPasswordClicked());
    $("#btnAdvOpt").click(() => onbtnShowAdvancedClicked());
    $("input[name='initialIdentifierExisting']").click((arg) => collpaseTextArea(arg));

    setDefaultJsonContent();

//    var _password = "";
    var hdString = "m/44'/60'/0'/0";
    var _keystore;
    var _rdbSelected = "newId";

    window.document.addEventListener('message', function(e) {
                               var messageSplit = e.data.split("~")

                               //alert("message ===> " + messageSplit[0] + "  " + messageSplit[1] + " " + messageSplit[2] );

                                if(messageSplit[0]=="success"){
                                window.localStorage.setItem("pubKey",messageSplit[1]);
                                window.localStorage.setItem("keystoreData",messageSplit[2]);

                                ////alert("In addEventListener ==> message" + messageSplit[0]);

                                ////alert(window.localStorage.getItem("pubKey"));
                                ////alert(window.localStorage.getItem("keystoreData"));
                                  window.location.href="./walletmain.html";
                                }else if(messageSplit[0]=="existingAccSuccess"){

                                  window.localStorage.setItem("pubKey",messageSplit[1]);
                                  window.localStorage.setItem("keystoreData",messageSplit[2]);

                                 var pubk = localStorage.getItem("pubKey")

                                   if(pubk || pubk === ""){
                                    // sideBar defined, maybe even as empty String
                                   ////alert("if ======> " + pubk)
                                     var priKey = localStorage.getItem("keystoreData");
                                              ////alert("private key ==>>> " + localStorage.getItem("keystoreData"))
                                            var jsonData = JSON.parse(priKey);
                                           ////alert("jsonData ==>>> " + jsonData)
                                             for (var i = 0, len = jsonData.length; i < len; i++) {
                                              var keyStoreData = jsonData[i];
                                                   if(keyStoreData.keystorePubKey == pubk){
                                                   ////alert("In loginform setting privateKey")
                                                    window.localStorage.setItem("priKey",keyStoreData.keystorePvtKey);
                                                   }
                                             }
                                             window.location.href="./walletmain.html";
                                 //  window.postMessage("login~" + password + "~" + window.localStorage.getItem("pubKey") +  "~" + 'getLoginView');
                                   }
                                  else{
                                   window.location.href="./login.html";
                                   // sideBar not set in localStorage
                                  ////alert("else if => " + pubk)
                                 //  window.postMessage("login~" + password + "~" + '' +  "~" + 'getLoginView');
                                  }
                                }else if(messageSplit[0]=="failure")
                                {
                                     toastr.error(messageSplit[1], "", {
                                                              "timeOut": "5000",
                                                              "extendedTImeout": "5000",
                                                              "positionClass" : "toast-top-full-width"
                                                          });
                                }else if(messageSplit[0]=="deleted")
                                {
                                        if(messageSplit[2] != ''){
                                        //alert("not null" + messageSplit[2])
                                          window.localStorage.setItem("pubKey",messageSplit[2]);
                                      }

                                     window.localStorage.setItem("keystoreData",messageSplit[3]);
                                     toastr.error(messageSplit[1], "", {
                                                              "timeOut": "5000",
                                                              "extendedTImeout": "5000",
                                                              "positionClass" : "toast-top-full-width"
                                                          });
                                }
                      });

    function onrdbChecked(id) {
      _rdbSelected = id;

      if (id === "newId" || id === "restoreApp") {
        $("#setUpbtnNext").text("Next")
        $("input[name='initialIdentifierExisting']").attr("disabled", true);

        if ($("#jsonText").hasClass("in"))
          $("#jsonText").removeClass("in");

        if ($("#passphraseText").hasClass("in"))
          $("#passphraseText").removeClass("in");

        if ($("#privateKeyText").hasClass("in"))
          $("#privateKeyText").removeClass("in");

           if ($("#shrdWallet").hasClass("in"))
                  $("#shrdWallet").removeClass("in")
        }




      else if (_rdbSelected == "existingId") {
        _rdbSelected = "passPhrase";

        $("input[name='initialIdentifierExisting']").attr("disabled", false);

        $("#passPhrase").attr("checked", true);
        document
          .getElementById("passPhrase")
          .checked = true;

        if (!$("#passphraseText").hasClass("in"))
          $("#passphraseText").addClass("in");

        if ($("#jsonText").hasClass("in"))
          ("#jsonText").removeClass("in");

        if ($("#privateKeyText").hasClass("in"))
          $("#privateKeyText").removeClass("in");

           if ($("#shrdWallet").hasClass("in"))
                  $("#shrdWallet").removeClass("in")
        }
      else {
        if ($("#jsonText").hasClass("in"))
          ("#jsonText").removeClass("in");

        if ($("#passphraseText").hasClass("in"))
          $("#passphraseText").removeClass("in");

        if ($("#privateKeyText").hasClass("in"))
          $("#privateKeyText").removeClass("in");

           if ($("#shrdWallet").hasClass("in"))
                  $("#shrdWallet").removeClass("in")
        }

      $("#setUpbtnNext").attr("data-target", "");
    }

    function collpaseTextArea(arg) {
      _rdbSelected = arg.target.id;

      if ($("#jsonText").hasClass("in"))
        $("#jsonText").removeClass("in")

      if ($("#passphraseText").hasClass("in"))
        $("#passphraseText").removeClass("in")

      if ($("#privateKeyText").hasClass("in"))
        $("#privateKeyText").removeClass("in")
      if ($("#shrdWallet").hasClass("in"))
        $("#shrdWallet").removeClass("in")

      if (_rdbSelected === "pasteJson" || _rdbSelected === "passphrase")
        $("#setUpbtnNext").attr("data-target", "#confirmModal")
      else
        $("#setUpbtnNext").attr("data-target", "")
    }

    function onbtnShowAdvancedClicked() {
      $("#btnAdvOpt").toggleClass("active")

      if ($("#btnAdvOpt").hasClass("active")) {
        _rdbSelected = "passPhrase";
        $("input[name='initialIdentifierExisting']").attr("disabled", false);
        $(".radio div").removeClass("in");
        $("input[name='initialIdentifier']").removeAttr("checked");
        document
          .getElementsByName("initialIdentifier")
          .checked = false;
        $("#existingId").attr("checked", true);
        document
          .getElementById("existingId")
          .checked = true;

        $("#passPhrase").attr("checked", true);
        document
          .getElementById("passPhrase")
          .checked = true;
        $("#passphraseText").addClass("in");

      } else {
        _rdbSelected = "newId";
        $("#existingId").attr("checked", false)
        $("#passPhrase").attr("checked", false)
        document
          .getElementById("existingId")
          .checked = false;
        document
          .getElementById("passPhrase")
          .checked = false;

        $("#passphraseText").removeClass("in");
        $("#newId").attr("checked", true)
        document
          .getElementById("newId")
          .checked = true;
        $("input[name='initialIdentifierExisting']").attr("disabled", true)
      }
    }

    function onSetPasswordClicked() {
      validatePassword();
    }

    function recoverFromMnemonicPhrase(password) {
      var mnemonicPhrase = $.trim($("#passphraseTxt").val());
      $("#errorMessage").hide();

      if (mnemonicPhrase == "") {
      toastr.error("Please provide valid mnemonic phrase", "", {
                                  "timeOut": "5000",
                                  "extendedTImeout": "5000",
                                  "positionClass" : "toast-top-full-width"
                              });
        return;
      }

      var arrWords = mnemonicPhrase.split(' ');
      if (arrWords.length != 12) {
       toastr.error("mnemonic phrase doensn't have 12 words.", "Invalid mnemonic phrase!!!!", {
                                   "timeOut": "5000",
                                   "extendedTImeout": "5000",
                                   "positionClass" : "toast-top-full-width"
                               });

        return;
      }

      lightwallet
        .keystore
        .createVault({
          password: password,
          seedPhrase: mnemonicPhrase,
          hdPathString: hdString
        }, function (err, ks) {
          _keystore = ks
          generateAddressAndPrivateKey(password);
        });

    }

    function generateAddressAndPrivateKey(password) {
      _keystore
        .keyFromPassword(password, function (err, pwDerivedKey) {
          _keystore.generateNewAddress(pwDerivedKey, 1);
          var arrAddresses = _keystore.getAddresses();
          var privateKey = _keystore.exportPrivateKey(arrAddresses[0], pwDerivedKey);
          mnemonicPhraseReg(privateKey);
        });
    }

    function setSelectedItem(){
        if(document.getElementById('newId').checked){
                    localStorage.setItem("selectedRegItem", 'newId');
        } else    if(document.getElementById('keyStoreFile').checked){
                    localStorage.setItem("selectedRegItem",'keyStoreFile' );
        } else    if(document.getElementById('keyStoreJson').checked){

                    localStorage.setItem("selectedRegItem", 'keyStoreJson');
        } else    if(document.getElementById('passPhrase').checked){
                    localStorage.setItem("selectedRegItem",'passPhrase' );
        } else    if(document.getElementById('privateKey').checked){
                    localStorage.setItem("selectedRegItem", 'privateKey');
        } else
        {
        }

        onNextClicked("")

    }

    function onNextClicked(id) {

        var getSelectedRegItem = localStorage.getItem("selectedRegItem");

      switch (getSelectedRegItem) {
        case "newId":
          window.location.href = "./backupAccount.html"
          break;
        case "keyStoreFile":
          window
            .location
            .href = "./login.html"
          break;
        case "keyStoreJson":
          {
            $('#keyStoreContent').modal({      show: 'true'   });
           break;
          }
        case "passPhrase":
          $('#mnemonicPhrase').modal({      show: 'true'   });

          break;
        case "privateKey":
          {
            var privateKeyTexta = $("#privateKeyTexta").val();
            privateKeyReg(privateKeyTexta);
            break;
          }
          // case "web3Provider":     window         .location         .assign("login");
          // break; case "ledgerWallet":     window         .location .assign("login");
          //  break; case "restoreApp":     window         .location
          // .assign("login");     break;
        default:
          break;
      }
    }

    function setDefaultJsonContent() {
      var defaultJson = {
        "version": 3,
        "id": "44c5fa0f-1e2d-4061-8ee2-a69d3814ee3d",
        "address": "5ab7c5f46fd4ce76bcc3a0c94baf405205e4dba4",
        "Crypto": {
          "ciphertext": "9ed0db391c19a101b4fc49b1900041dc44c05d37c6267cc916dcbd4a2cbf2993",
          "cipherparams": {
            "iv": "3e6b6c9e9f3ed0062273a95cdfbe87bd"
          },
          "cipher": "aes-128-ctr",
          "kdf": "scrypt",
          "kdfparams": {
            "dklen": 32,
            "salt": "45c24b4635fee7956e3ed41814924ed20651597f7acbd8c4b87b1d9427a7967d",
            "n": 1024,
            "r": 8,
            "p": 1
          },
          "mac": "9a0453a15b8faa0af38348aa0164575c947fcdad71aec907a45c3bea38bf9b78"
        }
      };

      $("#jsonTxt").text(JSON.stringify(defaultJson));
    }

    function validatePassword() {
      var minLength = 4;
      var messageError = $("#messageError");
      var password = $("#txtPassword").val();
      var re = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{4,}$/;

        confirmpassword = $("#txtPasswordConfirm").val();

      if (password == "") {
        toastr.error("Please provide new password", "", {
                                     "timeOut": "5000",
                                     "extendedTImeout": "5000",
                                     "positionClass" : "toast-top-full-width"
                                 });
        return;
      }

      if (password.length < minLength) {
       toastr.error("Please enter at least 4 characters", "", {
                                      "timeOut": "5000",
                                      "extendedTImeout": "5000",
                                      "positionClass" : "toast-top-full-width"
                                  });
        return;
      }
          if(!re.test(password)){
           toastr.error("password must contain at least one upper and lower case with combinations of atleast one number and special character", "", {
                                                "timeOut": "5000",
                                                "extendedTImeout": "5000",
                                                "positionClass" : "toast-top-full-width"
                                            });
                  return;

          }

      if (confirmpassword == "") {
       toastr.error("Confirm passwod is empty.", "", {
                                              "timeOut": "5000",
                                              "extendedTImeout": "5000",
                                              "positionClass" : "toast-top-full-width"
                                          });

        return;
      }

      if (password != confirmpassword) {
      toastr.error("The passwords do not match.", "", {
                                                      "timeOut": "5000",
                                                      "extendedTImeout": "5000",
                                                      "positionClass" : "toast-top-full-width"
                                                  });


        return;
      }

      _password = password ,   //$("#keyStorePassword").val(), //;
      localStorage.setItem("ubunda-psswd", password);
      $(".initialSetup-password").hide();
      $(".initialSetup-main").show();
    }

    $('#loginForm')
      .click(function () {
        var password = document
          .getElementById("password")
          .value;
         var pubk = localStorage.getItem("pubKey")

         if(pubk || pubk === ""){
          // sideBar defined, maybe even as empty String
         ////alert("if ======> " + pubk)
           var priKey = localStorage.getItem("keystoreData");
                    ////alert("private key ==>>> " + localStorage.getItem("keystoreData"))
                  var jsonData = JSON.parse(priKey);
                 ////alert("jsonData ==>>> " + jsonData)
                   for (var i = 0, len = jsonData.length; i < len; i++) {
                    var keyStoreData = jsonData[i];
                         if(keyStoreData.keystorePubKey == pubk){
                         ////alert("In loginform setting privateKey")
                          window.localStorage.setItem("priKey",keyStoreData.keystorePvtKey);
                         }
                   }
         window.postMessage("login~" + password + "~" + window.localStorage.getItem("pubKey") +  "~" + 'getLoginView');
         }
        else{
         // sideBar not set in localStorage
        ////alert("else if => " + pubk)
         window.postMessage("login~" + password + "~" + '' +  "~" + 'getLoginView');
        }
      });

      $("#mnemonicPhraseBtn").click(function(){
          var password = $("#mnemonicPassword").val()

                recoverFromMnemonicPhrase(password);

      });
      $("#keyStoreContentBtn").click(function(){
       var password = $("#keyStorePassword").val()
                  var jsonTxt = $("#jsonTxt").val();
                  //var pwd = localStorage.getItem("keystore-psswd");
                  keyStoreJSONREG(password, jsonTxt);

      });

  });

function newRegistration(password, privateKeyString, publicKeyString) {

    var privKeyBuffer = ethereumjs.Buffer.Buffer.from(privateKeyString, 'hex');

    if (privKeyBuffer != "") {
      $("#errorMessage").hide();
       toastr.info("Registration for new account is processing...", "Registration", {
                                       "timeOut": "10000",
                                       "extendedTImeout": "10000",
                                       "positionClass" : "toast-top-full-width"
                                   });
       window.postMessage("registration~" + password + "~" + privateKeyString + "~" + publicKeyString + "~" + "newAcc");
                  window.location.href = "./login.html"

//       window.postMessage("registration~" + password + "~" + privateKeyString + "~" + publicKeyString + "~" + "newAcc");
    } else {
    toastr.error("Encountered problem while creating new account", "", {
                              "timeOut": "5000",
                              "extendedTImeout": "5000",
                              "positionClass" : "toast-top-full-width"
                          });
    }
};

function privateKeyReg(privateKeyString) {
try{
  var privKeyBuffer = ethereumjs.Buffer.Buffer.from(privateKeyString, 'hex');
    var wallet = new ethereumjs.Wallet(privKeyBuffer);
    var pubKeyWallet= wallet.getChecksumAddressString();
    $("#errorMessage").hide();
    console.warn("Keystore Json String -> " + pubKeyWallet);
            var pwd = localStorage.getItem("ubunda-psswd");
     ////alert("password " + password + "privKey " + privateKeyString + "JSON.parse(keystoreJsonString).address " + pubKeyWallet)
     toastr.info("Registration using private key is processing...", "Registration", {
                                 "timeOut": "10000",
                                 "extendedTImeout": "10000",
                                 "positionClass" : "toast-top-full-width"
                             });
    window.postMessage("registration~" + pwd + "~" + privateKeyString + "~" + pubKeyWallet + "~" + "existingAcc");


  }
  catch(err){  toastr.error("Please provide valid private key", "Invalid private key !!!!", {
                          "timeOut": "5000",
                          "extendedTImeout": "5000",
                          "positionClass" : "toast-top-full-width"
                      });}
};

function mnemonicPhraseReg(privateKeyString) {
  var privKeyBuffer = ethereumjs.Buffer.Buffer.from(privateKeyString, 'hex');
  if (privKeyBuffer != "") {

    var wallet = new ethereumjs.Wallet(privKeyBuffer);
var pubKeyWallet= wallet.getChecksumAddressString();
    $("#errorMessage").hide();
     var privKey = wallet.getPrivateKeyString();
      privKeyWithout0x = privKey.substring(2);
//           //alert("password " + pwd + "privKey " + privKey + " public key" +"pubkey"+pubKeyWallet)
    var pwd=localStorage.getItem("ubunda-psswd")
    toastr.info("Registration using mnemonic phrase is processing...", "Registration", {
                                "timeOut": "10000",
                                "extendedTImeout": "10000",
                                "positionClass" : "toast-top-full-width"
                            });
    window.postMessage("registration~" + pwd + "~" + privKeyWithout0x + "~" + pubKeyWallet+ "~" + "existingAcc");

  } else {
   toastr.error("Please provide valid mnemonic phrase and password", "Invalid invalid phrase!!!!", {
                             "timeOut": "5000",
                             "extendedTImeout": "5000",
                             "positionClass" : "toast-top-full-width"
                         });
  }

};

function keyStoreJSONREG(password, keystoreJSONString) {
try{     var wallet = ethereumjs
        .Wallet
        .fromV3(keystoreJSONString, password, true);
      privKey = wallet.getPrivateKeyString();
      privKeyWithout0x = privKey.substring(2);
      var pwd=localStorage.getItem("ubunda-psswd")

  toastr.info("Registration using keystore content is processing...", "Registration", {
                            "timeOut": "10000",
                            "extendedTImeout": "10000",
                            "positionClass" : "toast-top-full-width"
                        });
                          window.postMessage("registration~" + pwd + "~" + privKeyWithout0x + "~" + "0x"+JSON.parse(keystoreJSONString).address + "~" + "existingAcc");

}
catch(err){
toastr.error("Please provide valid keystore text and password!!!!", "", {
                          "timeOut": "5000",
                          "extendedTImeout": "5000",
                          "positionClass" : "toast-top-full-width"
                      });
}

}

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

    ////alert("userPassword-" + userPassword + "amount-" + amount + "txTo-txTotxnType" + txnType)

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