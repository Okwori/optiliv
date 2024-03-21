(ns com.pringwa.optiliv.web.controllers.change-password
  (:require [clojurewerkz.scrypt.core :as sc]
            [com.pringwa.optiliv.web.routes.utils :as utils]))

(def ^:private errors
  {:missing-password   "Error: you did not provide a password."
   :password-too-short "Error: your password must be at least 8 characters."})

(defn- bad-request [err]
  {:status  400
   :headers {}
   :body    (errors err)})

(defn handler
  [{:keys [session multipart-params] :as req}]
  (let [{:keys [query-fn]} (utils/route-data req)
        encrypted-pass #(sc/encrypt % 16384 8 1)]
    (if-not session
      {:status 403, :headers {}}
      (let [password (multipart-params "password")
            account-id (:identity session)]
        (cond
          (not password) (bad-request :missing-password)
          (> 8 (count password)) (bad-request :password-too-short)
          :else (do
                  (query-fn :update-account-password! {:pwd (encrypted-pass password)
                                                    :id account-id})
                  {:status 204, :headers {}}))))))
