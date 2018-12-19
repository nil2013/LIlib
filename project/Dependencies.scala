import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  object logger {
    lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
    lazy val logback      = "ch.qos.logback" % "logback-classic" % "1.2.3"
  }
}
