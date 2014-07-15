package controllers

import _root_.cache.Test
import play.api._
import play.api.mvc._
import play.api.libs.json.{Writes, Json, JsValue}
import models.{DataDay, DataHour, Data, StubData}
import akka.actor.{Props, ActorSystem}
import actors.{StartCounting, WordCountMaster}


object Application extends Controller {



  def customers = Action {
    Ok(views.html.customer())
  }

  def listRun = Action {

    Ok(StubData.generateList)
  }


  def redis = Action {

    Test.runB
    Ok("redis")
  }

  def test = Action {
    val system = ActorSystem("word-count-system")
    val m = system.actorOf(Props[WordCountMaster], name="master")
    m ! StartCounting("d:\\test\\", 2)

    Ok("ok")
  }



}