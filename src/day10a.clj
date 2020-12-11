(ns day10a
  (:require
    [clojure.string :as str]))

(defn to-long [string] (Long/parseLong string))

(defn delta [[x y]] (- y x))

(defn mult [freq]
  (*
    (inc (freq 1))
    (inc (freq 3))))

(defn run
  [_]
  (let [lines (-> "resources/day10.txt"
                  (slurp)
                  (str/split-lines))
        result (->> lines
                  (map to-long)
                  sort
                  (partition 2 1)
                  (map delta)
                  frequencies
                  mult)]
    (println result)))
