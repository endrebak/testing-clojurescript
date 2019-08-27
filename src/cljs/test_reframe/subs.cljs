(ns test-reframe.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))


(re-frame/reg-sub
 ::click
 (fn [db]
   (:click db)))


(re-frame/reg-sub
 ::data
 (fn [db]
   (:data db)))
