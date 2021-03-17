#!/bin/bash

set -e -u

echo "Stopping local Confluent Kafka"
confluent local services stop

echo "Cleaning up any local state"
confluent local destroy
rm -rf /tmp/kafka-streams/

echo "Starting up Confluent Kafka"
confluent local services kafka start

echo "Creating input topic"
kafka-topics --bootstrap-server localhost:9092 --create --topic processed-messages --partitions=12


