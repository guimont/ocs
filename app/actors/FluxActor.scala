package actors

import akka.actor.Actor
import akka.actor.Props
import models.{RequestHeader, Message}
import akka.routing.SmallestMailboxRouter
import influxdb.Connection


case class Start()
case class Writer(mess: Message)
case class Reader(rh: RequestHeader)

/**
 * Worker to execute asynchronous order
 */
class FluxActorWorker extends Actor {

  val connector =  new Connection

  def receive = {
    case Writer(mess) =>  connector.addMessage(mess)
    case Reader(request) =>  sender ! (connector.getData(request))
  }
  override def postStop(): Unit = {
    println(s"Worker actor is stopped: ${self}")
  }
}

/**
 *  Actor to dispatch asynchronous order
 */
class FluxActorMaster extends Actor {

  val workers = createWorkers(5);

  def receive = {
    case Start() =>
      println("Akka Started")

    case Writer(mess) =>
      workers ! Writer(mess)
    case Reader(mess) =>
      workers .! (Reader(mess)) (sender)
  }


  /**
   * Stop akka worker
   */
  override def postStop(): Unit = {
    println(s"Master actor is stopped: ${self}")
  }


  /**
   * create workers
   * @param numActors (number of workers)
   * @return
   */
  private def createWorkers(numActors: Int) = {
    context.actorOf(Props[FluxActorWorker].withRouter(SmallestMailboxRouter(numActors)))
  }

}