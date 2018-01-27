package com.shazam.service

import com.shazam.model.{Chart, StateChart}
import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfter, FlatSpec}

class MatchQueryServiceTest extends FlatSpec with BeforeAndAfter{

  val testSession = SparkSession.builder().master("local[1]").appName("ShazamTest").getOrCreate()
  val testDataUrl = getClass.getClassLoader.getResource("shazamtagdatatest.json.gz")
  val matchQueryService = new MatchQueryService(testSession, testDataUrl.getPath)

  it should "find the top 2 matches" in {
    val chartArray = matchQueryService.findTopMatch(2)
    assert(chartArray.size == 4)
    assert(chartArray == List(Chart(1, "Like I'm Gonna Lose You", "Meghan Trainor"),
    Chart(1, "Believer", "Imagine Dragons"),
    Chart(2, "Even If", "MercyMe"),
    Chart(2, "Catch Me", "Nicole Beharie")))
  }

  it should "find the top 1 matches" in {
    val chartArray = matchQueryService.findTopMatch(1)
    assert(chartArray.size == 2)
    assert(chartArray == List(Chart(1, "Like I'm Gonna Lose You", "Meghan Trainor"),
      Chart(1, "Believer", "Imagine Dragons")))
  }

  it should "find the top 10 matches" in {
    val chartArray = matchQueryService.findTopMatch(10)
    assert(chartArray.size == 7)
    assert(chartArray ==  List(Chart(1, "Like I'm Gonna Lose You", "Meghan Trainor"),
      Chart(1, "Believer", "Imagine Dragons"),
      Chart(2, "Even If", "MercyMe"),
      Chart(2, "Catch Me", "Nicole Beharie"),
      Chart(3, "Wish I Knew You", "The Revivalists"),
      Chart(3, "Swang", "Rae Sremmurd"),
      Chart(3, "Nothing Compares 2 U", "Chris Cornell")))
  }

  it should "find the top 2 matches by US state" in {
    val chartArray = matchQueryService.findTopMatchByUSState(2)
    assert(chartArray == List(
      StateChart(1,	"TX",	"Even If",	"MercyMe"),
      StateChart(2,	"TX",	"Swang", "Rae Sremmurd"),
      StateChart(1,	"FL",	"Like I'm Gonna Lose You", "Meghan Trainor"),
      StateChart(2,	"FL",	"Catch Me", "Nicole Beharie"),
      StateChart(2,	"FL",	"Believer", "Imagine Dragons")))
  }

  it should "find the top 1 matches by US state" in {
    val chartArray = matchQueryService.findTopMatchByUSState(1)
    assert(chartArray == List(
      StateChart(1,	"TX",	"Even If",	"MercyMe"),
      StateChart(1,	"FL",	"Like I'm Gonna Lose You", "Meghan Trainor")))
  }

  it should "find the top 100 matches by US state" in {
    val chartArray = matchQueryService.findTopMatchByUSState(100)
    assert(chartArray == List(
      StateChart(1,	"TX",	"Even If",	"MercyMe"),
      StateChart(2,	"TX",	"Swang", "Rae Sremmurd"),
      StateChart(1,	"FL",	"Like I'm Gonna Lose You", "Meghan Trainor"),
      StateChart(2,	"FL",	"Catch Me", "Nicole Beharie"),
      StateChart(2,	"FL",	"Believer", "Imagine Dragons"),
      StateChart(3,	"FL",	"Wish I Knew You", "The Revivalists")))
  }
}
