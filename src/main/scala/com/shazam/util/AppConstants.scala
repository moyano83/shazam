package com.shazam.util

object AppConstants {

  object AllowedCommands extends Enumeration {
    val chart, state_chart = Value
  }


  val CountColumnAlias = "COUNT"
  val ChartPositionColumnAlias = "CHART POSITION"
  val ArtistNameColumnPath = "match.track.metadata.artistname"
  val ArtistNameColumnAlias = "ARTIST NAME"
  val TrackTitleColumnPath = "match.track.metadata.tracktitle"
  val TrackTitleColumnAlias = "TRACK TITLE"
  val StateColumnPath = "geolocation.zone"
  val StateColumnAlias = "STATE"
  val CountryColumnPath = "geolocation.region.country"

  val printSeparator = "\t"
  val ChartHeader = Array(ChartPositionColumnAlias, TrackTitleColumnAlias, ArtistNameColumnAlias).mkString(printSeparator)
  val StateChartHeader = Array(ChartPositionColumnAlias, StateColumnAlias, TrackTitleColumnAlias, ArtistNameColumnAlias).mkString(printSeparator)
}
