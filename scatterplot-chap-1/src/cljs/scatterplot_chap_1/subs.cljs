(ns scatterplot-chap-1.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::data
 (fn [db]
   (:data db)))
