(ns test-reframe.db
  (:require [shadow.resource :as rc]))

(def app-state
  (->> (rc/inline "./test.json")
       (.parse js/JSON)
       ))



(def default-db
  {:name "re-frame" :click 0 :data app-state})
