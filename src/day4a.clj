(ns day4a
  (:require
    [clojure.string :refer [split]]
    [clojure.walk :refer [keywordize-keys]]))

(defn to-passport-map [password-string]
  (let [fields (split password-string #"\s")
        pairs (map #(split % #":") fields)]
    (->> pairs
      (into {})
      keywordize-keys)))

(defn valid-passport? [{:keys [byr iyr eyr hgt hcl ecl pid]}]
  (and byr iyr eyr hgt hcl ecl pid))

(defn run
  [_]
  (let [lines (-> "resources/day4.txt"
                  (slurp)
                  (split #"\n\n"))
        result (->> lines
                (map to-passport-map)
                (filter valid-passport?)
                count)]
    (println result)))
