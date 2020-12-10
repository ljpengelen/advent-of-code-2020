(ns day3a
  (:require
    [clojure.string :refer [split-lines]]))

(defn is-tree? [lines x y]
  (= \# (nth (nth lines y) x)))

(defn traverse
  ([lines width height] (traverse lines width height 0 0 0))
  ([lines width height count x y]
  (if (= y height)
    count
    (let [new-x (mod (+ 3 x) width)
          new-y (inc y)
          new-count (if (is-tree? lines x y) (inc count) count)]
      (traverse lines width height new-count new-x new-y)))))

(defn run
  [_]
  (let [lines (-> "resources/day3.txt"
                  (slurp)
                  (split-lines))
        height (count lines)
        width (count (nth lines 0))
        result (traverse lines width height)]
    (println result)))
