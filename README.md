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

### Running the demo

* adjust the configs in `producerapp/src/main/resources/application.yaml` and `streamsapp/src/main/resources/application.yaml` to point to your Kafka cluster. 

* Run the producer app: `cd prodcuerapp && mvn spring-boot:run`

* Run the streams app: `cd streamsapp && mvn spring-boot:run`

* Watch the logs of the streams app to see the state store being populated (offsets being committed). 

* Watch the size of the Rocksdb state store on disk: `du -sh /tmp/kafka-streams/`

* Query the state store through the rest interface: `./query-state-store.sh` 




