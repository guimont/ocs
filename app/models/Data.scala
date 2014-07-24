package models

import play.api.libs.json.{JsValue, Json}
import scala.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 04/06/14
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
case class Data (run: Int, cpu: Int, memory: Int)
case class DataHour(id: Long, hour: Int, data: Data)
case class DataDay(id: Long, day: String,list: List[DataHour], dataDay: Data)
case class DataDaySimple(id: Long, day: String, dataDay: Data)
case class DataMonth(id: Long, month: String, list: List[DataDay])
case class DataMonthSimple(id: Long, month: String, list: List[DataDaySimple])


object DataS {

  def stubValue(i:Int):Int = {Math.abs(new Random().nextInt()%i)}

  def generateStubList : JsValue  = {

    var list = List(DataHour(0,0,Data(stubValue(16),stubValue(10),stubValue(100))))
    for(i <- 1 to 23) {
      list = list ::: List(DataHour(i,i,Data(stubValue(16),stubValue(10),stubValue(100))))
    }


    var listM = List(DataDay(1,"04/1",list,Data(stubValue(16),stubValue(10),stubValue(100))))

    for (i <- 2 to 31) {
      listM = listM ::: List(DataDay(i,"04/"+i,list,Data(stubValue(16),stubValue(10),stubValue(100))))
    }

    return Json.toJson(DataMonth(1,"april",listM));
  }

  def serialize(data: DataMonthSimple) :JsValue = {

    Json.toJson(data)
  }


  /**
   * Alternative JSON formatter
   */
  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  implicit val dataWrites: Writes[Data] = (
    (JsPath \ "run").write[Int] and
      (JsPath \ "cpu").write[Int] and
      (JsPath \ "memory").write[Int]
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