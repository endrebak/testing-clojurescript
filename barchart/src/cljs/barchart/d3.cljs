(ns barchart.d3
  (:require  [cljs.test :as t :include-macros true]
             [shadow.resource :as rc]
             [reagent.core :as reagent]
             ["d3" :as d3]))

(enable-console-print!)

(defn metric-accessor [d]
    (.-humidity d))

(def width 600)

(def margins {:top 30 :right 10 :bottom 50 :left 50})

(def dimensions
  (let [height (* 0.6 width)
        bounded-width (- width (margins :left) (margins :right))
        bounded-height (- height (margins :top) (margins :bottom))]
    {:width width :height height :bounded-width bounded-width :bounded-height bounded-height}))


(defn wrapper []
  (.. d3
      (select "#wrapper")
      (append "svg")
      (attr "width" (:width dimensions))
      (attr "height" (:height dimensions))))


(defn translate
  [x y] (str "translate(" x "px," y "px)"))



(defn bounds [wrapper]
  (.. d3
      (append "g")
      (style "transform" (translate (margins :top) (margins :left)))))

(defn x-scale [dataset]
  (println "x-scale")
  (.. d3
      (scaleLinear)
      (domain (.extent d3 dataset metric-accessor))
      (range #js [0 (dimensions :bounded-width)])
      nice))

(defn bin-generator [dataset]
  (let [bins
        (..
            (.histogram d3)
            (value metric-accessor)
            (domain (.domain (x-scale dataset)))
            (thresholds 12))]
    (bins dataset)))


(defn d3-chart [data]
  (reagent/create-class
   {:reagent-render (fn [data] [:div#wrapper])

    :component-did-mount (fn [this]
                           (let [wrapper (wrapper)
                                 ])
                           ["hi"]
                           )

    :component-did-update (fn [this]
                            (let [[_ data] (reagent/argv this)
                                  data (clj->js data)
                                  bounds (.select d3 "#bounds")]))}))
