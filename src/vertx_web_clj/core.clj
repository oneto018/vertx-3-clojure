(ns vertx-web-clj.core
  (:require [vertx.core :as vertx]
            [vertx.http :as http]
            [vertx.route :as rt :refer [GET POST PUT DELETE]])
  (:import (io.vertx.core Vertx Handler Future AsyncResult)
           (io.vertx.core.http HttpServer HttpServerOptions 
                               HttpServerRequest HttpServerResponse)
            (io.vertx.ext.web RoutingContext Route Router)))


(defn block [sec]
  (Thread/sleep (* sec 1000))
  sec)

(defn blocking-handler 
  []
  (block 10))

(defn res-handler 
  [res]
  (println "after 30 secs " res))

(defn handle-t1 [req]
  (-> (http/response req)
      (http/response-end "t1 content")))

(defn handle-hello [req]
  (-> (http/response req)
      (http/response-end (str "hello " (http/param req "name") "!"))))

(defn handle-post-name [req]
  (-> (http/response req)
      (http/response-end "post request display")))

(defn main-router [vertx]
  (->
    (rt/router vertx)
    (GET "/" #(http/response-end (http/response %) "home page"))
    (GET "/t1" handle-t1)
    (POST "/p1" handle-post-name)
    (GET "/hello/:name" handle-hello)))


(defn listen-handler [server]
  (println "server started and running at port 8083" server))

(defn create-server [vertx]
  (->
    vertx
    (vertx/create-http-server)
    (http/request-handler (rt/route->handler (main-router vertx)))
    (http/listen 8083 listen-handler)))

(defn -main []
  (create-server (Vertx/vertx)))
