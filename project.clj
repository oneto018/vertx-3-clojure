(defproject vertx-web-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [io.vertx/vertx-core "3.1.0"]
                 [io.vertx/vertx-web "3.1.0"]
                 [camel-snake-kebab "0.3.2"]]
  :main vertx-web-clj.core

  :global-vars {*warn-on-reflection* true})
