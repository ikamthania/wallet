package com.livelygig.walletclient.services

import org.scalajs.dom
import org.scalajs.dom.experimental.serviceworkers._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }
import scala.scalajs.js
import scala.scalajs.js.annotation.{ JSExportTopLevel }

@JSExportTopLevel("ServiceWorker")
object ServiceWorker {
  def serviceWorker = "/wallet/serviceworker.js"

  def init(): Unit = {
    if (!js.isUndefined(dom.window.navigator.serviceWorker)) {
      dom.window.navigator.serviceWorker.register(serviceWorker).toFuture.onComplete {
        case Success(reg) => println(s"Service worker registration succeeded: $reg")
        case Failure(err) => println(s"Service worker registration failed: $err")
      }
    } else println("Service workers not available.")
  }
}
