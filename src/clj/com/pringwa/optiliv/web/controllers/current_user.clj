(ns com.pringwa.optiliv.web.controllers.current-user
  (:require
    [com.pringwa.optiliv.web.routes.utils :as utils]))

(defn get-session [id req]
  (let [{:keys [query-fn]} (utils/route-data req)
        account (query-fn :get-session {:identity id})
        roles (->> (query-fn :get-roles {:identity id})
                   (mapv (comp keyword :role)))]
    (when (and account roles (:active account))
      (assoc account :roles roles))))

(defn handler
  [{:keys [session] :as req}]
  (if (and session (:identity session))
    (let [_ (println "Session: " session)]
     {:status 200, :body (get-session (:identity session) req)})
    (utils/error-response 404 "Kindly Login to Access this Page!")))
