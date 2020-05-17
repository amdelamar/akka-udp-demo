name := "akka-tcp"
organization := "com.amdelamar"
version := "1.0"
scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.6.5"
)

lazy val root = (project in file("."))
enablePlugins(JavaAppPackaging)
