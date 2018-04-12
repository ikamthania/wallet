package com.livelygig.walletclient.facades.bootstrap

import com.definitelyscala.bootstrap.{ JQuery => BootstrapJQuery }
import org.scalajs.jquery.JQuery

trait BootstrapJQueryImplicits {
  implicit def implicitBootstrapJQuery(jq: JQuery): BootstrapJQuery = {
    jq.asInstanceOf[BootstrapJQuery]
  }
}
