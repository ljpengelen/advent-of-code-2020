(ns day5a
  (:require
    [clojure.string :as str]))

(defn to-int [string]
  (-> string
    (str/replace #"B|R" "1")
    (str/replace #"F|L" "0")
    (Integer/parseInt 2)))

(defn run
  [_]
  (let [lines (-> "resources/day5.txt"
                  (slurp)
                  (str/split-lines))
        result (->> lines
                (map to-int)
                (apply max))]
    (println result)))
