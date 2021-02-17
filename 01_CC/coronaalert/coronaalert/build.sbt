name := "coronaalert"
organization := "com.alvarodelaflor"
version := "1.0"
lazy val root = (project in file(".")).enablePlugins(PlayScala)
scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "com.typesafe.play" %% "play-mailer" % "8.0.1",
  "com.typesafe.play" %% "play-mailer-guice" % "8.0.1",
  "com.typesafe.play" %% "play-slick" %  "5.0.0",
  "com.typesafe.play" %% "play-slick" %  "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
  "mysql" % "mysql-connector-java" % "5.1.41"
)
