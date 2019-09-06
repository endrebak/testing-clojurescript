(ns barchart.resources
  (:require
   [cljs.test :as t :include-macros true]
   [shadow.resource :as rc]))


(def app-data
  (->> (rc/inline "./my_weather_data.json")
       (.parse js/JSON)))



(def name2
  (->> (rc/inline "./my_name.json")
       (.parse js/JSON)
       (.-name)))
