package actors

import akka.actor.{ActorRef, Props, ActorSystem}
import models.{RequestHeader, Message}


/**
 * Object to use dispatcher
 */
object FluxDispatcher {

  val system = ActorSystem("send-system")
  val m = system.actorOf(Props[FluxActorMaster], name="dispatcher")
  m ! Start

  /**
   * request to write in database
   * @param mess
   */
  def send(mess: Message) {
    m ! Writer(mess)
  }

  /**
   * request to read in database
   * @param rh
   */
  def read(rh: RequestHeader) {
    m ! Reader(rh)
  }

  /**
   *
   * @return instance of singleton
   */
  def getDispatcher() : ActorRef = {
    m
  }
}
