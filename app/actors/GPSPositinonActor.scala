package actors

import akka.actor.Actor
import model.{MessageFloats, GPSPosition}
import play.api.Logger





class GPSPositinonActor() extends Actor with XPlanePayloadParser {

  var lastGPSPosition: Option[GPSPosition] = None

  override def receive: Actor.Receive = {

    case MessageFloats(position) => {
      // 32 bytes, 4 byte floats
      val gpsPosition = GPSPosition(position(0), position(1), position(2), position(3))
      lastGPSPosition = Some(gpsPosition)
      ActorRegistry.websocketRegistry ! SendMessageToWebSockets(gpsPosition)
      Logger.debug(s"position received: $gpsPosition")
    }

    case GetGPSPosition => sender ! lastGPSPosition
  }
}



