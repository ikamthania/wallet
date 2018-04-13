package com.livelygig.walletclient.views

import com.livelygig.walletclient.rootmodel.TokenDetailsRootModel
import com.livelygig.walletclient.router.ApplicationRouter.Loc
import com.livelygig.walletclient.services.WalletCircuit
import diode.data.Pot
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._

object LandingView {

  case class Props(proxy: ModelProxy[Pot[TokenDetailsRootModel]], router: RouterCtl[Loc])

  final case class State()

  final class Backend(t: BackendScope[Props, State]) {

    def componentDidMount(props: Props): Callback = {
      Callback.empty
    }

    def render(p: Props, s: State): VdomElement = {
      if (WalletCircuit.zoomTo(_.appRootModel.appModel.data.keyrings.vault.data).value == "") {
        StaticLandingView.component()
      } else {
        EnterPasswordView.component(EnterPasswordView.Props(p.router))
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
