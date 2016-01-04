package sss.ui

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler

/**
 * Created by alan on 12/8/15.
 */

class ServerLauncher(port: Int, contextPath: String) {

  val server: Server = new Server(port)
  private val context: ServletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS)

  context.setContextPath(contextPath)
  server.setHandler(context)

  context.setResourceBase("./WebContent")
  context.addServlet(classOf[Servlet], "/*")
  server.setHandler(context)

  server.start

  def join = server.join()
}

object ServerLauncher {

  def main(args: Array[String]) {

    val port = args.find(_.startsWith("port=")).map(_.substring(5).toInt).getOrElse(8080)
    val cPath = args.find(_.startsWith("contextPath=")).map(_.substring(12)).getOrElse("/template")
    val server = new ServerLauncher(port, cPath)
    server.join
  }
}
