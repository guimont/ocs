package influxdb

import models._
import scala.Array
import com.github.nscala_time.time.Imports._
import models.JobRun
import models.Message
import models.Data
import influxdb.Series
import models.DataDay
import play.api.libs.json.{Json, JsValue}
import scala.collection.mutable.ListBuffer

/**
 */
object Connector {
  private val client: Client = new Client(/*"localhost:8086"*/)
  final val DB_NAME               = "ocs"
  client.createDatabase(DB_NAME)
  client.database = DB_NAME


  def addMessage(mess: Message) {
    mess.data match {
      case j:JobRun => addJobRun(mess)
      case s:Stat => addStat(mess)

      case _ => {
        return
      }
    }
  }


  def addJobRun(mess: Message) {

    val data = mess.data .asInstanceOf [JobRun]
    val jobRun = Series("JobRun",
      Array("idc","ide","status","id"),
      Array(
        Array[Any](mess.idc.id,mess.ide.id, data.status.toString, data.jobid)
      )
    )

    val res = client.writeSeries(Array(jobRun))
  }

  def addStat(mess: Message) {

    val data = mess.data .asInstanceOf [Stat]
    val stat = Series("Stat",
      Array("idc","ide","cpu","memory","thp","cachesize"),
      Array(
        Array[Any](mess.idc.id,mess.ide.id, data.cpu, data.memory, data.thp, data.cacheSize)
      )
    )

    val res = client.writeSeries(Array(stat))
  }


  def getJobRun(startDate:String,endDate:String): JsValue = {

    val (response, err) = client.query("SELECT count(status) FROM JobRun where time > '"+startDate+"' group by time(1d) order asc")
    //select Mean(cpu), Mean(thp) from Stat group by time(1d)
    assert(None == err)

    val series = response.toSeries
    val startDateTime = new DateTime(startDate)

    var listM  = new ListBuffer[DataDaySimple]()


    val size = series(0).points.length
    var curentSize = 0

    var currentDay = startDateTime
    for (i <- 1 to 31) {
      var count = 0
      val seriesDate = new DateTime(series(0).points(curentSize)(0).asInstanceOf[Double].toLong)
      if (seriesDate.dayOfMonth().equals(currentDay.dayOfMonth())) {
         count = series(0).points(curentSize)(1).asInstanceOf[Double].toInt
         if (curentSize < size-1) curentSize +=1
      }
      listM +=  DataDaySimple(i, currentDay.dayOfMonth().getAsString, Data(count,0,0))
      currentDay = startDateTime.plusDays(i)
    }

    DataS.serialize(DataMonthSimple(0,startDateTime.monthOfYear().toString,listM.toList))
  }
}
