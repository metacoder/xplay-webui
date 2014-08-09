import play.PlayScala

name := """xplay-webui"""

version := "1.2-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
).map(_.exclude("commons-logging", "commons-logging"))

libraryDependencies += "org.webjars" % "bootstrap" % "3.2.0"

libraryDependencies += "org.webjars" %% "webjars-play" % "2.3.0"

libraryDependencies += "org.webjars" % "chartjs" % "26962ce"

libraryDependencies += "org.webjars" % "angularjs" % "1.2.21"

libraryDependencies += "org.webjars" % "angular-ui-bootstrap" % "0.11.0-2"
