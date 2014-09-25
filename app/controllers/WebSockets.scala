package controllers

import akka.util.Timeout
import scala.concurrent.duration.{FiniteDuration, Duration}
import play.api.mvc.WebSocket
import play.api.libs.json.JsValue
import org.joda.time.DateTime
import akka.pattern.Patterns
import actors.{Reader, FluxDispatcher}
import scala.concurrent.Await
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.libs.concurrent.Promise
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 09/09/14
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
object WebSockets {
  final val timeout: Timeout = new Timeout(Duration.apply("30 seconds").asInstanceOf[FiniteDuration])


  /**
   * send event stub
   * @return
   */
  def eventStubFeed() = WebSocket.using[JsValue] { implicit request =>

    def getJobRun = {
      null.asInstanceOf[JsValue]
    }

    val in = Iteratee.ignore[JsValue]
    val out = Enumerator.generateM {
      Promise.timeout(Some(getJobRun), Duration(30, "seconds"))
    }

    (in, out)
  }


  /**
   * Send stub stat each 30 seconds
   * @return
   */
  def statusStubFeed() = WebSocket.using[JsValue] { implicit request =>

    def getJobRun = {
      null.asInstanceOf[JsValue]
    }

    val in = Iteratee.ignore[JsValue]
    val out = Enumerator.generateM {
      Promise.timeout(Some(getJobRun), Duration(30, "seconds"))
    }

    (in, out)
  }


  /**
   * Send stat each 30 seconds
   * @return
   */
  def statusFeed() = WebSocket.using[JsValue] { implicit request =>

    def getJobRun = {
      val now = new DateTime()
      val date = now.year().get().toString+"-"+"%02d".format(now.monthOfYear().get())+"-"+"%02d".format(now.dayOfMonth().get())
      val read = Patterns.ask(FluxDispatcher.getDispatcher(),Reader(models.RequestHeader(1,date)),timeout)

      val rep = Await.result(read,timeout.duration)
      rep.asInstanceOf[JsValue]
    }

    val in = Iteratee.ignore[JsValue]
    val out = Enumerator.generateM {
      Promise.timeout(Some(getJobRun), Duration(30, "seconds"))
    }

    (in, out)
  }

}
