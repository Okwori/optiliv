(ns com.pringwa.optiliv.web.controllers.verify-token
  (:require [com.pringwa.optiliv.util :refer [error-response]]
            [com.pringwa.optiliv.web.routes.utils :as utils]))

(defn- found [email full-name]
  {:status 200
   :body {:email email :full-name full-name}})

(defn- not-found []
  (error-response 404 "not found"))

(defn handler
  [{:keys [query-params] :as req}]
  (let [{:strs [token]} query-params
        {:keys [query-fn]} (utils/route-data req)]
    (if-let [user (query-fn :get-account-by-token {:token token})]
      (found (:email user) (:full-name user))
      (not-found))))
