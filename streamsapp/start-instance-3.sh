#!/bin/bash

set -e -u

export APP_HOST=localhost
export APP_PORT=7003

mvn spring-boot:run
