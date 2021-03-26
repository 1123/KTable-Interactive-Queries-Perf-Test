#!/bin/bash

set -u -e

for id in 340f9b36-1939-49e5-9e63-b131b38ea0e4 102075ee-33b9-439c-9dc2-d78e1c35fda4 2893d86c-ba08-4573-9cd8-7248870bc529 400c5321-4bac-4e59-b7de-a0597f934a11 c428c89b-051f-4968-bf01-f5e2c2e2adf7; do
	echo ""
	echo "Querying instance 1"
	echo ""
	curl localhost:7001/store/$id ||echo could not reach instance 1
	echo ""
	echo "Querying instance 2"
	echo ""
	curl localhost:7002/store/$id ||echo could not reach instance 2
	echo ""
	echo "Querying instance 3"
	echo ""
	curl localhost:7003/store/$id ||echo could not reach instance 3
done
