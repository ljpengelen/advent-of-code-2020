(ns day7a
  (:require
    [clojure.string :as str]))

(defn contains-no-bags? [line]
  (str/includes? line "contain no other bags"))

(defn to-bag-map [line]
  (let [[[_ _ container] & contained] (re-seq #"(?:^|(\d+) )(\w+ \w+) bags?" line)
        contained-maps (map (fn [[_ count color]] {color (Integer/parseInt count)}) contained)
        contained-map (apply merge contained-maps)]
  {container contained-map}))

(defn containers-for
  [color bags-map]
  (let [containing-colors (->> bags-map
                              (filter (fn [[_ contained-colors]] (contained-colors color)))
                              keys)
        rec (mapcat #(containers-for % bags-map) containing-colors)]
    (set (concat rec containing-colors))))

(defn run
  [_]
  (let [lines (-> "resources/day7.txt"
                  (slurp)
                  (str/split #"\n"))
        bags-map (->> lines
                  (remove contains-no-bags?)
                  (map to-bag-map)
                  (apply merge))
        result (count (containers-for "shiny gold" bags-map))]
    (println result)))
