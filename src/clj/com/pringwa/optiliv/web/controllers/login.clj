(ns com.pringwa.optiliv.web.controllers.login
  (:require [clojure.string :as string]
            [clojurewerkz.scrypt.core :as sc]
            [com.pringwa.optiliv.web.routes.utils :as utils]
            [taoensso.timbre :as log])
  (:import (java.io ByteArrayOutputStream)
           (java.util Date)))

(defn- authenticate [req]
  (let [{:keys [query-fn]} (utils/route-data req)
        {:strs [email password]} (:multipart-params req)
        email (string/lower-case email)
        account (query-fn :get-account {:email email})]
    (when (and account
               (sc/verify password (:password account))
               (:active account))
      (-> account
          (dissoc :password)))))

(defn handler
  [{:keys [session] :as req}]
  (log/info "Login process started")
  (let [{:keys [query-fn]} (utils/route-data req)]
    (if-let [result (authenticate req)]
      (let [roles (->> (query-fn :get-roles {:identity (:id result)})
                       (mapv (comp keyword :role)))
            ;_ (println "ROLES: " roles)
            _ (query-fn :create-session {:identity (:id result)})
            _ (query-fn :update-last-login! {:id         (:id result)
                                             :last_login (Date.)})]
        {:status  200
         :body    (assoc result :roles roles)
         :session (assoc session :identity (:id result)
                                 :roles roles)})
      {:status 403})))


