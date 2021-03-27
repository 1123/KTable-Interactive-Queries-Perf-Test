#!/bin/bash

set -e -u

export SPRING_PROFILES_ACTIVE=local1
mvn spring-boot:run 
