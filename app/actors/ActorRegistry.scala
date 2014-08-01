package actors

import akka.actor.Props
import play.api.GlobalSettings
import play.api.libs.concurrent.Akka._

/**
 * Created by becker on 01.08.14.
 */
object ActorRegistry {

  import play.api.Play.current

  val gpsCoordsActor = system.actorOf(Props[GPSCoordsActor], name="gpscoordsactor")
  val pitchRollHeadingActor = system.actorOf(Props[PitchRollHeadingActor], name="pitchrollheadingactor")
  val xplaneDataReceiver = system.actorOf(Props[XPlaneUDPReceiverActor], name = "xplanereceiver")
}
