(ns test-reframe.d3
  (:require
   [cljs.test :as t :include-macros true]
   ["d3" :as d3]
   [test-reframe.db :refer [app-state]]
   [reagent.core :as reagent]))


(def window-width (* js/window.innerWidth 0.9))

(def margins
  {:top 15
   :right 15
   :bottom 40
   :left 60})


(def dimensions
  {:width window-width
   :height 400
   :bounded-width (- window-width (margins :left) (margins :right))
   :bounded-height (- 400 (margins :top) (margins :bottom))})

;; const y-accessor = d => d.temperatureMax
;; const x-accessor = d => d.date

(def date-parser (.timeParse d3 "%Y-%m-%d"))

(defn y-accessor [d]
    (.-temperatureMax d))

(defn x-accessor [d]
  (date-parser (.-date d)))

(def y-scale (..
              (.scaleLinear d3)
              (domain
               (.extent d3 app-state y-accessor))
              (range #js [(dimensions :bounded-height) 0])))


(def x-scale (..
              (.scaleLinear d3)
              (domain
               (.extent d3 app-state x-accessor))
              (range #js [0 (dimensions :bounded-width)])))


(def line-generator
  (..
   (.line d3)
   (x #(-> % x-accessor x-scale))
   (y #(-> % y-accessor y-scale))))


(defn d3-inner [data]
  (reagent/create-class
   {:reagent-render (fn [data] [:div#wrapper])

    :component-did-mount (fn []
                           (let [d3data (clj->js data)]
                             (js/console.log (clj->js (first data)))
                             (.. d3
                                 (select "#wrapper")
                                 (append "svg")
                                 (attr "width" (dimensions :width))
                                 (attr "height" (dimensions :height))
                                 (append "g")
                                 (style "transform" (str "translate(" (margins :left) "px, " (margins :top) "px)"))
                                 (append "path")
                                 (attr "d" (line-generator app-state))
                                 ;; (attr "d","M 0 0 L 500 0 L 100 100 L 0 50 Z")
                                 )))


    :component-did-update (fn [this]
                            (let [[_ data] (reagent/argv this)
                                  d3data (clj->js data)]
                              (js/console.log "Update")
                              (.. d3
                                  (selectAll "circle")
                                  (data d3data))))
    }))
