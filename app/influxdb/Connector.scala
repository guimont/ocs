package influxdb

import models.{DataMonth, JobRun, Message}
import scala.Array
import com.github.nscala_time.time.Imports._

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

      case _ => {
        return
      }
    }
  }


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
        Array[Any](mess.idc.id,mess.ide.id, data.status.toString, data.jobid)
      )
    )

    val res = client.writeSeries(Array(jobRun))
  }


  def getJobRun(startDate:String,endDate:String){
    var lDate : Long = 0
    val (response, err) = client.query("SELECT count(status) FROM JobRun where time > '"+startDate+"' group by time(1h)")
    assert(None == err)

    val series = response.toSeries


    series(0).points(0)(0) match {
      case x:Double => lDate = x.toLong
    }


    val date= new DateTime(lDate)
    println(date.dayOfMonth().get())



    //pour chaque element de series
    //recuperer heure/jour/mois
    //si jour = jour cursuer
    //si heure = heure curseur
    //ajout compteur heure
    //sinon creation DataHour => ajour dans DataDay; heure curseur +1; compteur heure=0
    //si heure curseur > 23

    //ajout compteur
    //sinon creation element jour

    /*
        series.map {
          x:Message => Data()
        }

    */


  }


}
