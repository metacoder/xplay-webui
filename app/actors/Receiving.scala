package actors

import akka.actor.Actor

/**
 * Created by benjamin on 8/10/14.
 */
trait Receiving {
  var receivers: Actor.Receive = Actor.emptyBehavior
  def receiver(next: Actor.Receive) { receivers = receivers orElse next }
  def receive = receivers // Actor.receive definition
}
