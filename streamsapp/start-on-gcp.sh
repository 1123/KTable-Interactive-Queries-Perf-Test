#!/bin/bash

set -u -e

export SPRING_PROFILES_ACTIVE=$1

curl https://raw.githubusercontent.com/jeanlouisboudart/kafka-platform-prometheus/master/jmx-exporter/kafka-streams-post-2.5.yml -o /tmp/kafka-streams.yml
curl https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.15.0/jmx_prometheus_javaagent-0.15.0.jar -o/tmp/jmx_prometheus_javaagent-0.15.0.jar
export OPTS='-javaagent:/tmp/jmx_prometheus_javaagent-0.15.0.jar=1234:/tmp/kafka-streams.yml'
/usr/lib/jvm/java-11-openjdk-11.0.10.0.9-1.el7_9.x86_64/bin/java \
  $OPTS -jar /tmp/interactive-queries-streams-app-0.0.1-SNAPSHOT.jar

