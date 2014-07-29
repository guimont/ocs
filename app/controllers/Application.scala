package controllers

import _root_.cache.Test
import play.api._
import play.api.mvc._
import play.api.libs.json.{JsValue, Writes, Json}
import models.{DataDay, DataHour, Data, DataS}
import akka.actor.{Props, ActorSystem}
import actors.{Reader, FluxDispatcher}
import scala.concurrent. Await
import scala.concurrent.duration.{FiniteDuration, Duration}
import akka.pattern.Patterns
import akka.util.Timeout


object Application extends Controller {
  final val timeout: Timeout = new Timeout(Duration.apply("30 seconds").asInstanceOf[FiniteDuration])


  def customers = Action {
    Ok(views.html.customer())
  }

  def listRun = Action {

    val readed = Patterns.ask(FluxDispatcher.getDispatcher(),Reader("2014-07-01"),timeout)

    val rep = Await.result(readed,timeout.duration)
    Ok(rep.asInstanceOf[JsValue])
  }


  def redis = Action {

    Test.runB
    Ok("redis")
  }

  def test = Action {

    Ok("ok")
  }



}