(ns day6a
  (:require
    [clojure.string :as str]))

(defn unique-answers [group-answers]
  (-> group-answers
    (str/replace #"\W" "")
    frequencies
    keys
    count))

(defn run
  [_]
  (let [group-answers (-> "resources/day6.txt"
                  (slurp)
                  (str/split #"\n\n"))
        result (->> group-answers
                (map unique-answers)
                (apply +))]
    (println result)))
