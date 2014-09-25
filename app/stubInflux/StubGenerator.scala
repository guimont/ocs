package stubInflux

import akka.actor.{Cancellable, ActorSystem, Actor, Props}
import scala.concurrent.duration._
import play.api.libs.concurrent.Akka
import models._
import cache.MemoryCache
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global
import models.JobRun
import models.IdentEngine
import models.Message
import models.IdentCustomer
import scala.util.Random
import actors.FluxDispatcher


object StubGenerator {

  var counter=0
  val Tick = "tick"
  class TickActor extends Actor {
    def receive = {
      case Tick => FluxDispatcher.send(StubGenerator.generateFakeRunMess)
    }
  }

  def stubValue(i:Int):Int = {Math.abs(new Random().nextInt(i))}

  var cancellable: Cancellable = null

  def start {
    val system = ActorSystem("MySystem")
    val actor = system.actorOf(Props(new TickActor), name = "actor")
    cancellable = system.scheduler.schedule(0 seconds, 5 seconds, actor, Tick)
  }


  //cancellable.cancel()

  def generateFakeRunMess():Message = {
    if (stubValue(100) <30) return null //simulate no message

    val idc = IdentCustomer(1,"orange","parisorprod","ORPR60","orangeprod")
    val ide = IdentEngine(1, 'I', 'X')
    val job = JobRun('T',counter)
    counter+=1
    Message(idc,ide,job)
  }

  def stop {
    //This cancels further Ticks to be sent
    cancellable.cancel()
  }

}


object StubGeneratorStat {

  def stubValue(i:Int):Int = {Math.abs(new Random().nextInt(i))}

  val Tick = "tick"
  class TickActor extends Actor {
    def receive = {
      case Tick => FluxDispatcher.send(StubGeneratorStat.generateFakeStatMess)
    }
  }

  var cancellable: Cancellable = null
  def start {
    val system = ActorSystem("MySystem")
    val actor = system.actorOf(Props(new TickActor), name = "actor")
    cancellable = system.scheduler.schedule(0 seconds, 20 seconds, actor, Tick)
  }


  //cancellable.cancel()

  def generateFakeStatMess():Message = {
    val idc = IdentCustomer(1,"orange","parisorprod","ORPR60","orangeprod")
    val ide = IdentEngine(1, 'I', 'X')
    val stat = Stat(stubValue(20),stubValue(100),stubValue(100),stubValue(100))
    Message(idc,ide,stat)
  }

  def stop {
    //This cancels further Ticks to be sent
    cancellable.cancel()
  }

}



