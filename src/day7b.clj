(ns day7b
  (:require
    [clojure.string :as str]))

(defn contains-no-bags? [line]
  (str/includes? line "contain no other bags"))

(defn to-bag-map [line]
  (let [[[_ _ container] & contained] (re-seq #"(?:^|(\d+) )(\w+ \w+) bags?" line)
        contained-maps (map (fn [[_ count color]] {color (Integer/parseInt count)}) contained)
        contained-map (apply merge contained-maps)]
  {container contained-map}))

(defn contained-by
  [color bags-map multiplier]
  (let [contained (bags-map color)
        contained-count (->> contained
                          vals
                          (apply +)
                          (* multiplier))
        rec-count (->> contained
                    (map #(contained-by (first %) bags-map (* multiplier (last %))))
                    (apply +))]
    (+ contained-count rec-count)))

(defn run
  [_]
  (let [lines (-> "resources/day7.txt"
                  (slurp)
                  (str/split #"\n"))
        bags-map (->> lines
                  (remove contains-no-bags?)
                  (map to-bag-map)
                  (apply merge))
        result (contained-by "shiny gold" bags-map 1)]
    (println result)))
