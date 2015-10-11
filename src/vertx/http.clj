(ns vertx.http
  (:require [vertx.util :as util])
  (:import (io.vertx.core.http HttpServer HttpServerOptions 
                               HttpServerRequest HttpServerResponse)
           (io.vertx.ext.web RoutingContext)
           (io.vertx.core Vertx Handler)))

(defprotocol RequestLike
  "unifies the request like objects "
  (^HttpServerResponse response [this])
  (^String param [this key-name]))

(extend-protocol RequestLike
  
  io.vertx.core.http.HttpServerRequest
  (response [^HttpServerRequest this]
    (.response this))
  
  (param [^HttpServerRequest this ^String key-name]
    (.getParam this key-name))
  
  
  io.vertx.ext.web.RoutingContext
  (response [^RoutingContext this]
    (.response this))

  (param [^RoutingContext this ^String key-name]
    (.getParam (.request this) key-name)))


(defn ^HttpServer request-handler 
  "assigns a request handler to http server"
  ([^HttpServer server]
   (.requestHandler server))
  ([^HttpServer server handler-fn]
   (.requestHandler server (util/get-handler* handler-fn))))


(defn ^HttpServer listen
  "listen to a port for the request handler"
  ([^HttpServer server]
   (.listen server))
  ([^HttpServer server ^long port]
   (.listen server port))
  ([^HttpServer server ^long port handler-fn]
   (.listen server (int port) (util/get-handler* handler-fn))))

(comment 
  (defn ^HttpServerResponse response
  "constructs a HttpServerResponse from request "
  ([^HttpServerRequest req]
   (.response req))))

(defn response-end
  "write a given string to response and finishes it"
  ([^HttpServerResponse res]
   (.end res))
  ([^HttpServerResponse res ^String content]
   (.end res content))
  ([^HttpServerResponse res ^String content ^String enc]
   (.end res content enc)))


(defn ^HttpServerResponse response-write
  "write a given string to response"
  ([^HttpServerResponse res ^String content]
   (.write res content))
  ([^HttpServerResponse res ^String content ^String enc]
   (.write res content enc)))