package sss.ui.reactor

import com.vaadin.ui.{ Component, UI }
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import java.util.UUID
import scala.language.implicitConversions
import scala.language.dynamics
import sss.ancillary.ReflectionUtils
import scala.reflect.runtime.universe._

object UIReactor extends ReactorActorSystem {

  lazy val eventBroadcastActorRef = actorSystem.actorOf(Props(classOf[EventBroadcastActor]), "uiReactorBroadcastEndpoint")

  def apply(ui: UI): UIReactor = apply(ui, UUID.randomUUID().toString)

  def apply(ui: UI, sessionId: String): UIReactor = {
    new UIReactor(actorSystem, ui, sessionId, eventBroadcastActorRef)
  }

}

class UIReactor(system: ActorSystem, ui: UI, sessionId: String, eventBroadcastEndpoint: => ActorRef) {

  private lazy val reactor = system.actorOf(Props(classOf[UIReactorActor]), s"uiReactor-${sessionId}")

  def createListener[I: TypeTag]: I = {
    ReflectionUtils.createProxy[I]((obj, method, args) =>
      if (args != null && args.length > 0 && args(0).isInstanceOf[Component.Event]) {
        fireEvent(args(0).asInstanceOf[Component.Event])
        null
      } else {
        method.invoke(this, args: _*)
      })
  }

  private def fireEvent(ev: Component.Event) = reactor ! ev

  def actorOf(props: Props, componentsToListenTo: Component*): ActorRef = {
    val ref = system.actorOf(props)
    ref ! UIEventActorSetup(reactor, eventBroadcastEndpoint, ui)
    componentsToListenTo foreach {
      ref ! ListenTo(_)
    }
    ref
  }

  def !(ev: Event) = broadcast(ev)

  def broadcast(ev: Event): Unit = eventBroadcastEndpoint ! ev

}
