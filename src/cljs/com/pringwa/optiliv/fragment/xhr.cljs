(ns com.pringwa.optiliv.fragment.xhr
  (:require com.pringwa.optiliv.events
            [re-frame.core :as rf]))

(defn xhr-status
  [subkey {:keys [in-flight? success? error] :as xhr-status}]
  (cond
    in-flight? "⏳"
    error [:div.xhr-error
           (if (not-empty error)
             [:abbr {:title (str "Error: " error)} "❌"]
             "❌")]
    success? "✅"
    :else ""))
