(ns test-reframe.views
  (:require
   [re-frame.core :as re-frame]
   [test-reframe.subs :as subs]
   [test-reframe.events :as events]
   [test-reframe.d3 :refer [d3-inner]]
   [test-reframe.db :refer [app-state]]
   [goog.object :as go]
   ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        clicks (re-frame/subscribe [::subs/click])
        data (re-frame/subscribe [::subs/data])]
    [:div
     [:h1 "Hello from " @name]
     [:h2 "You have clicked me " @clicks " times"]
     [:h3 (str (js->clj (go/getValueByKeys app-state @clicks)))]
     [:input {:type "button" :value "click me!!!!" :on-click #(re-frame/dispatch [::events/click])}]
     [:input {:type "button" :value "click me!!!!" :on-click #(re-frame/dispatch [::events/shuffle])}]
     [:br]
     [d3-inner @data]
     ]))
