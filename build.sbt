import play.Project._

name := "forms"

version := "1.0"

playJavaSettings


libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaEbean,
  "mysql" % "mysql-connector-java" % "5.1.18"
)