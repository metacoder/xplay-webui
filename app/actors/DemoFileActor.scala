package actors

import java.io.{File, OutputStream}
import java.nio.file.{Paths, Files}

import org.apache.commons.codec.binary.Base64

import scala.io.Source

import actors.ActorRegistry._
import akka.actor.Actor
import akka.util.ByteString
import play.api.Logger

/**
 * Created by benjamin on 8/21/14.
 */
class DemoFileActor  extends Actor with XPlanePayloadParser {

  val path = Paths.get("data.txt")

  var data = readFile()

  var out: OutputStream = null

  var pos = 0;

  override def receive: Receive = {
    case message: ByteString => {
      if (pos >= 0) {
        if (path.toFile.exists) path.toFile.renameTo(new File("data.old." + System.currentTimeMillis() + ".txt"))
        out = Files.newOutputStream(path)
        pos = -1
      }
      out.write(Base64.encodeBase64(message.toArray))
      out.write('\n')
    }
    case UDPConnectionStatusActorMessages.statusWaiting => {
      data = readFile()
      pos = 0;
    }
    case "tick" => {
      if (data.size > 0 && pos >= 0) {
        udpConnectionStatusActor ! UDPConnectionStatusActorMessages.MessageReceived

        try {
          parsePayloadMessages(ByteString(data(pos)))
        } catch {
          case e: Exception => Logger.error(s"exception caught while parsing message $pos", e)
        }

        pos = if (pos >= data.size - 1) 0 else pos + 1
      }
    }
  }

  private def readFile(): List[Array[Byte]] = {
    if (path.toFile.exists) {
      (for (line <- Source.fromFile(path.toFile).getLines)
      yield Base64.decodeBase64(line)).toList
    } else List()
  }
}
