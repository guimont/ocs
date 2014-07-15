package actors

import akka.actor.Actor
import akka.actor.Props
import scala.concurrent.duration._
import play.api.libs.concurrent.Akka
import influxdb.Connector
import models.{JobRun, IdentEngine, IdentCustomer, Message}
import cache.MemoryCache


object StubGenerator {

  val Tick = "tick"
  class TickActor extends Actor {
    def receive = {
      case Tick => Connector.addJobRun(StubGenerator.generateFakeMess)
    }
  }

  val tickActor = Akka.system.actorOf(Props(classOf[TickActor], this))
  //Use system's dispatcher as ExecutionContext


  //This will schedule to send the Tick-message
  //to the tickActor after 0ms repeating every 50ms
  val cancellable =
    Akka.system.scheduler.schedule(0 milliseconds,
      50 milliseconds,
      tickActor,
      Tick)

  //This cancels further Ticks to be sent
  cancellable.cancel()

  def generateFakeMess():Message = {
    val idc = IdentCustomer(0,"orange","parisorprod","ORPR60","orangeprod")
    //val idC =MemoryCache.getId(idTest.toString)
    val ide = IdentEngine(0, 'I', 'X')
    val job = JobRun('T',0)

    Message(idc,ide,job)
  }

}




