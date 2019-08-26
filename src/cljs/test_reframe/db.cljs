(ns test-reframe.db
  (:require [shadow.resource :as rc]))

(def app-state
  (->> (rc/inline "./my_weather_data.json")
       (.parse js/JSON)
       (js->clj)))



(def default-db
  {:name "re-frame" :click 0})
