package actors

import akka.actor.{Props, ActorSystem, Cancellable, Actor}
import scala.util.Random
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source


/**
 * Schedule thread to ping rul each 5 minutes for heroku
 */
object RefreshPageAuto {
  val Tick = "tick"
  class TickActor extends Actor {
    def receive = {
      case Tick =>  {
        Source.fromURL("http://guimont.herokuapp.com/ping") //ugly =< FIND ABSOLUTE URL be better
      }
    }
  }

  def stubValue(i:Int):Int = {Math.abs(new Random().nextInt(i))}

  var cancellable: Cancellable = null

  def start {
    val system = ActorSystem("MySystemRefresh")
    val actor = system.actorOf(Props(new TickActor), name = "actor")
    cancellable = system.scheduler.schedule(0 seconds, 5 minutes, actor, Tick)
  }


  def stop {
    //This cancels further Ticks to be sent
    cancellable.cancel()
  }

}
