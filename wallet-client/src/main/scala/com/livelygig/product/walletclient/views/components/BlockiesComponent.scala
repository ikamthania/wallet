/*
package com.livelygig.product.walletclient.views.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.querki.jquery._
import org.scalajs.dom.raw.HTMLCanvasElement

import scala.language.reflectiveCalls

object BlockiesComponent {

  case class Props(seed: String, color: String, spotcolor: String, bgcolor: String, size: Int, scale: Int)

  case class State(userProfileImg: HTMLCanvasElement)

  class Backend(t: BackendScope[Props, State]) {

    def mounted(props: Props, state: State): Callback = {
      val userProfileImg = Blockies.create(props.seed, props.color, props.spotcolor, props.bgcolor, props.size, props.scale)
      $("#userImg").html(s"<img src=${userProfileImg}>")
      //      t.modState(s => s.copy(userProfileImg = userProfileImg))
      Callback.empty
    }

    def render(props: Props, state: State) = {
      <.div(^.id := "userImg")()
    }
  }

  val component = ScalaComponent.builder[Props]("NewMessage")
    .initialState(State(Blockies.create("0x7cB57B5A97eAbe94205C07890BE4c1aD31E486A8", "#dfe", "#000", "#aaa", 15, 3)))
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props, scope.state))
    .build

  def apply(props: Props) = component(props)
}
*/
