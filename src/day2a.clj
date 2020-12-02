(ns day2a
  (:require
    [clojure.math.combinatorics :refer [combinations]]
    [clojure.string :refer [split-lines]]))

(defn to-int
  [string]
  (Integer/parseInt string))

(defn match
  [string]
  (re-matches #"(\d+)-(\d+) (.): (.+)" string))

(defn valid?
  [[_ lower-string upper-string character password]]
  (let [lower (to-int lower-string)
        upper (to-int upper-string)
        password-frequencies (frequencies password)
        character-count (get password-frequencies (first character) 0)]
        (and (>= character-count lower) (<= character-count upper))))

(defn run
  [args]
  (let [lines (-> "resources/day2.txt"
                  (slurp)
                  (split-lines))
        result (->> lines
                    (map match)
                    (filter valid?)
                    (count))]
    (dorun (println result))))
