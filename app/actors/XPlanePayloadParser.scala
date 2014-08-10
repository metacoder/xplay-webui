package actors

import java.math.{RoundingMode, MathContext}
import java.nio.ByteOrder

import akka.util.ByteString

trait XPlanePayloadParser {

  def parseBigDecimalsFromMessage(bytes: ByteString): List[BigDecimal] = {

    // 32 bytes, 4 byte floats
    bytes.grouped(4).map(_.asByteBuffer).map(floatByteBuffer => {
      floatByteBuffer.order(ByteOrder.LITTLE_ENDIAN)
      BigDecimal.decimal(floatByteBuffer.getFloat, new MathContext(16, RoundingMode.HALF_UP))
    }).toList

  }
}
