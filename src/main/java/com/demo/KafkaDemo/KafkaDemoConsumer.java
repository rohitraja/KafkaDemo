package com.demo.KafkaDemo;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.kafka.client.consumer.KafkaConsumer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KafkaDemoConsumer {

  KafkaConsumer<String, String> consumer;



  public KafkaDemoConsumer init (Vertx vertx){
    Map<String, String> config = new HashMap<>();
    config.put("bootstrap.servers", "localhost:9092");
    config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    config.put("group.id", "my_group");
    config.put("auto.offset.reset", "earliest");
    config.put("enable.auto.commit", "false");

// use consumer for interacting with Apache Kafka
    consumer = KafkaConsumer.create(vertx, config);


    consumer.handler(record -> {
      System.out.println("Processing key=" + record.key() + ",value=" + record.value() +
        ",partition=" + record.partition() + ",offset=" + record.offset());
    });

    // subscribe to several topics with list
    Set<String> topics = new HashSet<>();
    topics.add("MyFirstTopic");
    consumer.subscribe(topics);
    return this;
  }

}
