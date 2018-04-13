# Client

This is a [scalaj-react](https://github.com/japgolly/scalajs-react) application. Which uses [Diode](https://github.com/suzaku-io/diode) for immutable model structure for the application state.
A very good resource to understand how these interact with each other and in general scalajs setup [scalajs-spa-tutorial](https://ochrons.github.io/scalajs-spa-tutorial/en/) is a recommended read.

Most of the bussines logic in this application is client side. The data is stored in local storage. The `LocalStorageApi.scala` files contains the method for keeping the root model state in sync with local storage. 

Essentially there is a subscription when the application is started which listen on root model changes
  ``` scala 
  def subscribeToAppRootChanges() = {
    updateRootModelFromStorage()
    WalletCircuit.subscribe(WalletCircuit.zoomTo(_.appRootModel)) { modelRO =>
      updateModelInLocalStorage()
    }
  }
  ```
  
  Any action which updates the state of root model is then automatically stored in local storage.

TODO Add details
