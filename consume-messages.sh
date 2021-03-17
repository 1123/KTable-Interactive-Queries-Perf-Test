#!/bin/bash

set -u -e

kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic processed-messages \
  --property print.key=true \
  --max-messages 10 \
  --from-beginning 


