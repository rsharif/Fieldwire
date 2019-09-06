name := "FieldWire"
 
version := "1.0" 
      
lazy val `fieldwire` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % "test"
)

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.8"

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-io-extra" % "2.1.8"

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-filters" % "2.1.8"

libraryDependencies += "commons-io" % "commons-io" % "2.6"

//libraryDependencies += "com.h2database" % "h2" % "1.4.192"

//libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.0"

//libraryDependencies += jdbc

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"               % "2.5.2",
  "com.h2database"  %  "h2"                        % "1.4.199",
  "ch.qos.logback"  %  "logback-classic"           % "1.2.3"
)