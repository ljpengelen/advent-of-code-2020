(ns day4b
  (:require
    [clojure.string :refer [split]]
    [clojure.walk :refer [keywordize-keys]]))

(defn to-passport-map [password-string]
  (let [fields (split password-string #"\s")
        pairs (map #(split % #":") fields)]
    (->> pairs
      (into {})
      keywordize-keys)))

(defn valid-height? [[_ digits unit]]
  (if (or (nil? digits) (nil? unit))
    false
    (let [height (Integer/parseInt digits)]
      (if (= unit "cm")
        (<= 150 height 193)
        (<= 59 height 76)))))

(defn valid-passport? [{:keys [byr iyr eyr hgt hcl ecl pid]}]
  (and byr iyr eyr hgt hcl ecl pid
    (<= 1920 (Integer/parseInt byr) 2002)
    (<= 2010 (Integer/parseInt iyr) 2020)
    (<= 2020 (Integer/parseInt eyr) 2030)
    (valid-height? (re-matches #"(\d+)(cm|in)" hgt))
    (re-matches #"#[0-9a-f]{6}" hcl)
    (re-matches #"amb|blu|brn|gry|grn|hzl|oth" ecl)
    (re-matches #"[0-9]{9}" pid)
))

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
