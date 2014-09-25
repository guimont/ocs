package controllers

import play.api.mvc.{Action, Controller}
import stubInflux.{StubInflux, StubGeneratorStat, StubGenerator}

import scala.concurrent. Await
import akka.pattern.Patterns
import play.api.libs.json.{JsValue}
import actors.{Reader, FluxDispatcher}
import akka.util.Timeout
import scala.concurrent.duration.{FiniteDuration, Duration}


/**
 * Controller for influx database
 */
object Influx extends Controller {

  final val timeout: Timeout = new Timeout(Duration.apply("30 seconds").asInstanceOf[FiniteDuration])

  /**
   * run stubInflux generation
   * add stubInflux in influx database
   * @return
   */
  def stub = Action {
    StubGenerator.start
    StubGeneratorStat.start
    Ok("stubInflux")
  }

  /**
   * generate a full time range of stubInflux in influx database
   * warning: function very slow and need high cpu
   * @return
   */
  def init = Action {
    StubInflux.init("2014-09-01", 31)
    Ok("init")
  }


  /**
   * get run list from now - 31 days
   * @return
   */
  def listRun = Action {

    val read = Patterns.ask(FluxDispatcher.getDispatcher(),Reader(models.RequestHeader(0,"2014-09-01")),timeout)

    val rep = Await.result(read,timeout.duration)
    Ok(rep.asInstanceOf[JsValue])
  }

}

