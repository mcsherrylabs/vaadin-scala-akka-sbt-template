package sss.ui.reactor

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef
import com.vaadin.ui.Component

private class UIReactorActor extends Actor with ActorLogging {

  private var eventCount = 0

  final def receive = routing(Map().withDefaultValue(Set()))

  def routing(listeners: Map[Component, Set[ActorRef]]): Receive = {

    case ev: Component.Event => {
      eventCount += 1
      if (eventCount < 10 || eventCount % 100 == 0) log.debug(s"Event count - $eventCount")
      listeners.get(ev.getComponent) map {
        _ map (_ ! ComponentEvent(ev.getComponent, ev))
      }
    }

    case ListenTo(component) => {

      log.debug(s"Registration for ${component}")
      val newRegistrant = sender()
      val currentRegistered = listeners(component)
      context.become(routing(listeners + (component -> (currentRegistered + newRegistrant))))
    }

    case StopListeningTo(component) => {
      log.debug(s"component ${component} losing registrant ")
      val registrant = sender()
      val newRegistrantList = listeners(component).filterNot(_ == registrant)
      context.become(routing(listeners + (component -> (newRegistrantList))))
    }

    case Detach =>
      val registrant = sender()
      val newRegistrantMap = listeners map {
        case (componentId, registrantSeq) => (componentId, registrantSeq.filterNot(_ == registrant))
      }
      context.become(routing(newRegistrantMap))

  }
}