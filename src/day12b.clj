(ns day12b
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

(defn forward [{:keys [ship waypoint] :as positions} amount]
  (let [ship-north (:north ship)
        ship-east (:east ship)
        wp-north (:north waypoint)
        wp-east (:east waypoint)]
    (-> positions
      (assoc-in [:ship :north] (+ ship-north (* amount wp-north)))
      (assoc-in [:ship :east] (+ ship-east (* amount wp-east))))))

(defn right [{:keys [waypoint] :as positions} amount]
  (let [wp-north (:north waypoint)
        wp-east (:east waypoint)
        angle (mod amount 360)]
    (case angle
      0 positions
      90 (assoc positions :waypoint {:north (- wp-east) :east wp-north})
      180 (assoc positions :waypoint {:north (- wp-north) :east (- wp-east)})
      270 (assoc positions :waypoint {:north wp-east :east (- wp-north)}))))

(defn left [position amount]
  (right position (- 360 amount)))

(defn move [{:keys [waypoint] :as positions} {:keys [action amount]}]
  (let [wp-north (:north waypoint)
        wp-east (:east waypoint)]
    (pprint positions)
    (case action
      :N (assoc-in positions [:waypoint :north] (+ wp-north amount))
      :S (assoc-in positions [:waypoint :north] (- wp-north amount))
      :E (assoc-in positions [:waypoint :east] (+ wp-east amount))
      :W (assoc-in positions [:waypoint :east] (- wp-east amount))
      :L (left positions amount)
      :R (right positions amount)
      :F (forward positions amount))))

(defn manhattan [{:keys [ship]}] (+ (Math/abs (:north ship)) (Math/abs (:east ship))))

(def positions (reduce move {:ship {:north 0 :east 0} :waypoint {:north 1 :east 10}} actions))

(defn run [_] (pprint [actions positions (manhattan positions)]))
