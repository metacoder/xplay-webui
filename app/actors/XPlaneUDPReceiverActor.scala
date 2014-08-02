package actors

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef}
import akka.io.{Udp, IO}
import model.MessageFloats
import play.api.Logger

class XPlaneUDPReceiverActor() extends Actor with XPlanePayloadParser {

  import ActorRegistry.udpConnectionStatusActor

  import context.system

  IO(Udp) ! Udp.Bind(self, new InetSocketAddress("0.0.0.0", 48000))

  def receive = {

    case Udp.Bound(local) =>
      udpConnectionStatusActor ! UDPConnectionStatusActorMessages.Bound
      Logger.info(s"actor bound to udp $local")
      context.become(receivingXplaneData(sender()))


    case Udp.CommandFailed(command) =>
      udpConnectionStatusActor ! UDPConnectionStatusActorMessages.Error
      Logger.error("udp connection failed!")
      // finite state

  }

  def receivingXplaneData(connection: ActorRef): Receive = {

    case Udp.Received(data, remote) => {

      udpConnectionStatusActor ! UDPConnectionStatusActorMessages.MessageReceived

      try {

        // data is structured as the following: first 5 bytes are DATA@
        // followed by a chunk of 36 bytes
        //      => first 4 bytes: index number of data element in data output screen from xplane (eg 20 = gps)
        //      => last 32 bytes: data (up to  8 single precising floating point numbers)

        val (_, message) = data.splitAt(5)

        message.grouped(36) foreach { payload =>

          val messageType = payload(0)
          val messageContent = payload.takeRight(32)

          val floats = parseFloatsFromMessage(messageContent)

          messageType match {
            case 20 => ActorRegistry.gpsPositionActor ! MessageFloats(floats)
            case 17 => ActorRegistry.pitchRollHeadingActor ! MessageFloats(floats)

            case unknown => Logger.debug(s"ignoring message type $unknown because i can't handle this")
          }

        }

      } catch {
        case e: Exception => Logger.error(s"exception caught while parsing message $data", e)
      }
    }
  }
}




