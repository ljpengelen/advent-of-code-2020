(ns day11b
  (:require
    [clojure.pprint :refer [pprint]]
    [clojure.string :as str]))

(defn get-visible-seat [x y succ layout]
    (let [[next-x next-y] (succ x y)
            elem (get-in layout [next-y next-x])]
        (case elem
            "." (recur next-x next-y succ layout)
            elem)))

(defn get-action [x y layout]
    (let [elem (get-in layout [y x])
            n1 (get-visible-seat x y (fn [x y] [x (inc y)]) layout)
            n2 (get-visible-seat x y (fn [x y] [x (dec y)]) layout)
            n3 (get-visible-seat x y (fn [x y] [(inc x) y]) layout)
            n4 (get-visible-seat x y (fn [x y] [(dec x) y]) layout)
            n5 (get-visible-seat x y (fn [x y] [(inc x) (inc y)]) layout)
            n6 (get-visible-seat x y (fn [x y] [(inc x) (dec y)]) layout)
            n7 (get-visible-seat x y (fn [x y] [(dec x) (inc y)]) layout)
            n8 (get-visible-seat x y (fn [x y] [(dec x) (dec y)]) layout)
            occupied-count (get (frequencies [n1 n2 n3 n4 n5 n6 n7 n8]) "#" 0)]
        (cond
            (and (= elem "L") (= occupied-count 0)) :occupy
            (and (= elem "#") (> occupied-count 4)) :leave)))

(defn get-actions [layout]
    (let [width (count (get layout 0))
            height (count layout)]
        (for [y (range 0 height)
                x (range 0 width)
                :let [action (get-action x y layout)]
                :when action]
                [x y action])))

(defn occupy-or-leave [layout [x y action]]
    (case action
        :occupy (assoc-in layout [y x] "#")
        :leave (assoc-in layout [y x] "L")))

(defn occupy-or-leave-all-once [layout]
    (let [actions (get-actions layout)]
        (reduce occupy-or-leave layout actions)))

(defn occupy-or-leave-all [layout]
    (loop [current layout]
        (let [next (occupy-or-leave-all-once current)]
            (if (= next current)
                current
                (recur next)))))

(defn count-occupied [layout]
    (as-> layout l
        (flatten l)
        (frequencies l)
        (l "#")))

(defn run
  [_]
  (let [layout (->> "resources/day11.txt"
                  slurp
                  str/split-lines
                  (mapv #(str/split % #"")))
        result (-> layout
                    occupy-or-leave-all
                    count-occupied)]
    (pprint result)))
