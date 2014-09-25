package models
import play.api.libs.json.{JsValue, Json}
import scala.collection.mutable.ListBuffer
import org.joda.time.DateTime


case class Event(typeEvent: String, log: String, date: String)

/**
 * Alternative JSON formatter
 */

/*
"Disk space warning"
"Disk full error"
"Thread pool max reached"
"Cache limit reached"
"Authentication problem"
"License invalid"
"Synchronization failed"
"Manager SAP not reachable"
"Manager HP not reachable"
"Manager Oracle not reachable"
""
*/



object EventS {


  def generateStubList : List[Event]  = {

    val now = new DateTime()
    var listD  = new ListBuffer[Event]()

    listD += Event("fa-database","Disk space warning" , "%02d".format(now.minusHours(336).monthOfYear().get())+"-"+"%02d".format(now.minusHours(336).dayOfMonth().get()));
    listD += Event("fa-plug","Manager SAP not reachable" , "%02d".format(now.minusHours(306).monthOfYear().get())+"-"+"%02d".format(now.minusHours(306).dayOfMonth().get()));
    listD += Event("fa-wifi","Synchronization failed" , "%02d".format(now.minusHours(262).monthOfYear().get())+"-"+"%02d".format(now.minusHours(262).dayOfMonth().get()));
    listD += Event("fa-database","Disk space warning" , "%02d".format(now.minusHours(200).monthOfYear().get())+"-"+"%02d".format(now.minusHours(200).dayOfMonth().get()));
    listD += Event("fa-database","Disk space warning" , "%02d".format(now.minusHours(192).monthOfYear().get())+"-"+"%02d".format(now.minusHours(192).dayOfMonth().get()));
    listD += Event("fa-wifi","Manager Oracle not reachable" , "%02d".format(now.minusHours(100).monthOfYear().get())+"-"+"%02d".format(now.minusHours(100).dayOfMonth().get()));
    listD += Event("fa-database","Thread pool max reached" , "%02d".format(now.minusHours(50).monthOfYear().get())+"-"+"%02d".format(now.minusHours(50).dayOfMonth().get()));
    listD += Event("fa-database","Thread pool max reached" , "%02d".format(now.minusHours(40).monthOfYear().get())+"-"+"%02d".format(now.minusHours(40).dayOfMonth().get()));


    listD.toList
  }



  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  implicit val eventWrites: Writes[Event] = (
    (JsPath \ "typeEvent").write[String] and
      (JsPath \ "log").write[String] and
      (JsPath \ "date").write[String]
    )(unlift(Event.unapply))

}