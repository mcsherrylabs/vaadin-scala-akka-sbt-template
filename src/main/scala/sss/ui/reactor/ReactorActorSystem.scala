package sss.ui.reactor

import akka.actor.ActorSystem
import scala.concurrent.Await
import scala.concurrent.duration.Duration

trait ReactorActorSystem {
  lazy val actorSystem = ReactorActorSystem.actorSystem
  def terminate = ReactorActorSystem.terminate
  def blockingTerminate = ReactorActorSystem.blockingTerminate
}

object ReactorActorSystem {
  lazy val actorSystem = ActorSystem()
  def terminate = actorSystem.terminate
  def blockingTerminate = Await.result(actorSystem.terminate(), Duration.Inf)

}