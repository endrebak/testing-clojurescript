(ns scatterplot-chap-1.d3
  (:require
   ["d3" :as d3]
   [scatterplot-chap-1.db :refer [app-state]]
   [cljs.test :as t :include-macros true]
   [reagent.core :as reagent]))

(enable-console-print!)

(def width
  (let [win js/window]
    (* 0.9
       (.min d3 #js [win.innerWidth win.innerHeight]))))


(def margins
  {:top 10 :right 10 :bottom 50 :left 50})

(def dimensions
  {:width width :height width
   :bounded-width (- width (margins :left) (margins :right))
   :bounded-height (- width (margins :top) (margins :bottom))})


(defn x-accessor [d]
  (.-dewPoint d))

(defn y-accessor [d]
  (.-humidity d))


(def x-scale (..
              (.scaleLinear d3)
              (domain (.extent d3 app-state x-accessor))
              (range #js [0 (dimensions :bounded-width)])
              nice))



(def y-scale (..
              (.scaleLinear d3)
              (domain (.extent d3 app-state y-accessor))
              (range #js [(dimensions :bounded-height) 0])
              nice))


(defn wrapper
  []
  (.. d3
      (select "#wrapper")
      (append "svg")
      (attr "width" (dimensions :width))
      (attr "height" (dimensions :width))))


(defn bounds
  [wrapper]
  (.. wrapper
      (append "g")
      (style "transform" (str "translate(" (margins :left) "px, " (margins :top) "px)"))))


(defn graph
  [bounds color]
  (.. bounds
      (selectAll "circle")
      (data app-state)
      (enter)
      (append "circle")
      (attr "cx" #(-> % x-accessor x-scale))
      (attr "cy" #(-> % y-accessor y-scale))
      (attr "r" 5)
      (attr "fill" color)
      ))


(defn d3-chart [data]
  (reagent/create-class
   {:reagent-render (fn [data] [:div#wrapper])

    :component-did-mount (fn [this]
                           (let [wrapper (wrapper)
                                 bounds (bounds wrapper)
                                 graph (graph bounds "black")])
                           (println (str "This (mount) " ))
                           (println this)
                           graph)

    :component-did-update (fn [this old-argv]
                            (let [wrapper (wrapper)
                                  bounds (bounds wrapper)
                                  graph (graph bounds "cornflowerblue")]
                              ;; (reset! (reagent/dom-node this))
                              graph
                              ))}))
