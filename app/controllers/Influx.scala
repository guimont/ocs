package controllers

import play.api.mvc.{Action, Controller}
import actors.StubGenerator


object Influx extends Controller {

  def stub = Action {
    StubGenerator
    Ok("stub")
  }


}
