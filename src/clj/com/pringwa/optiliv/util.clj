(ns com.pringwa.optiliv.util)

(defn error-response [status msg]
  {:status status
   :body {:error msg}})

(defn by-id
  [f xs]
  (into {}
        (for [x xs]
          [(f x) x])))

