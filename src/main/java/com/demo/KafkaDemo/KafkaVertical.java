package com.demo.KafkaDemo;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;


public class KafkaVertical extends AbstractVerticle {
  private final Logger LOGGER = LoggerFactory.getLogger(KafkaVertical.class);
  public final VerticleContext verticleContext;
  private KafkaDemoProducer kafkaDemoProducer;
//  private KafkaDemoConsumer kafkaDemoConsumer;
  public KafkaVertical() {
    super();
    verticleContext = new VerticleContext();
  }


  @Override
  public void start(Future<Void> startFuture) {

    verticleContext.initVerticleContext(vertx, config());
    kafkaDemoProducer = verticleContext.getKafkaDemoProducer();
//    kafkaDemoConsumer = verticleContext.getKafkaDemoConsumer();

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get(config().getString("HEALTH_CHECK_API")).handler(this::healthCheck);

    //***************************************//

    router.post(config().getString("POST_HEALTH_CHECK_API")).handler(this::orderReturn).failureHandler(event -> event.response());
    router.post(config().getString("PUSH_TO_KAFA")).handler(this::pushToKafka).failureHandler(event -> event.response());;


    vertx.createHttpServer().requestHandler(router)
      .listen(config().getInteger("APP_PORT"), result -> {
        if (result.succeeded()) {
          startFuture.complete();
        } else {
          startFuture.fail(result.cause());
        }
      });
  }

  private void pushToKafka(RoutingContext routingContext ) {
    JsonObject reqBody = routingContext.getBodyAsJson();
    kafkaDemoProducer.sendDatatoTest("MyFirstTopic", reqBody)
    .subscribe(success->{
      routingContext.response().setStatusCode(200).end(routingContext.getBodyAsJson().toString());
      },
      failure->{
        routingContext.response().setStatusCode(400).end(new JsonObject().put("status", 400).encodePrettily());
      });
  }

  private void orderReturn(RoutingContext routingContext) {
    routingContext.response().setStatusCode(200)
      .end(routingContext.getBody());
  }

  private void healthCheck(RoutingContext rc) {
    rc.response().end("Kafka Demo is up and running at port  " + config().getInteger("APP_PORT"));
  }

  @Override
  public void stop(Future<Void> stopFuture) throws Exception {
    super.stop(stopFuture);
  }

}
