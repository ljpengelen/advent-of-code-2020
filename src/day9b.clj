(ns day9b
  (:require
    [clojure.string :as str]))

(defn to-long [string] (Long/parseLong string))

(defn adds-up-to?
  [total longs]
  (loop [from 0
          to 2]
    (let [sub (subvec longs from to)
          sum (apply + sub)]
      (cond
        (< sum total) (recur from (inc to))
        (> sum total) (recur (inc from) (+ 2 from))
        (= sum total) sub))))

(defn run
  [_]
  (let [lines (-> "resources/day9.txt"
                  (slurp)
                  (str/split-lines))
        result (->> lines
                  (map to-long)
                  vec
                  (adds-up-to? 26134589)
                  sort
                  ((fn [x] (+ (first x) (last x)))))]
    (println result)))
