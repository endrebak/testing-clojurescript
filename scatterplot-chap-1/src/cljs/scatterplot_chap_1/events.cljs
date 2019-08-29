(ns scatterplot-chap-1.events
  (:require
   [re-frame.core :as re-frame]
   [scatterplot-chap-1.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))


;; (defn shuffle [data]
;;   (map))

(re-frame/reg-event-db
 ::shuffle
 (fn [db _]
   (update-in db [:data] shuffle)))
