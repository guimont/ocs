package controllers

import play.api.mvc.{Action, Controller}
import influxdb.{Connector}
import stub.{StubInflux, StubGeneratorStat, StubGenerator}


object Influx extends Controller {

  def stub = Action {
    StubGenerator.start
    StubGeneratorStat.start
    Ok("stub")
  }

  def init = Action {
    StubInflux.init("2014-07-01", 28)
    Ok("init")
  }

  def get = Action {

    Connector.getJobRun("2014-07-19","2014-07-17")

    Ok("get")
  }
}
