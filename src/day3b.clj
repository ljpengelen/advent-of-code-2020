(ns day3b
  (:require
    [clojure.string :refer [split-lines]]))

(defn is-tree? [lines x y]
  (= \# (nth (nth lines y) x)))

(defn traverse
  ([lines width height dx dy] (traverse lines width height dx dy 0 0 0))
  ([lines width height dx dy count x y]
  (if (>= y height)
    count
    (let [new-x (mod (+ dx x) width)
          new-y (+ dy y)
          new-count (if (is-tree? lines x y) (inc count) count)]
      (traverse lines width height dx dy new-count new-x new-y)))))

(defn run
  [_]
  (let [lines (-> "resources/day3.txt"
                  (slurp)
                  (split-lines))
        height (count lines)
        width (count (nth lines 0))
        d11 (traverse lines width height 1 1)
        d31 (traverse lines width height 3 1)
        d51 (traverse lines width height 5 1)
        d71 (traverse lines width height 7 1)
        d12 (traverse lines width height 1 2)]
    (println (* d11 d31 d51 d71 d12))))
