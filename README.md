# KTable-Interactive-Queries-Perf-Test
Spring Boot KTable Perf Test

Microservices often communicate with each other via REST calls. Yet this introduces synchronous runtime dependencies between otherwise independent systems. 
Introducing a large number of these point to point runtime dependencies results in brittle systems. 

This demo shows how to use asynchronous communication instead by builing a materialized view on a Kafka topic within a Spring Boot application. 
It allows to do performance benchmarking when building large materialized views over Kafkat topics. 

The demo consists of two components:

* The producer-app produces a large amount of messages to a topic in Kafka.

* The interactive-query-app reads these messages into a Kafka Streams KTable. 
  Kafka Streams interactive queries are used to query the RocksDB state store that the Kafka Streams app is building on disk. 
  The state store is exposed over a REST interface using Spring-Kafka-Rest. 
  
### Prerequisites:

* Confluent Platform or Kafka 
* maven
* Java JDK

### Running the demo locally

* Cleanup any state from previous runs, restart Confluent Platform, and create the input topic as scripted in `./cleanup.sh`.

* Adjust the configs in `producerapp/src/main/resources/application.yaml` and `streamsapp/src/main/resources/kafkastreams-local{1,2,3}.properties` to point to your Kafka cluster. If you are running Confluent Platform locally with default configuration, no configurations must be changed. 

* Run the producer app: `cd prodcuerapp && ./run-producer.sh`

* Run the streams app: 
  * `cd streamsapp`
  * `./start-instance-1.sh`
  * `./start-instance-2.sh`
  * `./start-instance-3.sh`
  * Inspect the `/tmp/kafka-streams` directory to see the RocksDB state stores being built: `du -sh /tmp/kafka-streams/*`.  
  * Watch the logs of the streams app to see the state store being populated (offsets being committed). 

* Copy some message keys from the output of the producer app, and use them to query the state store through the rest interface: `./query-state-store.sh "7dc6a760-9d34-4b21-9722-3fc7c56e1858 739b4c8b-9e59-4286-b47f-1466854ae259 c6b41d81-7da3-4f60-82ef-da3e58371f5a" "localhost:7001 localhost:7002 localhost:7003" ` 




