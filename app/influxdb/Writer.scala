package influxdb

/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 25/07/14
 * Time: 17:35
 * To change this template use File | Settings | File Templates.
 */
class Writer {
  private val client: Client = new Client(/*"localhost:8086"*/)
  final val DB_NAME               = "ocs"
  client.createDatabase(DB_NAME)
  client.database = DB_NAME

  
}
