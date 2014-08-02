package model

case class XPlaneData(position: Option[GPSPosition], pitchRollHeading: Option[PitchRollHeading], udpStatus: String)
