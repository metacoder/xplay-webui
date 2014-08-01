package actors

import akka.actor.Actor
import play.api.Logger

case class GPSCoords(latitude: Float, longitude: Float)

object GetGPSCoords

class GPSCoordsActor() extends Actor with XPlanePayloadParser {

  var lastGPSPosition: Option[GPSCoords] = None

  override def receive: Actor.Receive = {

    case MessageFloats(coords) => {
      // 32 bytes, 4 byte floats
      lastGPSPosition = Some(GPSCoords(coords(0), coords(1)))
      Logger.debug(s"coords received: ${lastGPSPosition.get}")
    }

    case GetGPSCoords => sender ! lastGPSPosition
  }
}



