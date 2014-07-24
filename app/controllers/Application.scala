package controllers

import _root_.cache.Test
import play.api._
import play.api.mvc._
import play.api.libs.json.{Writes, Json, JsValue}
import models.{DataDay, DataHour, Data, DataS}
import akka.actor.{Props, ActorSystem}
import influxdb.Connector


object Application extends Controller {



  def customers = Action {
    Ok(views.html.customer())
  }

  def listRun = Action {

    Ok(Connector.getJobRun("2014-07-01","2014-07-31"))
    //Ok(DataS.generateStubList)
  }


  def redis = Action {

    Test.runB
    Ok("redis")
  }

  def test = Action {

    Ok("ok")
  }



}