# Akka UDP Demo

A simple UDP Server written in Scala, that listens to messages and simply echos them back.

Requires [JDK 11](https://adoptopenjdk.net/) and [sbt 1.3+](https://www.scala-sbt.org/).

## Usage

Start the server:

```bash
$ sbt run
[info] Loading settings for project global-plugins from metals.sbt ...
[info] Loading global plugins from /home/amdelamar/.sbt/1.0/plugins
[info] Loading settings for project akka-udp-build from plugins.sbt ...
[info] Loading project definition from /home/amdelamar/workspace/akka-udp/project
[info] Loading settings for project root from build.sbt ...
[info] Set current project to akka-udp (in build file:/home/amdelamar/workspace/akka-udp/)
[info] Compiling 1 Scala source to /home/amdelamar/workspace/akka-udp/target/scala-2.13/classes ...
[info] running com.amdelamar.UdpServer 
UDP Server up. Enter Ctl+C to stop...
[INFO] [05/17/2020 15:26:04.124] [default-akka.actor.default-dispatcher-6] [akka://default/user/$a] Starting UDP Server on localhost:9010
[INFO] [05/17/2020 15:26:04.189] [default-akka.actor.default-dispatcher-6] [akka://default/user/$a] UDP Server is listening to 127.0.0.1:9010

```

Then open another terminal and send messages with netcat:

```bash
$ nc -u localhost 9010
hello
hello
hows it going?
hows it going?
```

Ctrl+C to exit both client and server.