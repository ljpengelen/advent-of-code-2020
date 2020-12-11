(ns day5b
  (:require
    [clojure.string :as str]))

(defn to-int [string]
  (-> string
    (str/replace #"B|R" "1")
    (str/replace #"F|L" "0")
    (Integer/parseInt 2)))

(defn has-gap? [[x y]]
  (not= y (inc x)))

(defn run
  [_]
  (let [lines (-> "resources/day5.txt"
                  (slurp)
                  (str/split-lines))
        result (->> lines
                (map to-int)
                sort
                (partition 2 1)
                (filter has-gap?))]
    (println result)))
