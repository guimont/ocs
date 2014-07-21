package actors

import akka.actor.Actor
import akka.actor.Props
import models.Message
import akka.routing.SmallestMailboxRouter
import influxdb.Connector


case class Start()
case class Writer(mess: Message)
case class Reader()

class FluxActorWorker extends Actor {

  def receive = {
    case Writer(mess) =>  Connector.addMessage(mess)
  }
  override def postStop(): Unit = {
    println(s"Worker actor is stopped: ${self}")
  }
}


class FluxActorMaster extends Actor {

  val workers = createWorkers(5);

  def receive = {
    case Start() =>
      println("Akka Started")

    case Writer(mess) =>
      workers ! Writer(mess)
  }


  override def postStop(): Unit = {
    println(s"Master actor is stopped: ${self}")
  }


  private def createWorkers(numActors: Int) = {
    context.actorOf(Props[FluxActorWorker].withRouter(SmallestMailboxRouter(numActors)))
  }

}