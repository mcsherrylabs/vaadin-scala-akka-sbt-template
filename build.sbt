
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

libraryDependencies += "org.vaadin.addons.lazyquerycontainer" % "vaadin-lazyquerycontainer" % "7.4.0.1"

libraryDependencies += "org.scalatra" %% "scalatra" % "2.3.1"

libraryDependencies += "mcsherrylabs.com" %% "sss-db" % "0.9.6"

libraryDependencies += "org.hsqldb" % "hsqldb" % "2.3.2"

libraryDependencies += "com.typesafe" % "config" % "1.2.1"

libraryDependencies += "org.scalatra" %% "scalatra-json" % "2.3.1"

libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.2.11"

libraryDependencies += "org.json4s" %% "json4s-ext" % "3.2.11"

libraryDependencies += "us.monoid.web" % "resty" % "0.3.2"

mainClass in (Compile, run) := Some("sss.ui.ServerLauncher")
