#!/bin/bash

set -e -u

# echo "Stopping local Confluent Kafka"
# confluent local services stop
# Not recreating brokers in this environment. 

# echo "Cleaning up any local state"
# confluent local destroy
# rm -rf /tmp/kafka-streams/
# TODO: remove the directory on each of the hosts, where the streams app is running. 

# echo "Starting up Confluent Kafka"
# confluent local services kafka start
# not recreating brokers in this environment

. $FUNCTIONS
. $CPA_ENV

echo "Deleting old data in input topic"
delete_topic processed-messages 

echo "Creating input topic"
create_topic processed-messages $NUM_PARTITIONS $REPLICATION_FACTOR




