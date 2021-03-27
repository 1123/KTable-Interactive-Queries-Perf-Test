#!/bin/bash

set -u -e

id=$1
urls=$2

for url in $urls; do
  echo ""
  echo "Querying instance at $url"
  curl $url/store/$id ||echo could not reach instance 1
  echo ""
done
