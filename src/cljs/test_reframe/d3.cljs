(ns test-reframe.d3
  (:require
   [cljs.test :as t :include-macros true]
   [reagent.core :as reagent]))

(defn inner [data]
  (reagent/create-class
   {:display-name "Counter"

    :component-did-mount (fn [data]
                           (js/console.log "Initialized")
                           [:h1 "Initialized! " data])


    :component-did-update (fn [this _]
                            (let [[_ data] (reagent/argv this)]
                              (js/console.log (str "Updated " data))
                              [:div (str "My clicks " data)]))

    :reagent-render (fn [data] [:div (str "My clicks " data)])}))

;; (defn my-component
;;   [data]
;;   (reagent/create-class                 ;; <-- expects a map of functions
;;    {:display-name  "my-component"      ;; for more helpful warnings & errors

;;     :component-did-mount               ;; the name of a lifecycle function
;;     (fn [this]
;;       (println "component-did-mount")) ;; your implementation

;;     :component-did-update              ;; the name of a lifecycle function
;;     (fn [this old-argv]                ;; reagent provides you the entire "argv", not just the "props"
;;       (let [new-argv (rest (reagent/argv this))]
;;         (do-something new-argv old-argv)))

;;     ;; other lifecycle funcs can go in here
;;     :reagent-render        ;; Note:  is not :render
;;     (fn [x y z]           ;; remember to repeat parameters
;;       [:div (str data)])}))




;; (defn d3-inner [data]
;;   (reagent/create-class
;;    {:reagent-render (fn [] [:div [:svg {:width 400 :height 800}]])

;;     :component-did-mount (fn []
;;                            (let [d3data (clj->js data)]
;;                              (.. js/d3
;;                                  (select "svg")
;;                                  (selectAll "circle")
;;                                  )))


;;     :component-did-update (fn [this]
;;                             (let [[_ data] (reagent/argv this)
;;                                   d3data (clj->js data)]
;;                               (.. js/d3
;;                                   (selectAll "circle")
;;                                   (data d3data))))
;;     }))
