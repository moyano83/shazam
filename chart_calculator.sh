#!/bin/bash

CHARTS_JAR="target/scala-2.11/shazam_2.11-0.1.jar"
which spark-submit >/dev/null
if [ $? -ne 0 ]; then
    echo "Please add spark 2.2.0 to your PATH in order to run the charts"
    exit -1
fi

if [ ! -f $CHARTS_JAR ]; then
    echo "In order to run the charts application, please add sbt to your path, and run 'sbt package'"
    exit -1
fi

spark-submit --files "shazamtagdata.json.gz" --class "com.shazam.ChartApp" --master "local[1]" $CHARTS_JAR $1 $2