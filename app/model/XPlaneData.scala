package model

case class XPlaneData(gps: Option[GPSCoords], pitchRollHeading: Option[PitchRollHeading], udpStatus: String)
