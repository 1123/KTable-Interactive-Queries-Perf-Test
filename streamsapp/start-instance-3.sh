#!/bin/bash

set -e -u

export SPRING_PROFILES_ACTIVE=local3
mvn spring-boot:run
