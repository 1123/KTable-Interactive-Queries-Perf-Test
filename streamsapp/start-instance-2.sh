#!/bin/bash

set -e -u

export SPRING_PROFILES_ACTIVE=local2
mvn spring-boot:run
