package com.livelygig.product.walletclient.facades.bootstrapvalidator

import org.scalajs.jquery.JQuery

trait ValidatorJQueryImplicits {
  implicit def implicitBootstrapJQuery(jq: JQuery): ValidatorJQuery = {
    jq.asInstanceOf[ValidatorJQuery]
  }
}
