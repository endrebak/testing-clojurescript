(ns scatterplot-chap-1.views
  (:require
   [re-frame.core :as re-frame]
   [scatterplot-chap-1.subs :as subs]
   [scatterplot-chap-1.events :as events]
   [scatterplot-chap-1.d3 :as d3]
   ))


(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        data (re-frame/subscribe [::subs/data])]
    [:div
     [:h1 "Hello from " @name]
     [:h2 "Width is " d3/width]
     [:h3 "first datapoint " (str (js->clj (first @data)))]
     [:input {:type "button" :value "Click me!" :on-click #(re-frame/dispatch [::events/shuffle])}]
     [d3/d3-chart @data]]))
