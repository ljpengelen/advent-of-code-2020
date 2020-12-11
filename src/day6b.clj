(ns day6b
  (:require
    [clojure.set :as set]
    [clojure.string :as str]))

(defn same-answers [group-answers]
  (as-> group-answers v
    (str/split v #"\n")
    (map #(into #{} %) v)
    (apply set/intersection v)
    (count v)))

(defn run
  [_]
  (let [group-answers (-> "resources/day6.txt"
                  (slurp)
                  (str/split #"\n\n"))
        result (->> group-answers
                (map same-answers)
                (apply +))]
    (println result)))
