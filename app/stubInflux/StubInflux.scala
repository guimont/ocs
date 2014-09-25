package stubInflux

import com.github.nscala_time.time.Imports._


object StubInflux {

  def init(startDate: String, period: Int) {

    val startDateTime = new DateTime(startDate)

    val iteration = period * 24 * 60 * 12 // hour * minute each 5 sec

    for (i <- 1 to iteration) {
      val time = startDateTime.plusSeconds(5 * i)
      val mess = StubGenerator.generateFakeRunMess
      StubInfluxWriter.addStubMessage(mess, time.getMillis)

      val stat = StubGeneratorStat.generateFakeStatMess
      StubInfluxWriter.addStubMessage(stat, time.getMillis)
    }

  }

}
