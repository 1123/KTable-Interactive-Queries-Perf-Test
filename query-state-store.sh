#!/bin/bash

set -u -e

ids=$1
urls=$2

for id in $ids; do
  for url in $urls; do
    echo ""
    echo "Querying instance at $url"
    curl $url/store/$id ||echo could not reach instance 1
    echo ""
  done
done

