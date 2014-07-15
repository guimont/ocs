package influxdb

import models.{JobRun, Message}
import scala.Array

/**
 */
object Connector {
  private val client: Client = new Client


  def addJobRun(mess: Message) {

    val data: JobRun = mess.data match {
      case x:JobRun => x
      case _ => {
        return
      }
    }

    val jobRun = Series("JobRun",
      Array("idc","ide","status","id"),
      Array(
        Array[Any](mess.idc.id,mess.ide.id, data.status, data.jobid)
      )
    )

    client.writeSeries(Array(jobRun))
  }


}
