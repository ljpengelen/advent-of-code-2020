(ns day1
  (:require
    [clojure.java.io :refer [resource]]
    [clojure.math.combinatorics :refer [combinations]]
    [clojure.string :refer [split-lines]]))

(defn to-integers
  [strings]
  (map #(Integer/parseInt %) strings))

(defn twenty-twenty
  [pair]
  (= 2020 (apply + pair)))

(defn mult
  [pair]
  (apply * pair))

(defn run
  [args]
  (let [size (get args :size 2)
        pairs (-> "resources/day1.txt"
                  (slurp)
                  (split-lines)
                  (to-integers)
                  (combinations size))
        result (->> pairs
                    (filter twenty-twenty)
                    (map mult))]
    (dorun (println result))))
