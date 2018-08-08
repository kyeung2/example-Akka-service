

lazy val commonSettings = Seq(
  name := "library",
  organization := "io.flyingnimbus",
  scalaVersion := "2.12.6",
  version := "1.0-SNAPSHOT"
)

lazy val root = (project in file(".")).
  settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.1.3",
      "com.typesafe.akka" %% "akka-http-xml" % "10.1.3",
      "com.typesafe.akka" %% "akka-stream" % "2.5.14",

      "de.heikoseeberger" %% "akka-http-circe" % "1.21.0",
      "io.circe" %% "circe-core" % "0.9.3",
      "io.circe" %% "circe-generic" % "0.9.3",
      "io.circe" %% "circe-parser" % "0.9.3",

      "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0",

      //finish reading https://github.com/lightbend/scala-logging
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
      "ch.qos.logback" % "logback-classic" % "1.2.3",

      "com.typesafe.akka" %% "akka-http-testkit" % "10.1.3" % Test,
      "com.typesafe.akka" %% "akka-testkit" % "2.5.14" % Test,
      "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.14" % Test,
      "org.scalatest" %% "scalatest" % "3.0.1" % Test,

      "org.mongodb" % "mongo-java-driver" % "3.4.2",
      "com.github.fakemongo" % "fongo" % "2.1.0"
    )
  )
