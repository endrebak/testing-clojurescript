(ns test-reframe.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [test-reframe.events :as events]
   [test-reframe.views :as views]
   [test-reframe.config :as config]
   [test-reframe.d3 :as d3]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
