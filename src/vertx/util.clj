(ns vertx.util
  (import io.vertx.core.Handler)
  (:require [camel-snake-kebab.core :refer :all]
            [clojure.string :as s]))

;http://stackoverflow.com/questions/1710970/dynamic-method-calls-in-a-clojure-macro
(defmacro camel-case-config
   "General macro to apply setters to java object by passing map. map keys will be converted to camelCase setter methods"
  [object options]
  `(doto ~object
     ~@(map (fn [[property value]]
              (let [property (name property)
                    setter   (->camelCase (str ".set-" property))]
                `(~(symbol setter) ~value)))
            options)))


;https://github.com/vert-x/mod-lang-clojure/blob/master/api/src/main/clojure/vertx/utils.clj
(defn camelize [input]
  (let [words (s/split (name input) #"[\s_-]+")]
    (s/join (cons (.toLowerCase ^String (first words))
              (map #(str (.toUpperCase (subs % 0 1)) (subs % 1))
                (rest words))))))

(defn set-property [obj prop value]
  (clojure.lang.Reflector/invokeInstanceMethod
    obj
    (->> prop name (str "set-") camelize)
    (into-array Object [value]))
  obj)

(defn configure-java-object [obj props]
  (mapv (fn [[prop value]]
          (set-property obj prop value)) props)
  obj)



(defn ^Handler get-handler* [f]
  (reify io.vertx.core.Handler 
    (handle [this req]
      (f req))))