(ns com.pringwa.optiliv.util)

(defn by-id
  [f xs]
  (into {}
        (for [x xs]
          [(f x) x])))
