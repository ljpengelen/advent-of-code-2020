(ns day8a
  (:require
    [clojure.string :as str]))

(defn to-instruction [string]
  (let [[_ instruction delta] (re-matches #"(acc|jmp|nop) ([+\-]\d+)" string)]
    [(keyword instruction) (Integer/parseInt delta)]))

(defn execute [instructions]
  (loop [instructions instructions
          index 0
          acc 0
          executed #{}]
    (if (executed index)
      acc
      (if-let [[instruction delta] (nth instructions index)]
        (case instruction
          :acc (recur instructions (inc index) (+ acc delta) (conj executed index))
          :jmp (recur instructions (+ index delta) acc (conj executed index))
          :nop (recur instructions (inc index) acc (conj executed index)))
        acc))))

(defn run
  [_]
  (let [lines (-> "resources/day8.txt"
                  (slurp)
                  (str/split-lines))
        instructions (map to-instruction lines)
        result (execute instructions)]
    (println result)))
