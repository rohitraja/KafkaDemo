# KafkaDemo
Kafka producer and consumer using vert.x



### Kafka installation on windows

Step: 1
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

Step: 2. 
bin\windows\kafka-server-start.bat config\server.properties

Step: 3
bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic MyFirstTopic

Step: 4
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic MyFirstTopic
