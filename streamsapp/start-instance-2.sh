#!/bin/bash

set -e -u

export APP_HOST=localhost
export APP_PORT=7002

mvn spring-boot:run
