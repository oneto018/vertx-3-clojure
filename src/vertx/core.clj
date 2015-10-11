(ns vertx.core
  (:require [camel-snake-kebab.core :refer :all]
            [vertx.util :as util])
  (:import (io.vertx.core Vertx Handler VertxOptions Future AsyncResult)
           (io.vertx.core.http HttpServer HttpServerOptions)))


(defn ^Vertx vertx
  "create vertx entry point."
  ([] 
   (Vertx/vertx))
  ([options]
   (let [^VertxOptions v-opt (VertxOptions.)
         conf-opts (util/configure-java-object v-opt options) ]
     (Vertx/vertx conf-opts))))

(defn ^Vertx vertx
  "create vertx entry point."
  ([] 
   (Vertx/vertx))
  ([options]
   (let [^VertxOptions v-opt (VertxOptions.)]
     (Vertx/vertx 
       (util/configure-java-object v-opt options)))))

(defn ^Vertx vertx
  "create vertx entry point."
  ([] 
   (Vertx/vertx))
  ([options]
   (let [^VertxOptions v-opt (VertxOptions.)]
     (Vertx/vertx))))


(defn ^HttpServer create-http-server
  "creates basic http server. "
  ([^Vertx vertx]
   (.createHttpServer vertx))
  ([^Vertx vertx options]
   (let [^HttpServerOptions http-server-opt (HttpServerOptions.)
         conf-opts (util/configure-java-object http-server-opt options)]
     (.createHttpServer vertx conf-opts))))


(defn execute-blocking
  "execute blocking code safely and delivers result with ordered=true"
  [^Vertx vertx  blocking-handler result-handler]
  (.executeBlocking 
    vertx 
    (util/get-handler* 
      (fn [^Future future] 
        (.complete future (blocking-handler))))
    (util/get-handler* 
      (fn [^AsyncResult res] 
        (result-handler (.result res))))))


(defn execute-blocking-low
  "execute blocking code safely and delivers result"
  [^Vertx vertx  blocking-handler result-handler]
  (.executeBlocking 
    vertx 
    (util/get-handler* blocking-handler)
    (util/get-handler* result-handler)))


