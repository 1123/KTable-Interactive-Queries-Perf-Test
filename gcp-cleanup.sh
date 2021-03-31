#!/bin/bash

set -e -u

# echo "Cleaning up any local state"
# TODO: remove the directory on each of the hosts, where the streams app is running. 

. $FUNCTIONS
. $CPA_ENV

echo "Deleting old data in input topic"
delete_topic processed-messages 

echo "Creating input topic"
create_topic processed-messages $NUM_PARTITIONS $REPLICATION_FACTOR




