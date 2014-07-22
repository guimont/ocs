package controllers

import play.api.mvc.{Action, Controller}
import actors.StubGenerator
import influxdb.Connector


object Influx extends Controller {

  def stub = Action {
    StubGenerator
    Ok("stub")
  }

  def get = Action {

    Connector.getJobRun("2014-07-17","2014-07-17")

    Ok("get")
  }
}
