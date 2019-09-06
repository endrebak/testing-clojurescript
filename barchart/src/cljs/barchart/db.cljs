(ns barchart.db
  (:require
   [barchart.resources :refer [app-data name2]]))

(def default-db
  {:name "re-frame" :data app-data})
