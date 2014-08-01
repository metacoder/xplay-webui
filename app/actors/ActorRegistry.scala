package actors

import akka.actor.{Props, ActorSystem}

object ActorRegistry {

  implicit val system = ActorSystem("UDPTest")

  val gpsCoordsActor = system.actorOf(Props[GPSCoordsActor], name="gpscoordsactor")
  val pitchRollHeadingActor = system.actorOf(Props[PitchRollHeadingActor], name="pitchrollheadingactor")
  val xplaneDataReceiver = system.actorOf(Props[XPlaneUDPReceiverActor], name = "xplanereceiver")

}


