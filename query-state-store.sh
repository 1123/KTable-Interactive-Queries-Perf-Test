#!/bin/bash

set -u -e

curl localhost:8080/store/$1
