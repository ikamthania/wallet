package com.livelygig.product.walletclient.views

import com.livelygig.product.walletclient.rootmodel.ERCTokenRootModel
import com.livelygig.product.walletclient.router.ApplicationRouter.Loc
import com.livelygig.product.walletclient.services.WalletCircuit
import diode.data.Pot
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._

object LandingView {

  case class Props(proxy: ModelProxy[Pot[ERCTokenRootModel]], router: RouterCtl[Loc])

  final case class State()

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {

      /*if (dom.window.localStorage.getItem("pubKey") == null) {
        t.props.flatMap(_.router.set(AccountLoc)) >> t.props.flatMap(_.router.refresh)
      } else {
        Callback.empty
      }*/
      Callback.empty
    }

    def render(p: Props, s: State): VdomElement = {
      if (!WalletCircuit.zoom(_.user.isloggedIn).value) {
        StaticLandingView.component()
      } else {
        AccountView.component(AccountView.Props(t.props.runNow().proxy, t.props.runNow().router))
      }
    }

  }

  val component = ScalaComponent.builder[Props]("LandingView")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.componentDidMount(scope.props))
    .build

  def apply(props: Props) = component(props)

}
