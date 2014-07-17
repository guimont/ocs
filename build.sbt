name := "demo"

version := "1.0-SNAPSHOT"

resolvers += "rediscala" at "https://raw.github.com/etaty/rediscala-mvn/master/releases/"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.json4s" %% "json4s-native" % "3.2.9",
  "org.json4s" %% "json4s-jackson" % "3.2.9",
  "org.scalatest" %% "scalatest" % "2.1.0" % "test",
  "com.thoughtworks.paranamer" % "paranamer" % "2.6",
  "org.scala-lang" % "scalap" % "2.9.0-1",
  "com.etaty.rediscala" %% "rediscala" % "1.3"
)

play.Project.playScalaSettings
