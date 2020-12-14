(ns day12a
  (:require
    [clojure.pprint :refer [pprint]]
    [clojure.string :as str]))

(defn to-int [string] (Integer/parseInt string))

(defn to-action [string]
  (->> string
      (re-matches #"(N|S|E|W|L|R|F)(\d+)")
      ((fn [[_ action amount]] { :action (keyword action) :amount (to-int amount)}))))

(def actions (->> "resources/day12.txt"
                  slurp
                  str/split-lines
                  (map to-action)))

(defn forward [{:keys [north east heading] :as position} amount]
  (pprint position)
  (case heading
    0 (assoc position :north (+ north amount))
    90 (assoc position :east (+ east amount))
    180 (assoc position :north (- north amount))
    270 (assoc position :east (- east amount))))

(defn right [{:keys [heading] :as position} amount]
  (assoc position :heading (mod (+ heading amount) 360)))

(defn left [position amount]
  (right position (- 360 amount)))

(defn move [{:keys [north east] :as position} {:keys [action amount]}]
  (case action
    :N (assoc position :north (+ north amount))
    :S (assoc position :north (- north amount))
    :E (assoc position :east (+ east amount))
    :W (assoc position :east (- east amount))
    :L (left position amount)
    :R (right position amount)
    :F (forward position amount)))

(defn manhattan [{:keys [north east]}] (+ (Math/abs north) (Math/abs east)))

(def position (reduce move {:north 0 :east 0 :heading 90} actions))

(defn run [_] (pprint [actions position (manhattan position)]))
