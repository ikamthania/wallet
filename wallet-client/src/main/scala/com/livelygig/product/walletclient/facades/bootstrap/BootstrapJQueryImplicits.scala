package com.livelygig.product.walletclient.facades.bootstrap

import org.scalajs.jquery.JQuery
import com.definitelyscala.bootstrap.{ JQuery => BootstrapJQuery }

trait BootstrapJQueryImplicits {
  implicit def implicitBootstrapJQuery(jq: JQuery): BootstrapJQuery = {
    jq.asInstanceOf[BootstrapJQuery]
  }
}
