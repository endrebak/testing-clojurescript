(ns scatterplot-chap-1.db
  (:require [shadow.resource :as rc]))

(def app-state
  (->> (rc/inline "./my_weather_data.json")
       (.parse js/JSON)))

(def default-db
  {:name "re-frame" :data app-state})
