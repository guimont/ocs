package actors

import akka.actor.{ActorSystem, Actor, Props}
import scala.concurrent.duration._
import play.api.libs.concurrent.Akka
import influxdb.Connector
import models.{JobRun, IdentEngine, IdentCustomer, Message}
import cache.MemoryCache
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global



object StubGenerator {

  var counter=0
  val Tick = "tick"
  class TickActor extends Actor {
    def receive = {
      case Tick => Connector.addJobRun(StubGenerator.generateFakeMess)
    }
  }

  val system = ActorSystem("MySystem")
  val actor = system.actorOf(Props(new TickActor), name = "actor")
  val cancellable = system.scheduler.schedule(0 seconds, 1 seconds, actor, Tick)

  //This cancels further Ticks to be sent
  //cancellable.cancel()

  def generateFakeMess():Message = {
    val idc = IdentCustomer(1,"orange","parisorprod","ORPR60","orangeprod")
    //val idC =MemoryCache.getId(idTest.toString)
    val ide = IdentEngine(1, 'I', 'X')
    val job = JobRun('T',counter)
    counter+=1
    Message(idc,ide,job)
  }

}




