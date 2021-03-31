#!/bin/bash

set -u -e

for vm in vm1 vm2 vm3; do
  for endpoint in metadata threadmetadata; do
    echo "Running curl $vm:7000/$endpoint"
    curl $vm:7000/$endpoint | jq .
  done
done
