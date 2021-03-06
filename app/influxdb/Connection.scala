package influxdb

import models._
import play.api.libs.json.JsValue
import org.joda.time.DateTime
import scala.collection.mutable.ListBuffer

import models.Stat
import models.DataDaySimple
import models.JobRun
import models.Message
import models.Data


/**
 Connection class
  Purpose is to have method to write and read data in InfluxDB
 */


class Connection {
  private val client: Client = new Client("192.168.112.158:8086"/*"188.226.240.96:8086"*//*"localhost:8086"*/)
  final val DB_NAME               = "ocs"
  client.createDatabase(DB_NAME)
  client.database = DB_NAME

  def Writer(series : Series) {
    val res = client.writeSeries(Array(series))
  }


  def addMessage(mess: Message) {

    if (mess != null) { //TODO improve this cheat/shit
      mess.data match {
        case j:JobRun => addJobRun(mess)
        case s:Stat => addStat(mess)

        case _ => {
          return
        }
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

    client.writeSeries(Array(jobRun))
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


  def getData(request: RequestHeader): JsValue = {
    request.typeRequest match {
      case it if 0==it => getJobRuns(request.date,request.date)
      case it if 1==it => getJobRun(request.date)
    }
  }


  def getJobRun(date:String) : JsValue = {

    val (response, err) = client.query("SELECT count(status) FROM JobRun where time > '"+date+"'")
    assert(None == err)
    val series = response.toSeries

    val (responseStat,err2) =  client.query("select Mean(cpu), Mean(memory) from Stat where time > '"+date+"'")
    assert(None == err2)
    val seriesStat = responseStat.toSeries

    val count = series(0).points(0)(1).asInstanceOf[Double].toInt
    val cpu = seriesStat(0).points(0)(1).asInstanceOf[Double]
    val mem = seriesStat(0).points(0)(2).asInstanceOf[Double]

    DataS.serialize(DataDaySimple(0, new DateTime(date).dayOfMonth().getAsString, Data(DataRun(count,0,0,0),cpu,mem)))

  }


  def getJobRuns(startDate:String,endDate:String): JsValue = {

    val (response, err) = client.query("SELECT count(status) FROM JobRun where time > '"+startDate+"' group by time(1d) order asc")
    assert(None == err)
    val series = response.toSeries

    val (responseStat,err2) =  client.query("select Mean(cpu), Mean(memory) from Stat where time > '"+startDate+"' group by time(1d) order asc")
    assert(None == err2)
    val seriesStat = responseStat.toSeries


    val startDateTime = new DateTime(startDate)

    var listM  = new ListBuffer[DataDaySimple]()


    val size = series(0).points.length -1
    val sizeStat = seriesStat(0).points.length -1
    var currentSize = 0
    var currentSizeStat = 0

    var currentDay = startDateTime
    for (i <- 1 to 31) {
      var count = 0
      var cpu = 0.
      var mem = 0.
      val seriesDate = new DateTime(series(0).points(currentSize)(0).asInstanceOf[Double].toLong)
      if (seriesDate.dayOfMonth().equals(currentDay.dayOfMonth())) {
        count = series(0).points(currentSize)(1).asInstanceOf[Double].toInt
        if (currentSize < size) currentSize +=1
      }
      if (seriesDate.dayOfMonth().equals(currentDay.dayOfMonth())) {
        cpu = seriesStat(0).points(currentSizeStat)(1).asInstanceOf[Double]
        mem = seriesStat(0).points(currentSizeStat)(2).asInstanceOf[Double]
        if (currentSizeStat < sizeStat) currentSizeStat +=1
      }
      listM +=  DataDaySimple(i, currentDay.dayOfMonth().getAsString, Data(DataRun(count,0,0,0),cpu,mem))
      currentDay = startDateTime.plusDays(i)
    }

    DataS.serialize(DataMonthSimple(0,startDateTime.monthOfYear().toString,listM.toList))
  }
}
