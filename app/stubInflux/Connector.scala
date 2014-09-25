package stubInflux

import scala.Array
import influxdb.{Series, Client}


object Connector {
  private val client: Client = new Client("192.168.112.158:8086"/*"188.226.240.96:8086*//*"localhost:8086"*/)
  final val DB_NAME               = "ocs"
  client.createDatabase(DB_NAME)
  client.database = DB_NAME

  def Writer(series : Series) {
    client.writeSeries(Array(series))
  }
}
