$(document)
    .ready(function () {

        $("#newId").click(() => onrdbChecked("newId"));
        $("#existingId").click(() => onrdbChecked("existingId"));
        $("#restoreApp").click(() => onrdbChecked("restoreApp"));

        $("#setUpbtnNext").click(() => onNextClicked(""));



        $("#btnSetPassword").click(() => onSetPasswordClicked());
        $("#btnAdvOpt").click(() => onbtnShowAdvancedClicked());
        $("input[name='initialIdentifierExisting']").click((arg) => collpaseTextArea(arg));

        setDefaultJsonContent();

        var _password = "";
        var hdString = "m/44'/60'/0'/0";
        var _keystore;
        var _rdbSelected = "newId";
        var _rdbAdvOption = "";

        function onrdbChecked(id) {
        console.log("on onedbChecked " + id)
            _rdbSelected = id;
            if (id === "newId" || id === "restoreApp") {
                $("#setUpbtnNext").text("Next")
                $("input[name='initialIdentifierExisting']").attr("disabled", true)
            } else if (id === "existingId")
                $("input[name='initialIdentifierExisting']").attr("disabled", false)
            else {
                $("#setUpbtnNext").text("Done")

                if ($("#jsonText").hasClass("in"))
                    $("#jsonText").removeClass("in")

                if ($("#passphraseText").hasClass("in"))
                    $("#passphraseText").removeClass("in")

                if ($("#privateKeyText").hasClass("in"))
                    $("#privateKeyText").removeClass("in")
            }

            $("#setUpbtnNext").attr("data-target", "")
        }

        function collpaseTextArea(arg) {
            var id = arg.target.id;

            if ($("#jsonText").hasClass("in"))
                $("#jsonText").removeClass("in")

            if ($("#passphraseText").hasClass("in"))
                $("#passphraseText").removeClass("in")

            if ($("#privateKeyText").hasClass("in"))
                $("#privateKeyText").removeClass("in")
                console.log("In collapseArea  " + id)
            _rdbAdvOption = id;

            if (id === "pasteJson" || id === "passphrase")
                $("#setUpbtnNext").attr("data-target", "#confirmModal")
            else
                $("#setUpbtnNext").attr("data-target", "")
        }

        function onbtnShowAdvancedClicked() {
            _rdbAdvOption = "existingId";
            $("#btnAdvOpt").toggleClass("active")

            if ($("#btnAdvOpt").hasClass("active")) {
                // $("#setUpbtnNext").text("Done")
                $("input[name='initialIdentifierExisting']").attr("disabled", false);
                $(".radio div").removeClass("in");
                $("input[name='initialIdentifier']").removeAttr("checked");
                document
                    .getElementsByName("initialIdentifier")
                    .checked = false;
                $("#existingId").attr("checked", true)
                document
                    .getElementById("existingId")
                    .checked = true;

                $("#passPhrase").attr("checked", true)
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
            var mnemonicPhrase = $("#passphraseTxt").val();
//            alert(_password);
//            alert(mnemonicPhrase);
            if (mnemonicPhrase == "") {
                alert("mnemonic phrase is empty");
                return;
            }

            var arrWords = mnemonicPhrase.split(' ');

            if (arrWords.length != 12) {
                alert("mnemonic phrase doensn't have 12 words.");
                return;

            }

            lightwallet
                .keystore
                .createVault({
                    password: _password,
                    seedPhrase: mnemonicPhrase,
                    //random salt
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
                     var arrAddresses  = _keystore.getAddresses();
                    var privateKey = _keystore.exportPrivateKey(arrAddresses[0], pwDerivedKey);
                    nemonicLogin(password,privateKey,arrAddresses[0]);
                    // window.postMessage("address~" + arrAddresses[0]);
                    // window.postMessage("privateKey~" + privateKey);
                });
        }

        function onNextClicked(id) {
            if (_rdbAdvOption != "")
                _rdbSelected = _rdbAdvOption;
//                else
//                _rdbSelected = id;

            console.log("In onNextClixked " + _rdbSelected);
            switch (_rdbSelected) {
                case "newId":
                    window
                        .location
                        .assign("backupAccount");
                    break;
                case "keyStoreFile":
                        window.location.assign("login");
                    break;
                case "keyStoreJson": {


                   var jsonTxt = $("#jsonText").val();
                                    var pwd = localStorage.getItem("ubunda-psswd");
                                   alert("privateKeyTexta => " + jsonTxt + "pwd => " + pwd)
                                    keyStoreJSONREG(pwd , jsonTxt);
                                    //                        window.location.assign("login");
                                                        break;
                }

                case "passPhrase":
                recoverFromMnemonicPhrase();
                    //window.location.assign("login");
                    //redirect to home page.
                    break;
                case "privateKey":{
                    var privateKeyTexta = $("#privateKeyTexta").val();
                    var pwd = localStorage.getItem("ubunda-psswd");
//                    alert("privateKeyTexta => " + privateKeyTexta + "pwd => " + pwd)
                    nemonicLogin(pwd , privateKeyTexta,"");
                                  //  window.location.assign("login");
                                    break;
                }

                case "web3Provider":
                window.location.assign("login");
                    break;
                case "ledgerWallet":
                window.location.assign("login");
                    break;
                case "restoreApp":
                window.location.assign("login");
                    break;
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
            var minLength = 8;
            var messageError = $("#messageError");
            var password = $("#txtPassword").val(),
                confirmpassword = $("#txtPasswordConfirm").val();

            if (password == "") {
                messageError.text("Password is empty.");
                messageError.show();
                return;
            }

            if (password.length < minLength) {
                messageError.text("Please enter at least 8 characters");
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

            // window.postMessage("password~" + password);
            _password = password;
            localStorage.setItem("ubunda-psswd", password);
            $(".initialSetup-password").hide();
            $(".initialSetup-main").show();
        }
    });