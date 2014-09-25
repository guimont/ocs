package cache

/**
 * cache to aglomerate data
 */
object MemoryCache {

  var index = 0
  val mapId  = scala.collection.mutable.Map[String, Int]()

  def getId(key:String): Int = {
    mapId.get(key).getOrElse(setId(key))
  }

  def setId(key: String):Int = {
    mapId +=  key -> index
    index = index + 1
    index
  }

}
