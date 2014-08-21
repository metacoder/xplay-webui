package actors

import java.math.{RoundingMode, MathContext}
import java.nio.ByteOrder

import akka.util.ByteString
import model.MessageBigDecimals
import play.api.Logger

trait XPlanePayloadParser {

  def parseBigDecimalsFromMessage(bytes: ByteString): List[BigDecimal] = {

    // 32 bytes, 4 byte floats
    bytes.grouped(4).map(_.asByteBuffer).map(floatByteBuffer => {
      floatByteBuffer.order(ByteOrder.LITTLE_ENDIAN)
      BigDecimal.decimal(floatByteBuffer.getFloat, new MathContext(16, RoundingMode.HALF_UP))
    }).toList
  }

  def parsePayloadMessages(payload: ByteString) {
    payload.grouped(36) foreach { payload =>

      val messageType = payload(0)
      val messageContent = payload.takeRight(32)

      val bigDecimals = parseBigDecimalsFromMessage(messageContent)

      messageType match {
        case 20 => ActorRegistry.gpsPositionActor ! MessageBigDecimals(bigDecimals)
        case 17 => ActorRegistry.pitchRollHeadingActor ! MessageBigDecimals(bigDecimals)
        case 3 => ActorRegistry.speedActor ! MessageBigDecimals(bigDecimals)

        case unknown => Logger.debug(s"ignoring message type $unknown because i can't handle this")
      }
    }
  }
}
