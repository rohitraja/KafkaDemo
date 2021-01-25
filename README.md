# KafkaDemoopen
Kafka producer and consumer using vert.x

## Introduction
This is small example of creating Kafka Producer and Consumer using vertx-kafka-client.
This example has very bare minimum code to demo the example.

## Environment Setup.
First we need to have kafka server ready, up and running to use this demo project. 
### Option 1.Have your kafka setup ready with the help of Docker. 
If you are femiliar with docker. This is the easiest way to have your kafka server up and running. The only thing you have to worry about is that, the suggest docker image recomend to have 4GB of ram for this. Follow the steps below. 

### Prirequisites
You should have docker installed on you machine. 
```
$ docker run --rm --net=host landoop/fast-data-dev

```
That is all you need to do after this commnad get successfully executed. You will have your Zookeeper, Kafka server and topices ready to run the KafkaDemo.

### Option 2. Installing kafka on you local machine

### Prerequisits. If you are using Windows machine
Download and extract the convinient Kafka version .tgz from the site https://kafka.apache.org/downloads. 
Make sure you extract your kafka binary files up in the directory level (else Windows generally have issues executing long path commnads) like C:/kafka folder. Below are the steps to start Zookeeper, Kafka server, Producer and Consumer on windows machine. 

Step: 1
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

Step: 2. 
bin\windows\kafka-server-start.bat config\server.properties

Step: 3
bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic MyFirstTopic

Step: 4
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic MyFirstTopic

## Prerquists. If you are linux 
You should have kafka on your local machine. You can follow this instrunction to download and install kafka: https://tecadmin.net/install-apache-kafka-ubuntu/ or follow below step to start kafka if already installed just take care of the folder where it is installed. 

Step 1. Start Zookeeper
sudo ./bin/zookeeper-server-start.sh config/zookeeper.properties

Step 2. Start Kafka Server
sudo ./bin/kafka-server-start.sh config/server.properties

Step 3. Create Producer and topic
./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic MyFirstTopic


Step 4. Create Consumer to listen on topic
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic MyFirstTopic

Note: You can test you setup by sending some message to Kafka server on topic "MyFirstTopic" which should be received by consumer what we have created on 4th setp. 


### Run KafkaDemo proejct. 
Fork this repo and run folling commands. 

```
$ mvn clean package
$ java -jar target/KafkaDemo-1.0.0-SNAPSHOT-fat.jar 
```
You can use Postman test your application. 
