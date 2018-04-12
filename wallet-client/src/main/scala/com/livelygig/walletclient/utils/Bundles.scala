package com.livelygig.walletclient.utils

import com.livelygig.walletclient.facades.ReactFacade
import com.livelygig.walletclient.facades.bootstrap.BootstrapBundle
import com.livelygig.walletclient.facades.bootstrapvalidator.ValidatorBundle
import com.livelygig.walletclient.facades.jquery.JQueryFacade

trait Bundles {
  ReactFacade.React
  ReactFacade.ReactDOM
  JQueryFacade.useNpmImport()
  ValidatorBundle.useNpmImports()
  BootstrapBundle.useNpmImports()
}
