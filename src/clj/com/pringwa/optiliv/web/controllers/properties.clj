(ns com.pringwa.optiliv.web.controllers.properties
  (:require
    [com.pringwa.optiliv.web.routes.utils :as utils]))

(defn handler
  [{:keys [body-params session] :as req}]
  (if session
    (let [_ (println "PARAMS:" body-params)
          place-types (keep #(when (second %) (name (first %)))
                            (:place-type body-params))
          result (if (not-empty place-types)
                   nil
                   nil)]
      {:status 201,
       :body   {}})
    (utils/error-response 403 "forbidden")))
