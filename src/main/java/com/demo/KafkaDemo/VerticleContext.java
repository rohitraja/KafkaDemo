package com.demo.KafkaDemo;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;

public class VerticleContext {
  private static final Logger LOGGER = LoggerFactory.getLogger(VerticleContext.class);
  private Vertx vertx;
  private KafkaDemoProducer kafkaDemoProducer;
  private KafkaDemoConsumer kafkaDemoConsumer;
  public VerticleContext() {
  }

  public void initVerticleContext(Vertx vertx, JsonObject config)  {
    JsonObject applicationConfig = config;
    if (null == this.vertx) {
      this.vertx = vertx;
    }

    System.out.println("Going to initialize kafkaClient"+ this.kafkaDemoProducer);
    if(null == this.kafkaDemoProducer) {
      KafkaDemoProducer initClient = new KafkaDemoProducer();
      this.kafkaDemoProducer = initClient.init(vertx,new JsonObject());
    }


    System.out.println("Going to initialize kafka consumer"+ this.kafkaDemoProducer);
    if(null == this.kafkaDemoConsumer) {
      KafkaDemoConsumer initConsumer = new KafkaDemoConsumer();
      this.kafkaDemoConsumer = initConsumer.init(vertx);
    }
  }


  public Vertx getVertx() {
    return vertx;
  }

  public KafkaDemoProducer getKafkaDemoProducer() {
    return kafkaDemoProducer;
  }

  public KafkaDemoConsumer getKafkaDemoConsumer() {
    return kafkaDemoConsumer;
  }
}




