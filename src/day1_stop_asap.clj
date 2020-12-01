(ns day1-stop-asap
  (:require
    [clojure.math.combinatorics :refer [combinations]]
    [clojure.string :refer [split-lines]]))

(defn to-integers
  [strings]
  (map #(Integer/parseInt %) strings))

(defn twenty-twenty
  [tuple]
  (= 2020 (apply + tuple)))

(defn run
  [args]
  (let [size (get args :size 2)
        tuples (-> "resources/day1.txt"
                  (slurp)
                  (split-lines)
                  (to-integers)
                  (combinations size))
        result (->> tuples
                    (filter twenty-twenty)
                    (first)
                    (apply *))]
    (dorun (println result))))
