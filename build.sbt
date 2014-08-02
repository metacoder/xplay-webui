import play.PlayScala

name := """xplay-webui"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies += "org.webjars" % "bootstrap" % "3.2.0"

libraryDependencies += "org.webjars" %% "webjars-play" % "2.3.0"

libraryDependencies += "org.webjars" % "chartjs" % "26962ce"

libraryDependencies += "org.webjars" % "angularjs" % "1.2.21"


