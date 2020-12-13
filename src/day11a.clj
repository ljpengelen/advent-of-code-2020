(ns day11a
  (:require
    [clojure.pprint :refer [pprint]]
    [clojure.string :as str]))

(defn get-action [x y layout]
    (let [elem (get-in layout [y x])
            n1 (get-in layout [(dec y) (dec x)])
            n2 (get-in layout [(dec y) x])
            n3 (get-in layout [(dec y) (inc x) ])
            n4 (get-in layout [y (dec x)])
            n5 (get-in layout [y (inc x)])
            n6 (get-in layout [(inc y) (dec x)])
            n7 (get-in layout [(inc y) x])
            n8 (get-in layout [(inc y) (inc x)])
            occupied-count (get (frequencies [n1 n2 n3 n4 n5 n6 n7 n8]) "#" 0)]
        (cond
            (and (= elem "L") (= occupied-count 0)) :occupy
            (and (= elem "#") (> occupied-count 3)) :leave)))

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

(defn count-empty [layout]
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
                    count-empty)]
    (pprint result)))
