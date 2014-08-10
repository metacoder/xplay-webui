package actors

import akka.actor.Actor
import model.{MessageBigDecimals, GPSPosition}
import play.api.Logger
import utils.BigDecimalRounding

class GPSPositionActor() extends Actor with BigDecimalRounding {

  var lastReceived: Option[GPSPosition] = None

  override def receive: Actor.Receive = {

    case MessageBigDecimals(position) => {
      val gpsPosition = GPSPosition(position(0), position(1), r(position(2), 0), r(position(3), 0))

      if(lastReceived.isDefined && lastReceived.get == gpsPosition){
        Logger.debug(s"dropping position $gpsPosition because last one was the same")
      } else {
        ActorRegistry.websocketRegistry ! SendMessageToWebSockets(gpsPosition)
        lastReceived = Some(gpsPosition)
        Logger.debug(s"position received: $gpsPosition")
      }
    }
  }
}



