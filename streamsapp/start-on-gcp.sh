#!/bin/bash

set -u -e

export SPRING_PROFILES_ACTIVE=$1

curl https://github.com/jeanlouisboudart/kafka-platform-prometheus/blob/master/jmx-exporter/kafka-streams-post-2.5.yml -o /tmp/kafka-streams.yml
curl https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.15.0/jmx_prometheus_javaagent-0.15.0.jar -o/tmp/jmx_prometheus_javaagent-0.15.0.jar
export MAVEN_OPTS='-javaagent:/usr/share/jmx_exporter/jmx_prometheus_javaagent-0.12.0.jar=1234:/usr/share/jmx_exporter/kafka-streams-pre-2.5.yml'
/usr/lib/jvm/java-11-openjdk-11.0.10.0.9-1.el7_9.x86_64/bin/java \
  -jar /tmp/interactive-queries-streams-app-0.0.1-SNAPSHOT.jar

