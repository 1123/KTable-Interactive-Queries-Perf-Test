#!/bin/bash

mvn clean package -Dmaven.test.skip=true
for vm in vm1 vm2 vm3; do
  gcloud compute scp target/interactive-queries-streams-app-0.0.1-SNAPSHOT.jar $vm:/tmp/ --zone us-central1-a
  gcloud compute scp start-on-gcp.sh $vm:/tmp/ --zone us-central1-a 
  gcloud compute ssh $vm --zone us-central1-a --command "sudo yum install -y java-11-openjdk-devel"
done

