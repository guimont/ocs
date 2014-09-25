package DBTest

import redis.{RedisBlockingClient, RedisClient}
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


/**
 * 
 */
object Test {
  implicit val akkaSystem = akka.actor.ActorSystem()
  val redis = RedisClient("192.168.112.158",6379)

  def run {


    val futurePong = redis.ping()
    println("Ping sent!")
    futurePong.map(pong => {
      println(s"Redis replied with a $pong")
    })
    Await.result(futurePong, 5 seconds)

    val futureResult = doSomething(redis)

    Await.result(futureResult, 5 seconds)

    //akkaSystem.shutdown()
  }


  def doSomething(redis: RedisClient): Future[Boolean] = {
    // launch command set and del in parallel
    val s = redis.set("redis", "is awesome")
    val d = redis.del("i")
    for {
      set <- s
      del <- d
      incr <- redis.incr("i")
      iBefore <- redis.get("i")
      incrBy20 <- redis.incrby("i", 20)
      iAfter <- redis.get("i")
    } yield {
      println("SET redis \"is awesome\"")
      println("DEL i")
      println("INCR i")
      println("INCRBY i 20")
      val ibefore = iBefore.map(_.utf8String)
      val iafter = iAfter.map(_.utf8String)
      println(s"i was $ibefore, now is $iafter")
      iafter == "20"
    }
  }

  val redisBlocking = RedisBlockingClient("192.168.112.158",6379)
  def runB {


    val r = redis.del("workList").flatMap(_ => {
      consumer()
      publisher()
    })

    Await.result(r, 15 seconds)

  }

  def publisher() = {
    redis.lpush("workList", "doSomeWork")
    Thread.sleep(2000)
    redis.rpush("otherKeyWithWork", "doSomeWork1", "doSomeWork2")
  }

  def consumer() = Future {
    val waitWork = 3
    val sequenceFuture = for {i <- 0 to waitWork}
    yield {
      redisBlocking.blpop(Seq("workList", "otherKeyWithWork"), 5 seconds).map(result => {
        result.map({
          case (key, work) => println(s"list $key has work : ${work.utf8String}")
        })
      })
    }

    Await.result(Future.sequence(sequenceFuture), 10 seconds)
  }




}