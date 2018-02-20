$(document)
    .ready(function () {
        $("#btnPrevious").hide();
        $("#btnGetStarted").click(onGetStartedClicked);

        // var fileInput = "<div id=\"input_file_div\"><h4>File</h4><input type=\"file\"
        // id=\"input-file\" n" +         "ame=@Messages(\"wallet.fileName\")
        // class=\"col-xs-12 info\"  data-error=\"Valid " +         "wallet file is
        // required\"><div class=\"help-block with-errors \" style=\"font-si" +
        // "ze: 16px;\"></div></div>" var emailInput="<div
        // id=\"email_address_div\"><h4>Email Address:</h4> <input type=\"email\"
        // class=\"col-md-12 col-xs-12 email-address-input\" id=\"emailAddress\"
        // name=\"emailAddress\" required=\"true\" data-error=\"valid email is
        // required\"><div class=\"help-block with-errors \" style=\"font-size:
        // 16px;\"></div></div>" setTimeout(function () {
        // $("#main-splash").addClass("hidden");
        // $("#welcome-splash").removeClass("hidden"); }, 2000);
        $(".test1").bind("click", function () {
            switch ($(this).attr("id")) {
                case "test1":
                    if (document.getElementById('input_file_div') == null) {
                        $("#file_field").append(fileInput);
                        $('.email_field').css("display", "none");
                    }
                    break;
                case "test2":
                    if (document.getElementById('email_address_div') == null) {
                        $('.email_field').css("display", "initial");
                        $("#input_file_div").remove();
                    }
                    break;
                default:
                    window.alert("None");
            }
        });

        var current = 0;

        function onGetStartedClicked() {
            current++;
            $("#main-splash").addClass("hidden");
            $("#welcome-splash").removeClass("hidden");
            checkPreviousButtonVisibility();
        }

        function checkPreviousButtonVisibility() {
            if (current == 0) 
                $("#btnPrevious").hide();
            else 
                $("#btnPrevious").show();
            }
        
        $("#btnNext")
            .click(function () {
                current++;
                if (current == 3) 
                    window.location.href = "/wallet/termsservice";
                
                checkPreviousButtonVisibility();
            });

        $("#btnPrevious").click(function () {
            current--;
            if (current == 0) {
                $("#main-splash").removeClass("hidden");
                $("#welcome-splash").addClass("hidden");
            }
            checkPreviousButtonVisibility();


            
        });

        /*qr code */

        //  function getURL() {    console.log(document.URL)    var str = document.URL
        //  var res = str.split("/");    console.log(res)    var a =
        // document.getElementById('camcanvas'); //or grab it by tagname etc    a.value
        // = res    var flashP = document.getElementById('embedflash'); //or grab it by
        // tagname etc    flashP.src = res    return res  }
        // $("#qrcode").click(function () {                  console.log(document.URL)
        //                 var str = document.URL                   var abc = str.slice(
        //                      str.lastIndexOf('/')                  );
        //   var res = str.split(abc);                   console.log(res)
        //
        //                window.location.href = res[0] + "#/send"           });

        $("#captureQRID").click(function () {
            captureToCanvas();
        });

        //Start here--->
        $("input[name='initialIdentifier']").click(function () {
            switch ($(this).attr("id")) {
                case "existingId":
                    $("input[name='initialIdentifierExisting']").attr("disabled", false);
                    break;
                case "newId":
                    $("input[name='initialIdentifierExisting']").attr("disabled", true);
                    break;
            }
        });

        $("button#setUpbtnNext").click(function () {
            switch ($("input[name='initialIdentifierExisting']:checked").attr("id")) {
                case "keyStoreFile":
                    $("#getInfoModal").modal("show");
                    break;
                case "newId":
                    $("input[name='initialIdentifierExisting']").attr("disabled", true);
                    $("#newAccountPopup").modal("show");
                    break;
                case "keyStoreText":
                    $("#getInfoModal").modal("show");
                    break;
            }
            switch ($("input[name='initialIdentifier']:checked").attr("id")) {

                case "newId":
                    $("input[name='initialIdentifierExisting']").attr("disabled", true);
                    $("#getInfoModal").modal("show");
                    break;

            }
        });


//

//


// $("#btnAdvOpt")
//            .click(function () {
//            $("input[name='initialIdentifierExisting']").attr("disabled", false);
//             $("#passPhrase").attr("checked", true)
//                            document
//                                .getElementById("passPhrase")
//                                .checked = true;
//                            $("#passphraseText").addClass("in");
//            })
//    });


/*$('input:radio[id="passPhrase"]').change(
    function(){
        if ($(this).is(':checked') && $(this).val() == 'on') {
             $("input[name='initialIdentifierExisting']").attr("disabled", false);
             $(".radio div").removeClass("in");

               $("#passPhrase").attr("checked", true)
                 document
                     .getElementById("passPhrase")
                     .checked = true;
                 $("#passphraseText").addClass("in");
        }
    });

    $('input:radio[id="privateKey"]').change(
        function(){
        if ($(this).is(':checked') && $(this).val() == 'on') {
             $("input[name='initialIdentifierExisting']").attr("disabled", false);
             $(".radio div").removeClass("in");

               $("#privateKey").attr("checked", true)
              document
                  .getElementById("privateKey")
                  .checked = true;
              $("#privateKeyText").addClass("in");
        }
    });

    $('input:radio[id="keyStoreText"]').change(
        function(){
        if ($(this).is(':checked') && $(this).val() == 'on') {
             $("input[name='initialIdentifierExisting']").attr("disabled", false);
             $(".radio div").removeClass("in");

                $("#keyStoreText").attr("checked", true)
                           document
                               .getElementById("keyStoreText")
                               .checked = true;
                           $("#jsonTxt").addClass("in");
        }
    });

    $('input:radio[id="keyStoreFile"]').change(
        function(){
            if (this.checked && this.value == 'on') {
              $("input[name='initialIdentifierExisting']").attr("disabled", false);
              $(".radio div").removeClass("in");

                 $("#keyStoreFile").attr("checked", true)
                            document
                                .getElementById("keyStoreFile")
                                .checked = true;

            }
    });


 $("#btnAdvOpt").click(function () {
            $("#btnAdvOpt").toggleClass("active")

            if ($("#btnAdvOpt").hasClass("active")) {

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
        });*/

    });
