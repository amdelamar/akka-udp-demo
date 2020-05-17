package com.amdelamar

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging
import akka.io.{IO, Tcp}

import scala.io.StdIn

object TcpServer {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()

    val host = "localhost"
    val port = 9020
    val tcpServer = system.actorOf(Props(classOf[TcpServer], host, port))

    println(s"TCP Server up. Enter Ctl+C to stop...")
    StdIn.readLine() // run until user cancels
  }
}

class TcpServer(host: String = "localhost", port: Int = 0) extends Actor {
  import akka.io.Tcp._
  import context.system
  val log = Logging(context.system, this.getClass)

  override def preStart(): Unit = {
    log.info(s"Starting TCP Server on $host:$port")
    IO(Tcp) ! Bind(self, new InetSocketAddress(host, port))
  }

  def receive: Receive = {
    case b @ Bound(local) =>
      log.info(s"TCP Server is listening to ${local.getHostString}:${local.getPort}")
      context.parent ! b

    case CommandFailed(_: Bind) =>
      context.stop(self)

    case Connected(remote, local) =>
      log.info(s"${remote.getHostString}:${remote.getPort} connected.")
      val handler = context.actorOf(Props(classOf[EchoHandler]))
      sender ! Register(handler)
  }

  override def postStop(): Unit = {
    log.info(s"Stopping TCP Server.")
  }
}

class EchoHandler extends Actor {
  import akka.io.Tcp._
  val log = Logging(context.system, this.getClass)

  def receive: Receive = {
    case Received(data) =>
      val msg = data.decodeString("utf-8").replaceAll("\n", " ")
      log.info(s"Got: ${msg}")

      // echo back to sender
      sender ! Write(data)

    case PeerClosed =>
      context.stop(self)
  }
}
