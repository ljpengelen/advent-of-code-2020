(ns day2b
  (:require
    [clojure.string :refer [split-lines]]))

(defn to-int
  [string]
  (Integer/parseInt string))

(defn match
  [string]
  (re-matches #"(\d+)-(\d+) (.): (.+)" string))

(defn xor
  [left right]
  (or
    (and left (not right))
    (and (not left) right)))

(defn valid?
  [[_ first-string second-string character-string password]]
  (let [first-index (dec (to-int first-string))
        second-index (dec (to-int second-string))
        first-char (get password first-index)
        second-char (get password second-index)
        char (first character-string)]
        (xor (= char first-char) (= char second-char))))

(defn run
  [args]
  (let [lines (-> "resources/day2.txt"
                  (slurp)
                  (split-lines))
        result (->> lines
                    (map match)
                    (filter valid?)
                    (count))]
    (println result)))
