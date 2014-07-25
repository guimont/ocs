package stub

import models.{Stat, JobRun, Message}
import influxdb.{Connector, Series}

/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 25/07/14
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
object StubInfluxWriter {

  def addStubMessage(mess: Message, time: Long) {
    if (mess != null) { //TODO improve this cheat/shit
      mess.data match {
        case j:JobRun => addStubJobRun(mess, time)
        case s:Stat => addStubStat(mess, time)

        case _ => {
          return
        }
      }
    }
  }


  def addStubJobRun(mess: Message,time: Long) {

    val data = mess.data .asInstanceOf [JobRun]
    val jobRun = Series("JobRun",
      Array("time","idc","ide","status","id"),
      Array(
        Array[Any](time,mess.idc.id,mess.ide.id, data.status.toString, data.jobid)
      )
    )

    Connector.Writer(jobRun)
  }

  def addStubStat(mess: Message,time: Long) {

    val data = mess.data .asInstanceOf [Stat]
    val stat = Series("Stat",
      Array("time","idc","ide","cpu","memory","thp","cachesize"),
      Array(
        Array[Any](time,mess.idc.id,mess.ide.id, data.cpu, data.memory, data.thp, data.cacheSize)
      )
    )

    Connector.Writer(stat)
  }



}
