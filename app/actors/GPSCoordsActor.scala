package actors

import akka.actor.Actor
import model.{MessageFloats, GetGPSCoords, GPSCoords}
import play.api.Logger





class GPSCoordsActor() extends Actor with XPlanePayloadParser {

  var lastGPSPosition: Option[GPSCoords] = None

  override def receive: Actor.Receive = {

    case MessageFloats(coords) => {
      // 32 bytes, 4 byte floats
      lastGPSPosition = Some(GPSCoords(coords(0), coords(1)))
      Logger.debug("coords received: $coords")
    }

    case GetGPSCoords => sender ! lastGPSPosition

  }


}



