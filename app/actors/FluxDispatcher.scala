package actors

import akka.actor.{Props, ActorSystem}
import models.Message

object FluxDispatcher {

  val system = ActorSystem("send-system")
  val m = system.actorOf(Props[FluxActorMaster], name="dispatcher")
  m ! Start

  def send(mess: Message) {
    m ! Writer(mess)
  }
}
