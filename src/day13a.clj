(ns day13a
  (:require
    [clojure.pprint :refer [pprint]]
    [clojure.string :as str]))

(defn to-int [string] (Integer/parseInt string))

(def lines (->> "resources/day13.txt"
                  slurp
                  str/split-lines))

(def timestamp (-> lines
                    first
                    to-int))

(def ids (as-> lines l
                (second l)
                (str/split l #",")
                (remove #(= "x" %) l)
                (map to-int l)))

(def best-bus (->> ids
                    (map (fn [id] [id (/ (mod timestamp id) id)]))
                    (sort-by second >)
                    first
                    first))

(def earliest-time (* best-bus (inc (quot timestamp best-bus))))

(def first-answer (* best-bus (- earliest-time timestamp)))

(defn run [_] (pprint first-answer))
