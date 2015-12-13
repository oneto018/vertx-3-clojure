# vertx-web-clj

A proof of concept code to use vertx3 with leiningen and clojure. Eventually need to create clojure wrapper library for vertx 3. For now few utilities and warpper functions for vertx core and vertx web is written .


## Usage
to run this repo
-----------------
```
git clone git@github.com:oneto018/vertx-3-clojure.git

cd vertx-3-clojure

lein run
```
then check urls

[http://localhost:8083/](http://localhost:8083/)
[http://localhost:8083/t1]([http://localhost:8083/t1)
[http://localhost:8083/hello/somename](http://localhost:8083/hello/somename)


how it looks like
------------------
full expamle is in [src/vertx-web_clj/core.clj](https://github.com/oneto018/vertx-3-clojure/blob/master/src/vertx_web_clj/core.clj)

```clojure
	(defn handle-t1 [req]
	  (-> (http/response req)
	      (http/response-end "t1 content")))

	(defn handle-hello [req]
	  (-> (http/response req)
	      (http/response-end (str "hello " (http/param req "name") "!"))))

	(defn handle-post-name [req]
		(-> (http/response req)
				(http/response-end (str "post request display - name: " (http/param req "name")))))

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
```

simple-handler
--------------
```clojure

	(-> (Vertx/vertx)
		(vertx/create-http-server)
		(http/request-handler
			#(http/response-end (http/response %) "hello world from clojure vertx3"))
		(http/listen 8083))
```

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
