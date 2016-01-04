package sss.ui

import akka.actor.ActorRef
import com.vaadin.data.Property.ValueChangeListener
import com.vaadin.event.FieldEvents.BlurListener
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.{ Component, UI }

/**
 *
 */
package object reactor {

  import scala.language.implicitConversions
  implicit def conv(uiReactor: UIReactor): BlurListener = uiReactor.createListener[BlurListener]

  implicit def conv2(uiReactor: UIReactor): ClickListener = uiReactor.createListener[ClickListener]

  implicit def conv3(uiReactor: UIReactor): ValueChangeListener = uiReactor.createListener[ValueChangeListener]

  case class ListenTo(component: Component)
  case class StopListeningTo(component: Component)
  case class Register(eventCategory: String)
  case class UnRegister(eventCategory: String)
  case object Detach
  case class ComponentEvent(component: Component, ev: Component.Event)

  /**
   * Only Events may be broadcast/received.
   */
  trait Event {
    val category: String
  }

  private[reactor] case class UIEventActorSetup(reactor: ActorRef, eventBroadcastEndpoint: ActorRef, ui: UI)
  private[reactor] case class Push(f: () => Unit)

}
