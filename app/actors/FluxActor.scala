package actors

import akka.actor.Actor
import akka.actor.Props
import models.Message
import akka.routing.SmallestMailboxRouter
import influxdb.Connection
import play.api.libs.json.JsValue


case class Start()
case class Writer(mess: Message)
case class Reader(date: String)

class FluxActorWorker extends Actor {

  val connector =  new Connection

  def receive = {
    case Writer(mess) =>  connector.addMessage(mess)
    case Reader(date) =>  {
      val test = connector.getJobRun(date,"")
      sender ! (test)
    }
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
      workers ! (Writer(mess))(self)
    case Reader(mess) =>
      workers.tell(Reader(mess),sender)
  }


  override def postStop(): Unit = {
    println(s"Master actor is stopped: ${self}")
  }


  private def createWorkers(numActors: Int) = {
    context.actorOf(Props[FluxActorWorker].withRouter(SmallestMailboxRouter(numActors)))
  }

}