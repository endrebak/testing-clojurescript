(ns barchart.views
  (:require
   [re-frame.core :as re-frame]
   [barchart.subs :as subs]
   [barchart.d3 :as d3]
   ))


(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from me, my name is " @name]]))
