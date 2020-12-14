(ns day14a
  (:require
    [clojure.pprint :refer [pprint]]
    [clojure.string :as str]))

(defn to-int [string] (Integer/parseInt string))

(def lines (->> "resources/day14.txt"
                  slurp
                  str/split-lines))

(defn set-bitmask [mem line]
  (let [[_ bitmask] (re-matches #"mask = ([01X]+)" line)]
    (assoc mem :bitmask bitmask)))

(defn mask [bits mask]
  (->> bits
      (map-indexed (fn [i c] (case (get mask i)
                                 \X c
                                 \0 "0"
                                 \1 "1")))
      (apply str)))

(defn pad [string] (.substring (str (apply str (repeat 36 "0")) string) (count string)))

(defn assign [{:keys [bitmask] :as mem} line]
  (let [[_ address value] (re-matches #"mem\[(\d+)\] = (\d+)" line)
        int (to-int value)
        bits (Integer/toBinaryString int)
        padded-bits (pad bits)
        masked-bits (mask padded-bits bitmask)
        masked-int (Long/parseLong masked-bits 2)]
    (assoc-in mem [:addr address] masked-int)))

(defn step [mem line]
  (if (= 0 (.indexOf line "mask ="))
    (set-bitmask mem line)
    (assign mem line)))

(def mem (reduce step {} lines))

(def sum (->> mem
              :addr
              vals
              (apply +)))

(defn run [_] (pprint [mem, sum]))
