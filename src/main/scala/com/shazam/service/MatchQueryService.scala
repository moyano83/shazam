package com.shazam.service

import com.shazam.model.{Chart, StateChart}
import com.shazam.util.AppConstants._
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

class MatchQueryService(sparkSession: SparkSession, pathToFile:String) {
  /**
    * Data frame to operate with
    * @return the dataframe representing the test file
    */
  private def getDataFrame:DataFrame = sparkSession.read.json(pathToFile)

  /**
    * Finds the topN most tagged songs
    * @param topN size of the rank to display
    * @return a List representing the chart tags
    */
  def findTopMatch(topN:Int): List[Chart] = {
    import sparkSession.implicits._
    val tagsDataFrame = getDataFrame
    tagsDataFrame.groupBy(
        tagsDataFrame(TrackTitleColumnPath).as(TrackTitleColumnAlias),
        tagsDataFrame(ArtistNameColumnPath).as(ArtistNameColumnAlias))
      .count().as(CountColumnAlias)
      .select("*")
      .withColumn(ChartPositionColumnAlias, dense_rank().over(Window.orderBy($"$CountColumnAlias".desc)))
      .filter($"$ChartPositionColumnAlias" <= topN)
      .map(row => Chart(row.getInt(3), row.getString(0), row.getString(1)))
      .collect().toList
  }

  /**
    * Finds the topN most tagged son by US state
    * @param topN the size of the rank to display per state
    * @return a List representing the state char tags
    */
  def findTopMatchByUSState(topN:Int): List[StateChart] = {
    import sparkSession.implicits._
    val tagsDataFrame = getDataFrame
    tagsDataFrame
      .filter($"$CountryColumnPath" === "United States")
      .select(
        tagsDataFrame(StateColumnPath).as(StateColumnAlias),
        tagsDataFrame(TrackTitleColumnPath).as(TrackTitleColumnAlias),
        tagsDataFrame(ArtistNameColumnPath).as(ArtistNameColumnAlias))
      .groupBy(StateColumnAlias, TrackTitleColumnAlias, ArtistNameColumnAlias).count.as(CountColumnAlias)
      .withColumn(ChartPositionColumnAlias,
        dense_rank().over(Window.partitionBy(StateColumnAlias).orderBy($"$CountColumnAlias".desc)))
      .filter($"$ChartPositionColumnAlias" <= topN).drop(CountColumnAlias)
      .map(row => {
        StateChart(row.getInt(3), row.getString(0), row.getString(1), row.getString(2))
    })
      .collect().toList
  }
}
