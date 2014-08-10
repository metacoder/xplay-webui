package actors

import akka.actor.Actor
import model.{MessageBigDecimals, GPSPosition}
import play.api.Logger
import utils.BigDecimalRounding

class GPSPositionActor() extends Actor with BigDecimalRounding {

  override def receive: Actor.Receive = {

    case MessageBigDecimals(position) => {
      val gpsPosition = GPSPosition(position(0), position(1), r(position(2), 0), r(position(3), 0))
      ActorRegistry.websocketRegistry ! SendMessageToWebSockets(gpsPosition)
      Logger.debug(s"position received: $gpsPosition")
    }
  }
}



