package actors

import akka.actor.Props
import play.api.GlobalSettings
import play.api.libs.concurrent.Akka._

/**
 * Created by becker on 01.08.14.
 */
object ActorRegistry {

  import play.api.Play.current

  val gpsPositionActor = system.actorOf(Props[GPSPositinonActor], name="gpsPositionActor")
  val pitchRollHeadingActor = system.actorOf(Props[PitchRollHeadingActor], name="pitchRollHeadingActor")
  val xplaneDataReceiver = system.actorOf(Props[XPlaneUDPReceiverActor], name = "xplaneReceiver")

  val websocketRegistry = system.actorOf(Props[WebSocketRegistry], name = "websocketRegistry")
}
