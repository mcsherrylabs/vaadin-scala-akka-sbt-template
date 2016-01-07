package sss.ui.reactor

import com.vaadin.ui.UI
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Terminated

import scala.util.{ Success, Failure, Try }

abstract class UIEventActor extends Actor with ActorLogging {

  final def receive = setup

  private def setup: Receive = {
    case UIEventActorSetup(reactor: ActorRef, eventBroadcastEndpoint: ActorRef, ui: UI) =>
      context.become(commonHandler(reactor, eventBroadcastEndpoint, ui) orElse react(reactor, eventBroadcastEndpoint, ui))

    case x => log.warning(s"Not set up yet, cannot deal with $x")

  }

  private def commonHandler(reactor: ActorRef, eventBroadcastEndpoint: ActorRef, ui: UI): Receive = {

    case l: ListenTo => reactor ! l
    case s: StopListeningTo => reactor ! s

    // TODO remove this to prevent other Actors sending closures and breaking encapsulation.
    case Push(f) => ui.access(new Runnable {
      def run = Try { f() } match {
        case Failure(x) => log.error(x, "Failed to push changes to ui!")
        case Success(_) =>
      }
    })

    case r @ Register(eventCategory) => eventBroadcastEndpoint ! r
    case r @ UnRegister(eventCategory) => eventBroadcastEndpoint ! r
    case Detach =>
      reactor ! Detach
      eventBroadcastEndpoint ! Detach
      context.become(setup)
  }

  def push(f: => Unit) = self ! Push(f _)

  def react(reactor: ActorRef, broadcaster: ActorRef, ui: UI): Receive

}
