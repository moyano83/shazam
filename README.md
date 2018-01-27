# Chart App Shazam

## Technology Stack
The application needs the following components in order to run:

    * Scala 2.11
    * Spark 2.2.0

## Execution
To create the package that the application needs in order to run please execute the following command:

`sbt package`

To run the char application you need to execute the following command from the root project:

`./chart_calculator.sh <char type> <number of results>`

The output of this command will be displayed in the screen as follows:
```text
...
17/10/12 14:56:26 INFO TaskSchedulerImpl: Removed TaskSet 3.0, whose tasks have all completed, from pool
17/10/12 14:56:26 INFO DAGScheduler: ResultStage 3 (collect at MatchQueryService.scala:24) finished in 0.781 s
17/10/12 14:56:26 INFO DAGScheduler: Job 1 finished: collect at MatchQueryService.scala:24, took 7.304095 s
17/10/12 14:56:26 INFO CodeGenerator: Code generated in 11.438209 ms
CHART POSITION	TRACK TITLE	ARTIST NAME
1	Crying In The Club	Camila Cabello
2	Redbone	Childish Gambino
3	I'm The One	DJ Khaled Feat. Justin Bieber & Quavo & Chance The Rapper & Lil Wayne
17/10/12 14:56:26 INFO SparkContext: Invoking stop() from shutdown hook
17/10/12 14:56:26 INFO SparkUI: Stopped Spark web UI at http://172.27.77.184:4040
17/10/12 14:56:26 INFO MapOutputTrackerMasterEndpoint: MapOutputTrackerMasterEndpoint stopped!
```