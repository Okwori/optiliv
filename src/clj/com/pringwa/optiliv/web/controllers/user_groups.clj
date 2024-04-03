(ns com.pringwa.optiliv.web.controllers.user-groups
  (:require
    [com.pringwa.optiliv.util :refer [error-response]]
    [com.pringwa.optiliv.web.routes.utils :as utils]))

(defn- user-groups [query-fn]
  (query-fn :get-all-user-groups {}))

(defn handler
  [{:keys [session] :as req}]
  (let [{:keys [query-fn]} (utils/route-data req)]
   (if session
     {:status 200
      :body   (user-groups query-fn)}
     (error-response 403 "forbidden"))))
