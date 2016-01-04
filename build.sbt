
version := "0.6"

scalaVersion := "2.11.7"

resolvers += "stepsoft" at "http://nexus.mcsherrylabs.com/nexus/content/groups/public"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.0"

libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.4.0"

libraryDependencies += "org.eclipse.jetty.aggregate" % "jetty-all-server" % "8.1.18.v20150929"

libraryDependencies += "com.vaadin" % "vaadin-server" % "7.5.8"

libraryDependencies += "com.vaadin" % "vaadin-themes" % "7.5.8"

libraryDependencies += "com.vaadin" % "vaadin-push" % "7.5.8"

libraryDependencies += "com.vaadin" % "vaadin-client-compiler" % "7.5.8"

libraryDependencies +=   "com.vaadin" % "vaadin-client-compiled" % "7.5.8"

libraryDependencies += "org.apache.poi" % "poi" % "3.12"

libraryDependencies += "mcsherrylabs.com" %% "sss-ancillary" % "0.9.4"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

libraryDependencies += "org.scalatra" %% "scalatra" % "2.3.1"

libraryDependencies += "com.typesafe" % "config" % "1.2.1"

mainClass in (Compile, run) := Some("sss.ui.ServerLauncher")
