#!/bin/bash

set -u -e

export SPRING_PROFILES_ACTIVE=$1

/usr/lib/jvm/java-11-openjdk-11.0.10.0.9-1.el7_9.x86_64/bin/java \
  -jar /tmp/interactive-queries-streams-app-0.0.1-SNAPSHOT.jar
