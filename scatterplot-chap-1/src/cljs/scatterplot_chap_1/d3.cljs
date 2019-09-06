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


(defn color-accessor [d]
  (println (str "Cloud cover: " (.-cloudCover d)))
  (.-cloudCover d))

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


(def color-scale (..
              (.scaleLinear d3)
              (domain (.extent d3 app-state color-accessor))
              (range #js ["skyblue" "darkslategrey"])
              nice))

(def x-axis-generator
  (.. d3
      (axisBottom)
      (scale x-scale)))


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
      (attr "id" "bounds")
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
      (attr "r" 10)
      (attr "fill" #(-> % color-accessor color-scale))))


(defn x-axis [bounds]
  (.. bounds
      (append "g")
      (attr "id" "x-axis")
      (call x-axis-generator)
      (style "transform" (str "translateY(" (:bounded-height dimensions) "px)"))))


(defn x-axis-label [x-axis]
  (.. x-axis
      (append "text")
      (attr "x" (/ (dimensions :bounded-width) 2))
      (attr "y" (- (margins :bottom) 10))
      (attr "fill" "black")
      (style "font-size" "1.4em")
      (html "Dew point (&deg;F)")
      ))

;; (defn y-axis [bounds]
;;   (.. bounds
;;       (append "g")
;;       (call x-axis-generator)
;;       (style "transform" (str "translateY(" (:bounded-height dimensions) "px)"))))

(def color (atom 0))

(defn d3-chart [data]
  (reagent/create-class
   {:reagent-render (fn [data] [:div#wrapper])

    :component-did-mount (fn [this]
                           (let [wrapper (wrapper)
                                 bounds (bounds wrapper)
                                 x-ax (x-axis bounds)
                                 _ (x-axis-label x-ax)
                                 graph (graph bounds "black")])
                           (println (str "This (mount) " ))
                           (println this)
                           graph)

    :component-did-update (fn [this]
                            (let [[_ data] (reagent/argv this)
                                  data (clj->js data)
                                  bounds (.select d3 "#bounds")]
                              (swap! color inc)
                              (.. bounds
                                  (selectAll "circle")
                                  (data data)
                                  (join "circle")
                                  (attr "cx" #(-> % x-accessor x-scale))
                                  (attr "cy" #(-> % y-accessor y-scale))
                                  (attr "r" 10)
                                  (attr "fill" (if (= 1(mod @color 2))
                                                 "red"
                                                 "cornflowerblue"))
                                  )))}))
