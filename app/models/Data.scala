package models

import play.api.libs.json.{JsValue, Json}
import scala.util.Random
import scala.collection.mutable.ListBuffer
import org.joda.time.DateTime

/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 04/06/14
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
case class DataRun (terminate: Int, aborted: Int, timeover: Int, waitevent: Int, global: Int = 0) {
  def aglomerate() = this.copy(global = this.terminate + this.aborted+ + this.timeover +this.waitevent)
}
case class Data (run: DataRun, cpu: Double, memory: Double)
case class DataHour(id: Long, hour: Int, data: Data)
case class DataDay(id: Long, day: String,list: List[DataHour], dataDay: Data)
case class DataDaySimple(id: Long, day: String, dataDay: Data)
case class DataMonth(id: Long, month: String, list: List[DataDay])
case class DataMonthSimple(id: Long, month: String, list: List[DataDaySimple])


object DataS {

  def stubValue(i:Int):Int = {Math.abs(new Random().nextInt(i))}

  def generateStubList : JsValue  = {

    val now = new DateTime()
    val date = "%02d".format(now.monthOfYear().get())+"-"+"%02d".format(now.dayOfMonth().get())

    var listD  = new ListBuffer[DataDaySimple]()
    for (i <- 1 to 31) {
      listD += DataDaySimple(i,"%02d".format(now.monthOfYear().get())+"-"+"%02d".format(i),Data(DataRun(stubValue(10000),stubValue(500),stubValue(500),stubValue(500)) aglomerate,stubValue(10),stubValue(i*10)+100))
    }

    Json.toJson(DataMonthSimple(1,date,listD.toList))
  }

  def serialize(data: DataMonthSimple) :JsValue = {

    Json.toJson(data)
  }

  def serialize(data: DataDaySimple) :JsValue = {

    Json.toJson(data)
  }

  /**
   * Alternative JSON formatter
   */
  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  implicit val dataRunWrites: Writes[DataRun] = (
    (JsPath \ "terminate").write[Int] and
      (JsPath \ "aborted").write[Int] and
      (JsPath \ "timeover").write[Int] and
      (JsPath \ "waitevent").write[Int] and
      (JsPath \ "global").write[Int]
    )(unlift(DataRun.unapply))

  implicit val dataWrites: Writes[Data] = (
    (JsPath \ "run").write[DataRun] and
      (JsPath \ "cpu").write[Double] and
      (JsPath \ "memory").write[Double]
    )(unlift(Data.unapply))

  implicit val dataHourWrites: Writes[DataHour] = (
    (JsPath \ "id").write[Long] and
      (JsPath \ "hour").write[Int] and
      (JsPath \ "data").write[Data]
    )(unlift(DataHour.unapply))

  implicit val dataDayWrites: Writes[DataDay] = (
    (JsPath \ "id").write[Long] and
      (JsPath \ "day").write[String] and
      (JsPath \ "list").write[List[DataHour]] and
      (JsPath \ "dataDay").write[Data]
    )(unlift(DataDay.unapply))

  implicit val dataDaySimpleWrites: Writes[DataDaySimple] = (
    (JsPath \ "id").write[Long] and
      (JsPath \ "day").write[String] and
      (JsPath \ "dataDay").write[Data]
    )(unlift(DataDaySimple.unapply))

  implicit val dataMonthWrites: Writes[DataMonth] = (
    (JsPath \ "id").write[Long] and
      (JsPath \ "month").write[String] and
      (JsPath \ "list").write[List[DataDay]]
    )(unlift(DataMonth.unapply))


  implicit val dataMonthSimpleWrites: Writes[DataMonthSimple] = (
    (JsPath \ "id").write[Long] and
      (JsPath \ "month").write[String] and
      (JsPath \ "list").write[List[DataDaySimple]]
    )(unlift(DataMonthSimple.unapply))


}