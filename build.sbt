lazy val pekkoHttpVersion = "1.2.0"
lazy val pekkoVersion     = "1.1.4"

// Run in a separate JVM, to make sure sbt waits until all threads have
// finished before returning.
// If you want to keep the application running while executing other
// sbt tasks, consider https://github.com/spray/sbt-revolver/
fork := true

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.13.16"
    )),
    name := "erp-pekko",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-http"                % pekkoHttpVersion,
      "org.apache.pekko" %% "pekko-http-spray-json"     % pekkoHttpVersion,
      "org.apache.pekko" %% "pekko-actor-typed"         % pekkoVersion,
      "org.apache.pekko" %% "pekko-stream"              % pekkoVersion,
      "org.apache.pekko" %% "pekko-http-cors" % pekkoHttpVersion,
      "ch.qos.logback"    % "logback-classic"           % "1.5.18",

      "com.typesafe.slick" %% "slick" % "3.6.1",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.6.1",
      "org.postgresql" % "postgresql" % "42.7.7",
      "javax.inject" % "javax.inject" % "1",
      "com.google.inject" % "guice" % "7.0.0",
      "net.codingwell" %% "scala-guice" % "7.0.0",
      "com.github.tototoshi" %% "scala-csv" % "2.0.0",
      "org.apache.poi" % "poi" % "5.4.1",
      "org.apache.poi" % "poi-ooxml" % "5.4.1",
      "org.scala-lang.modules" %% "scala-xml" % "2.4.0",
      "org.apache.pdfbox" % "pdfbox" % "3.0.5",

      "org.apache.pekko" %% "pekko-http-testkit"        % pekkoHttpVersion % Test,
      "org.apache.pekko" %% "pekko-actor-testkit-typed" % pekkoVersion     % Test,
      "org.scalatest"     %% "scalatest"                % "3.2.19"         % Test
    )
  )
