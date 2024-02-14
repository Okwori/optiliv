(ns com.pringwa.optiliv.xhr.util)

(defn make-event-name
  [base-keyword suffix-keyword]
  (keyword (subs (str base-keyword "-" (name suffix-keyword)) 1)))
