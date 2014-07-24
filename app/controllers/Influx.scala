package controllers

import play.api.mvc.{Action, Controller}
import actors.{StubGeneratorStat, StubGenerator}
import influxdb.Connector


object Influx extends Controller {

  def stub = Action {
    StubGenerator
    StubGeneratorStat
    Ok("stub")
  }

  def get = Action {

    Connector.getJobRun("2014-07-19","2014-07-17")

    Ok("get")
  }
}
