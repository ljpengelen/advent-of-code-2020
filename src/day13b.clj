(ns day13b
  (:require
    [clojure.pprint :refer [pprint]]
    [clojure.string :as str]))

(defn to-int [string] (Integer/parseInt string))

(def lines (->> "resources/day13.txt"
                  slurp
                  str/split-lines))

(def ids-with-timeslot (as-> lines l
                (second l)
                (str/split l #",")
                (map-indexed (fn [index id] [index id]) l)
                (remove #(= "x" (second %)) l)
                (map (fn [[index id]] [index (to-int id)]) l)
                (map (fn [[index id]] [(- id index) id]) l)
                (map (fn [[index id]] [(mod index id)  id]) l)
                (sort-by second > l)))

(def ids (map second ids-with-timeslot))

(defn ts
  [a1 n1 a2 n2 rest]
  (println a1 n1 a2 n2 rest)
  (if (= a2 (mod (+ a1 n1) n2))
    (if (empty? rest)
      (+ a1 n1)
      (let [[[a3 n3] & new-rest] rest]
        (recur (+ a1 n1) (* n1 n2) a3 n3 new-rest)))
    (recur (+ a1 n1) n1 a2 n2 rest)))

(defn timestamp
  ([] 0)
  ([[a1 _]] a1)
  ([[a1 n1] [a2 n2] & rest] (ts a1 n1 a2 n2 rest)))

(defn run [_] (pprint [ids-with-timeslot (apply timestamp ids-with-timeslot)]))
