(ns test-reframe.events
  (:require
   [re-frame.core :as re-frame]
   [test-reframe.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))


(re-frame/reg-event-db
 ::click
 (fn [db _]
   (update-in db [:click] inc)))


(re-frame/reg-event-db
 ::shuffle
 (fn [db _]
   (update-in db [:data] shuffle)))
