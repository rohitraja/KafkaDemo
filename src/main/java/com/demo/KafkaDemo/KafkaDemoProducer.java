package com.demo.KafkaDemo;


import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.kafka.client.producer.RecordMetadata;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.kafka.client.producer.KafkaProducer;
import io.vertx.reactivex.kafka.client.producer.KafkaProducerRecord;
import kafka.Kafka;


import java.util.HashMap;
import java.util.Map;

public class KafkaDemoProducer {

  KafkaProducer<String, String> producer;
  Map<String, String> config = new HashMap<>();
  private Vertx vertx;

  private final Logger LOGGER = LoggerFactory.getLogger(Kafka.class);


  public KafkaDemoProducer init(Vertx vertx, JsonObject vertxConfig) {
    this.vertx = vertx;
    System.out.println("Init-----> 1");
    try{
      config.put("bootstrap.servers", "localhost:9092");
      config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
      config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
      config.put("acks", "1");
      config.put("retries", "10");
      config.put("session.timeout.ms", "2000");
      config.put("request.timeout.ms", "1000");
      config.put("transaction.timeout.ms","2000");
      config.put("max.block.ms", "2000");
      System.out.println("Init----->2 ");

      // use producer for interacting with Apache Kafka
      KafkaProducer.create(vertx, config);
      producer = KafkaProducer.create(vertx, config);
      System.out.println("Init-----> 3 ");
      return this;
    }catch (Exception e){
      System.out.println(e.toString());
    }

    return this;
  }


  public Single<RecordMetadata> sendDatatoTest(String topicName , JsonObject data){

    KafkaProducerRecord<String, String> record = KafkaProducerRecord.create(topicName, data.toString());
    KafkaProducer producer=KafkaProducer.create(vertx,config);
    return producer.rxWrite(record).doOnError(re->LOGGER.info("Error occurred while sending data to topic Name "+topicName));

  }


}


