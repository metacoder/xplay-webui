package actors

import akka.actor.Actor
import model.{MessageFloats, GetGPSCoords, GPSCoords}
import play.api.Logger





class GPSCoordsActor() extends Actor with XPlanePayloadParser {

  var lastGPSPosition: Option[GPSCoords] = None

  override def receive: Actor.Receive = {

    case MessageFloats(coords) => {
      // 32 bytes, 4 byte floats
      val gpsCoords = GPSCoords(coords(0), coords(1))
      lastGPSPosition = Some(gpsCoords)
      ActorRegistry.websocketRegistry ! SendMessageToWebSockets(gpsCoords)
      Logger.debug(s"coords received: $gpsCoords")
    }

    case GetGPSCoords => sender ! lastGPSPosition
  }
}



