(ns com.pringwa.optiliv.web.controllers.properties
  (:require
    [com.pringwa.optiliv.web.model.map :as map]
    [com.pringwa.optiliv.web.routes.utils :as utils]))

(defn query-map-api [req]
  (let [{:keys [body-params]} req
        {:keys [query-fn]} (utils/route-data req)
        place-types (keep #(when (second %) (name (first %)))
                          (:place-types body-params))
        properties (query-fn :get-properties {})
        result (if (empty? place-types)
                 (map #(assoc % :rating 0) properties)
                 (pmap #(merge % (map/get-rating (:distance-radius body-params)
                                                 (:address %) place-types))
                       properties))]
    result))

(defn handler
  [{:keys [body-params session] :as req}]
  (if-let [result (query-map-api req)]
    {:status 201,
     :body   result}
    (utils/error-response 403 "forbidden")))
