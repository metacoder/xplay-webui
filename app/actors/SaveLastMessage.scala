package actors

import akka.actor.Actor
import play.api.Logger

/**
 * Created by benjamin on 8/10/14.
 */
trait SaveLastMessage[T] extends Receiving {
  var lastReceived: Option[T] = None

  def sendIfChanged(msg: T) = {
    if (lastReceived.isDefined && lastReceived.get == msg) {
      Logger.debug(s"dropping $msg because last one was the same")
    } else {
      ActorRegistry.websocketRegistry ! SendMessageToWebSockets(msg)
      lastReceived = Some(msg)
    }
  }

  receiver {
    case SendLastMessageToWebSockets(actor) => {
      if (lastReceived.isDefined) {
        actor ! lastReceived.get
      }
    }
  }
}
