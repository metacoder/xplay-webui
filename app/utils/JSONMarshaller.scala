package utils

import model.{GPSPosition, PitchRollHeading, Speed, UDPConnectionStatus}
import play.api.libs.json.{Json, Writes}

/**
 * Created by becker on 8/10/14.
 */
trait JSONMarshaller {
  implicit val positionFormat = new Writes[GPSPosition] {
    def writes(position: GPSPosition) = Json.arr(
      "p",
      Some(position.lat),
      position.lon,
      position.ftmsl,
      position.ftagl
    )
  }

  implicit val pitchRollHeadingFormat = new Writes[PitchRollHeading] {
    def writes(pitchRollHeading: PitchRollHeading) = Json.arr(
      "prh",
      pitchRollHeading.pitch,
      pitchRollHeading.roll,
      pitchRollHeading.trueHeading,
      pitchRollHeading.magHeading
    )
  }

  implicit val speedFormat = new Writes[Speed] {
    def writes(speed: Speed) = Json.arr(
      "s",
      speed.indKias,
      speed.trueKtgs
    )
  }

  implicit val udpConnectionStatusFormat = new Writes[UDPConnectionStatus] {
    def writes(udpConnectionStatus: UDPConnectionStatus) = Json.arr(
      "u",
      udpConnectionStatus.status
    )
  }
}
