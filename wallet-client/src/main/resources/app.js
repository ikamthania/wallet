window.addEventListener('load',function() {
    if (typeof web3 != 'undefined') {
          if(!web3.isConnected()) {
                console.log("Web3 not Connected")
                document.getElementById("connectedToEth").style.display = "none" ;
                document.getElementById("notConnectedToEth").style.display = "block" ;
                document.getElementById("toggleOnConnection").style.display = "none" ;
                 // show some dialog to ask the user to start a node
          } else {
               console.log("Web3 connected");
               document.getElementById("connectedToEth").style.display = "block" ;
               document.getElementById("notConnectedToEth").style.display = "none" ;
              document.getElementById("connectionID").innerHTML  = "CONNECTED";
              document.getElementById("nodeAddressID").innerHTML  = web3.eth.accounts;             // start web3 filters, calls, etc
          }

    } else {
      // set the provider you want from Web3.providers
       document.getElementById("connectedToEth").style.display = "none" ;
       document.getElementById("notConnectedToEth").style.display = "block" ;
         document.getElementById("toggleOnConnection").style.display = "none" ;
    }
})