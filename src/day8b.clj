(ns day8b
  (:require
    [clojure.string :as str]))

(defn to-instruction [string]
  (let [[_ instruction delta] (re-matches #"(acc|jmp|nop) ([+\-]\d+)" string)]
    [(keyword instruction) (Integer/parseInt delta)]))

(def switch
  {:acc :acc
  :jmp :nop
  :nop :jmp})

(defn execute [instructions]
  (loop [instructions instructions
          index 0
          acc 0
          executed #{}]
    (if (executed index)
      [acc, index]
      (if-let [[instruction delta] (get instructions index)]
        (case instruction
          :acc (recur instructions (inc index) (+ acc delta) (conj executed index))
          :jmp (recur instructions (+ index delta) acc (conj executed index))
          :nop (recur instructions (inc index) acc (conj executed index)))
        [acc, index]))))

(defn run
  [_]
  (let [lines (-> "resources/day8.txt"
                  (slurp)
                  (str/split-lines))
        instructions (vec (map to-instruction lines))
        result (->> (range (count instructions))
                  (map #(update instructions % (fn [[instruction delta]] [(switch instruction) delta])))
                  (map execute)
                  (sort-by last))]
    (println result)))
