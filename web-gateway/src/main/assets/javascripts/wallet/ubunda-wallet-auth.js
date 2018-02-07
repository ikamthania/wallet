$(document)
  .ready(function () {
var pubKey=""
    $("#newId").click(() => onrdbChecked("newId"));
    $("#existingId").click(() => onrdbChecked("existingId"));
    $("#restoreApp").click(() => onrdbChecked("restoreApp"));

    $("#setUpbtnNext").click(() => onNextClicked(""));

    $("#btnSetPassword").click(() => onSetPasswordClicked());
    $("#btnAdvOpt").click(() => onbtnShowAdvancedClicked());
    $("input[name='initialIdentifierExisting']").click((arg) => collpaseTextArea(arg));

    $("#newId").prop("checked", true);
    
    setDefaultJsonContent();

    var _password = "";
    var hdString = "m/44'/60'/0'/0";
    var _keystore;
    var _rdbSelected = "newId";

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
        }
      else {
        if ($("#jsonText").hasClass("in")) 
          ("#jsonText").removeClass("in");
        
        if ($("#passphraseText").hasClass("in")) 
          $("#passphraseText").removeClass("in");
        
        if ($("#privateKeyText").hasClass("in")) 
          $("#privateKeyText").removeClass("in");
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

    function recoverFromMnemonicPhrase() {
      var mnemonicPhrase = $.trim($("#passphraseTxt").val());
      $("#errorMessage").hide();
      
      if (mnemonicPhrase == "") {
       $("#errorMessage").text("mnemonic phrase is empty");
       $("#errorMessage").show();
        return;
      }

      var arrWords = mnemonicPhrase.split(' ');
      if (arrWords.length != 12) {
        $("#errorMessage").text("mnemonic phrase doensn't have 12 words.");
        $("#errorMessage").show();
        return;
      }

      lightwallet
        .keystore
        .createVault({
          password: _password,
          seedPhrase: mnemonicPhrase,
          hdPathString: hdString
        }, function (err, ks) {
          _keystore = ks
          generateAddressAndPrivateKey(_password);
        });

    }

    function generateAddressAndPrivateKey(password) {
      _keystore
        .keyFromPassword(password, function (err, pwDerivedKey) {
          _keystore.generateNewAddress(pwDerivedKey, 1);
          var arrAddresses = _keystore.getAddresses();
          var privateKey = _keystore.exportPrivateKey(arrAddresses[0], pwDerivedKey);
          nemonicLogin(password, privateKey);
        });
    }

    function onNextClicked(id) {
      switch (_rdbSelected) {
        case "newId":
          window
            .location
            .assign("backupAccount");
          break;
        case "keyStoreFile":
          window
            .location
            .assign("login");
          break;
        case "keyStoreJson":
          {
            var jsonTxt = $("#jsonTxt").val();
            var pwd = localStorage.getItem("ubunda-psswd");
            keyStoreJSONREG(pwd, jsonTxt);
            break;
          }
        case "passPhrase":
          recoverFromMnemonicPhrase();
          break;
        case "privateKey":
          {
            var privateKeyTexta = $("#privateKeyTexta").val();
            var pwd = localStorage.getItem("ubunda-psswd");
            nemonicLogin(pwd, privateKeyTexta);
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
      var password = $("#txtPassword").val(),
        confirmpassword = $("#txtPasswordConfirm").val();

      if (password == "") {
        messageError.text("Password is empty.");
        messageError.show();
        return;
      }

      if (password.length < minLength) {
        messageError.text("Please enter at least 4 characters");
        messageError.show();
        return;
      }

      if (confirmpassword == "") {
        messageError.text("Confirm passwod is empty.");
        messageError.show();
        return;
      }

      if (password != confirmpassword) {
        messageError.text("The passwords do not match.");
        messageError.show();
        return;
      }
      _password = password;
      localStorage.setItem("ubunda-psswd", password);
      $(".initialSetup-password").hide();
      $(".initialSetup-main").show();
    }

    $('#loginForm')
      .click(function () {
        var password = document
          .getElementById("password")
          .value;
        window.postMessage("login~" + password);
        window.localStorage.setItem("pubKey",pubKey)
       // window.location.href="../views/walletmain.html"
      });

  });

function mnemonicReg(password, privateKeyString) {

  var privKeyBuffer = ethereumjs
    .Buffer
    .Buffer
    .from(privateKeyString, 'hex');
  if (privKeyBuffer != "") {
    var wallet = new ethereumjs.Wallet(privKeyBuffer);
    var keystoreJsonString = wallet.toV3String(password);
    $("#errorMessage").hide();
    console.warn("Keystore Json String -> " + keystoreJsonString);
    localStorage.setItem("keystoreContent", keystoreJsonString);
    window.postMessage("registration~" + password + "~" + keystoreJsonString);
  } else {
    $("#errorMessage").text("private key not found");
    $("#errorMessage").show();
  }

};

function nemonicLogin(password, privateKeyString) {
  var privKeyBuffer = ethereumjs
    .Buffer
    .Buffer
    .from(privateKeyString, 'hex');
  if (privKeyBuffer != "") {
    var wallet = new ethereumjs.Wallet(privKeyBuffer);
    var keystoreJsonString = wallet.toV3String(password);
    $("#errorMessage").hide();
    console.warn("Keystore Json String -> " + keystoreJsonString);
    localStorage.setItem("keystoreContent", keystoreJsonString);
    window.postMessage("registration~" + password + "~" + keystoreJsonString);
    var pubKey = JSON
      .parse(keystoreJsonString)
      .address;
    var setPubKey = "0x" + pubKey;
    window
      .location
      .assign(setPubKey);

  } else {
    $("#errorMessage").text("private key not found");
    $("#errorMessage").show();
  }

};

function keyStoreJSONREG(password, privateKeyString) {
  console.warn("Keystore Json String -> " + privateKeyString);
  localStorage.setItem("keystoreContent", privateKeyString);
  window.postMessage("registration~" + password + "~" + privateKeyString);
  var pubKey = JSON
    .parse(privateKeyString)
    .address;
  var setPubKey = "0x" + pubKey;
  window
    .location
    .assign(setPubKey);
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
