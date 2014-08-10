package utils

import actors.{SendMessageToWebSockets, ActorRegistry}
import play.api.Logger

/**
 * Created by benjamin on 8/10/14.
 */
trait SendIfChanged[T] {
  var lastReceived: Option[T] = None

  def sendIfChanged(msg: T) = {
    if(lastReceived.isDefined && lastReceived.get == msg){
      Logger.debug(s"dropping $msg because last one was the same")
    } else {
      ActorRegistry.websocketRegistry ! SendMessageToWebSockets(msg)
      lastReceived = Some(msg)
    }
  }
}
