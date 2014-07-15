package models

case class JobRun(status: Char, jobid: Int)
case class Technical(code: Char, status: Int)
case class Stat(cpu: Float, memory: Float, cacheSize: Int, thp: Int)
case class Log(code: Char, log: String)