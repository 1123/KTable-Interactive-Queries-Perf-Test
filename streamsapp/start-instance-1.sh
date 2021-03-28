#!/bin/bash

set -e -u

curl https://github.com/jeanlouisboudart/kafka-platform-prometheus/blob/master/jmx-exporter/kafka-streams-post-2.5.yml -o /tmp/kafka-streams.yml
curl https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.15.0/jmx_prometheus_javaagent-0.15.0.jar -o/tmp/jmx_prometheus_javaagent-0.15.0.jar
export MAVEN_OPTS='-javaagent:/usr/share/jmx_exporter/jmx_prometheus_javaagent-0.12.0.jar=1234:/usr/share/jmx_exporter/kafka-streams-pre-2.5.yml'
export SPRING_PROFILES_ACTIVE=local1
mvn spring-boot:run 
