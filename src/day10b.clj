(ns day10b
  (:require
    [clojure.string :as str]))

(defn to-long [string] (Long/parseLong string))

(defn delta [[x y]] (- y x))

(def find-arrangements
  (memoize
    (fn [deltas]
      (if (= 1 (count deltas))
        1
        (let [[x y & rest] deltas]
          (if (> (+ x y) 3)
          (find-arrangements (conj rest y))
          (+
            (find-arrangements (conj rest y))
            (find-arrangements (conj rest (+ x y))))))))))

(defn run
  [_]
  (let [lines (-> "resources/day10.txt"
                  (slurp)
                  (str/split-lines))
        longs (->> lines
                  (map to-long))
        device-jolts (->> (apply max longs)
                        (+ 3))
        joltages (-> longs
                  (conj 0 device-jolts))
        result (->> joltages
                  sort
                  (partition 2 1)
                  (mapv delta)
                  (find-arrangements))]
    (println result)))
