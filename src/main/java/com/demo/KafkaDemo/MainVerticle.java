package com.demo.KafkaDemo;



import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.config.ConfigRetriever;





public class MainVerticle extends AbstractVerticle {

    private ConfigRetriever configRetriever;

    @Override
    public void start(Future<Void> startFuture) throws Exception {

    ConfigStoreOptions fileOptions = new ConfigStoreOptions()
      .setType("file")
      .setFormat("properties")
      .setConfig(new JsonObject().put("path", "./src/conf/config.properties"));
    ConfigStoreOptions envOptions = new ConfigStoreOptions().setType("env");

    ConfigRetrieverOptions options = new ConfigRetrieverOptions()
      .addStore(fileOptions)
      .addStore(envOptions);

    configRetriever = ConfigRetriever.create(vertx, options);

    configRetriever.getConfig(ar -> {
      if (ar.succeeded()) {
        JsonObject config = ar.result();
        deployVerticle(config);
        startFuture.complete();
      } else {
        startFuture.fail(ar.cause());
      }
    });
  }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
    super.stop(stopFuture);
  }

    private void deployVerticle(JsonObject config) {
    DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(config);
    vertx.rxDeployVerticle(KafkaVertical.class.getName(), deploymentOptions).subscribe();
  }


}
