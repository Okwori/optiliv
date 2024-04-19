(ns com.pringwa.optiliv.web.model.calc)

(defn inverse
  "Gets the inverse of a number, x"
  [x base-distance]
  (/ base-distance x))

(defn get-points
  "Returns the number of points based on distance in m: Given Base Distance = 1pt, Note:  50km is Max Distance"
  [distance base-distance]
  (inverse distance base-distance))

(defn sum-distances
  "Returns the sum of the collection"
  [coll]
  (apply + coll))

(defn count-distances
  "Returns the number of the collection"
  [coll]
  (count coll))

(defn inverse-coll
  "Returns the inverse of the collection"
  [coll base-distance]
  (map #(get-points % base-distance) coll))

(defn reset-base-distance
  "Returns the collection taking into cognition the base-distance and reset to base-distance distances that are less"
  [coll base-distance]
  (map (fn [n]
         (if (< n base-distance)
           base-distance
           n)) coll))

(defn get-point-percentage
  "Returns the percentage of a number, x"
  [x]
  (Math/round (* x 100)))

(defn calculate-points
  "Returns the percent points relevancy of a given distance coll"
  [coll base-distance]
  (-> (reset-base-distance coll base-distance)
      (inverse-coll base-distance)
      (sum-distances)
      (/  (count-distances coll))
      (double)
      (get-point-percentage)))
