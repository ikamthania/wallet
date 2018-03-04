package com.livelygig.product.walletclient.utils

import com.livelygig.product.walletclient.facades.ReactFacade
import com.livelygig.product.walletclient.facades.bootstrap.BootstrapBundle
import com.livelygig.product.walletclient.facades.bootstrapvalidator.ValidatorBundle
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade

trait Bundles {
  ReactFacade.React
  ReactFacade.ReactDOM
  JQueryFacade.useNpmImport()
  ValidatorBundle.useNpmImports()
  BootstrapBundle.useNpmImports()
}
