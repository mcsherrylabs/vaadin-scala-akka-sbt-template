package sss.ui

import akka.actor.ActorSystem
import sss.ui.template.{ KickedOut, BroadcastMessage }

object BroadcastMain {

  def main(args: Array[String]) {

    implicit val system = ActorSystem("LocalSystem")
    try {
      val remote = system.actorSelection("akka.tcp://default@127.0.0.1:2567/user/uiReactorBroadcastEndpoint")

      args match {
        case Array("msg", msg) => remote ! BroadcastMessage(msg)
        case Array("logout", msg) => remote ! KickedOut(msg)
        case _ => println("Usage: msg <some text> OR logout <some text>")
      }
      Thread.sleep(500)
    } finally system.terminate()
  }
}
