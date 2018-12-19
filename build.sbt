import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "me.nsmr",
      scalaVersion := "2.12.7",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "LIlib",
    libraryDependencies ++= List(
      scalaTest % Test,
      logger.scalaLogging,
      logger.logback
    )
  )
