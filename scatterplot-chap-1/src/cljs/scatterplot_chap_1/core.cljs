(ns scatterplot-chap-1.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [scatterplot-chap-1.events :as events]
   [scatterplot-chap-1.views :as views]
   [scatterplot-chap-1.config :as config]))


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
