(ns day1
  (:require
    [clojure.java.io :refer [resource]]
    [clojure.math.combinatorics :refer [combinations]]
    [clojure.string :refer [split-lines]]))

(defn to-integers
  [strings]
  (map #(Integer/parseInt %) strings))

(defn twenty-twenty
  [tuple]
  (= 2020 (apply + tuple)))

(defn mult
  [tuple]
  (apply * tuple))

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
                    (map mult))]
    (dorun (println result))))
