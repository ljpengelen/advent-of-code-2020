(ns day9a
  (:require
    [clojure.string :as str]))

(defn to-long [string] (Long/parseLong string))

(defn adds-up?
  [ints]
  (first
    (for [i (range 25)
      j (range i 25)
      :let [x (nth ints i)]
      :let [y (nth ints j)]
      :let [last (nth ints 25)]
      :when (= last (+ x y))]
      [x y last])))

(defn run
  [_]
  (let [lines (-> "resources/day9.txt"
                  (slurp)
                  (str/split-lines))
        result (->> lines
                  (map to-long)
                  (partition 26 1)
                  (remove adds-up?)
                  first)]
    (println result)))
