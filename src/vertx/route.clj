(ns vertx.route
  (:require [vertx.util :as util])
  (:import (io.vertx.core.http HttpServer HttpServerOptions 
                               HttpServerRequest HttpServerResponse
                               HttpMethod)
           (io.vertx.ext.web RoutingContext Route Router)
           (io.vertx.core Vertx Handler)))


(defn router ^Router [^Vertx vertx]
  (Router/router vertx))

(defmacro def-route [method]
  `(defn ~method ^Router [^router ~'router ^String ~'route ~'handler]
     (-> 
       (.route ~'router (~(symbol (str "HttpMethod/" method))) ~'route)
       (.handler (util/get-handler* ~'handler)))))

(defmacro def-block-route [method]
  `(defn ~(symbol (str "BL-" method)) ^Router 
    ([^router ~'router ^String ~'route ~'handler]
     (-> 
       (.route ~'router (~(symbol (str "HttpMethod/" method))) ~'route)
       (.blockingHandler (util/get-handler* ~'handler) false)))
    ([^router ~'router ^String ~'route ~'handler ^boolean ~'orderered]
     (-> 
       (.route ~'router (~(symbol (str "HttpMethod/" method))) ~'route)
       (.blockingHandler (util/get-handler* ~'handler) orderered)))))

(comment 
(defn GET ^Router [^Router router ^String route handler]
  (-> (.route router (HttpMethod/GET) route)
        (.handler (util/get-handler* handler)))
  router))

(def-route GET)
(def-route POST)
(def-route PUT)
(def-route DELETE)
(def-route CONNECT)
(def-route HEAD)
(def-route OPTIONS)
(def-route PATCH)
(def-route TRACE)

(def-block-route GET)
(def-block-route POST)
(def-block-route PUT)
(def-block-route DELETE)
(def-block-route CONNECT)
(def-block-route HEAD)
(def-block-route OPTIONS)
(def-block-route PATCH)
(def-block-route TRACE)


(defn route->handler
  "convert router object to http request handler. returns a http handler function"
   [^Router router]
  (fn [req]
    (.accept router req)))