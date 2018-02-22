$(document)
  .ready(function () {
    if(localStorage.getItem("ubunda-psswd") == null) window.href = '/setup';


    var _keystore;
    var language = "english";
    var hdString = "m/44'/60'/0'/0";

      var privateKey = "";
      var arrAddresses = [];

    // var m = new Mnemonic(language);
    var wordsSelected = 0;
    var phrase = "";
    var isSecure = false
      var sectionPage = 1,
        generateSection = 2,
        phraseSection = 3,
        confirmSection = 4,
        nextScreen = 5;

      $("#chkbSecure").click(() => {
        isSecure = !isSecure;
      });
      $("#btnNextStart").click((arg) => onbtnNextStartClicked(arg));
      //$("#mnemonicLanguageSlct").change(() => onMnemonicLanguageChange());

      function onWordClicked(arg) {
        if (arg.target.nodeName === "P") {
          var word = arg.target.innerText;

          $("#" + word).css("visibility", "visible");
          $("#p_" + word).remove();
        } else {
          var word = arg.target.id;
          var p = "<p id='p_" + word + "' class='col-xs-12'>" + word + " </p>";

          $(".backupPhrase-container").append(p);
          $("#" + word).css("visibility", "hidden");
          $(".backupPhrase-container p").click((arg) => onWordClicked(arg));
        }
      }

      function generateMnemonicPhrase() {
        phrase = lightwallet.keystore.generateRandomSeed("");
        $("#mnemonic-phrase").html(phrase);
      }
function registerNewAccount(){
       var p = localStorage.getItem("ubunda-psswd");

        lightwallet.keystore.createVault({
          password: p,
          seedPhrase: phrase,
          //random salt
          hdPathString: hdString
        }, function (err, ks) {
         _keystore = ks
          generateAddressAndPrivateKey(p);
        });

}

      function generateAddressAndPrivateKey(password) {

        _keystore.keyFromPassword(password, function (err, pwDerivedKey) {
          _keystore.generateNewAddress(pwDerivedKey, 1);
           arrAddresses = _keystore.getAddresses();
           privateKey = _keystore.exportPrivateKey(arrAddresses[0], pwDerivedKey);

                   newRegistration(password,privateKey,arrAddresses[0]);
                  // window.location.assign(arrAddresses[0]);
          // window.postMessage("address~" + arrAddresses[0]);
          // window.postMessage("privateKey~" + privateKey);
        });
      }

      function generateWordList() {
        var words = phrase.split(" ");
        var items = "";
        // var wordsUnsorted = shuffleArray(words);
        var sortedWords = words.sort();

        for (var i = 0; i < sortedWords.length; i++)
          items += "<li id ='" + sortedWords[i] + "'>" + sortedWords[i] + "</li>";

        $("#mnemonic-list").html(items);
        $("#mnemonic-list li").on("click", (arg) => onWordClicked(arg));
      }

      function shuffleArray(arr) {
        for (var i = arr.length - 1; i >= 0; i--) {
          var j = Math.ceil(Math.random() * arr.length) - 1;
          var tmp = arr[i];
          arr[i] = arr[j];
          arr[j] = tmp;
        }
        return arr;
      }

      function isCorrectMnemonic(){
        var seed = $.trim($(".backupPhrase-container").children().text());
        return seed === phrase;
      }

      function onbtnNextStartClicked(arg) {
        sectionPage++;
        switch (sectionPage) {
          case generateSection:
            generateMnemonicPhrase();
            $(".backupView-start").hide();
            $(".backupView-generate").show();
            break;
          case phraseSection:
            if (isSecure) {
              generateWordList();
              $(".backupView-generate").hide();
              $(".backupView-phrase").show();
            }
            break;
          case confirmSection:
            if(isCorrectMnemonic()) {
              $(".backupView-phrase").hide();
              $(".backupView-confirm").show();
            } else {
 toastr.error("Mnemonic phrase provided is not matched!!!", "", {
                              "timeOut": "5000",
                              "extendedTImeout": "5000",
                              "positionClass" : "toast-top-full-width"
                          });
              sectionPage--;
            };
            break;
          case nextScreen:
          registerNewAccount()
//           window.location.href = "./login.html"

            break;
          default:
            break;
        }
      }
    });