import com.typesafe.config.ConfigFactory
import play.PlayScala

val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()

name := """xplay-webui"""

version := conf.getString("application.version")

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
).map(_.exclude("commons-logging", "commons-logging"))

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.webjars" % "chartjs" % "26962ce",
  "org.webjars" % "angularjs" % "1.2.21",
  "org.webjars" % "angular-ui-bootstrap" % "0.11.0-2",
  "org.webjars" % "smoothie" % "1.24",
  "org.webjars" % "reconnecting-websocket" % "23d2fbc",
  "org.webjars" % "leaflet" % "0.7.3",
  "org.webjars" % "leaflet-plugins" % "1.1.2"
)
