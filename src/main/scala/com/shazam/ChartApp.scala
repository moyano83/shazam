package com.shazam

import com.shazam.service.MatchQueryService
import com.shazam.util.AppConstants._
import org.apache.spark.sql.SparkSession

import scala.util.{Failure, Success, Try}

object ChartApp {

  /**
    * Map containing the allowed commands for this application
    */
  val commandsMap = AllowedCommands.values.map(command => (command.toString -> command)).toMap
  /**
    * Map containing the header to display for each command
    */
  val headersMap = Map(AllowedCommands.chart -> ChartHeader, AllowedCommands.state_chart-> StateChartHeader)
  /**
    * Name of the file to use
    * Note: This parameter can be change to a variable passed as a program argument
    */
  val pathToFile = "shazamtagdata.json.gz"

  /**
    * Application entry point
    * @param args application parameters
    */
  def main(args:Array[String]):Unit = {
    val arguments = parseArgs(args)
    if(!arguments.isDefined) printUsage
    else{
      val sparkSession = SparkSession.builder().appName("ShazamCharts").getOrCreate()
      arguments.foreach(parsedArg => {
        runCommand(sparkSession, pathToFile, parsedArg._1, parsedArg._2).foreach(item => println(item.toString))
      })
    }
  }

  /**
    * Runs the command that performs the calculations
    * @param sparkSession the spark session to use
    * @param pathToFile path to the gz file
    * @param command chart/state_chart
    * @param topN rank size to display
    * @return
    */
  def runCommand(sparkSession: SparkSession, pathToFile:String, command:AllowedCommands.Value , topN:Int):List[Any] = {
    val queryService = new MatchQueryService(sparkSession, pathToFile)
    command match{
      case AllowedCommands.chart =>  headersMap.getOrElse(AllowedCommands.chart, "CHARTS") ::
        queryService.findTopMatch(topN)
      case AllowedCommands.state_chart => headersMap.getOrElse(AllowedCommands.state_chart, "STATE CHARTS") ::
        queryService.findTopMatchByUSState(topN)
    }
  }

  /**
    * Parses de input arguments
    * @param args the application input arguments
    * @return An option containing a tuple with the command and the number of rows to retrieve, None if the arguments
    * were invalid
    */
  def parseArgs(args:Array[String]):Option[(AllowedCommands.Value, Int)] =
    if(args.size != 2 || !commandsMap.get(args(0).toLowerCase()).isDefined) None
    else Try(args(1).toInt) match{
        case Failure(_) => None
        case Success(topNValue) => if(topNValue<=0) None
          else commandsMap.get(args(0).toLowerCase()).map(command => (command, topNValue))
    }

  /**
    * Displays a help message on the screen for the user
    */
  def printUsage = println("""
      |***********************************************************************************************
      |* Usage of the shazam chart finder:                                                           *
      |* shell> ./chart_calculator.sh <Char Option> <Number to display>                              *
      |* Where:                                                                                      *
      |*   Char Option: can be either 'chart' or 'state_chart'                                       *
      |*   Number to display: Display the top N Tags on shazam. N must be a number greater than 0    *
      |***********************************************************************************************
    """.stripMargin)
}
