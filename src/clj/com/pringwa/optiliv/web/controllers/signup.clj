(ns com.pringwa.optiliv.web.controllers.signup
  (:require
    [clojurewerkz.scrypt.core :as sc]
    [com.pringwa.optiliv.email :as email]
    [com.pringwa.optiliv.web.routes.utils :as utils]))

(defn- bad-token [query-fn token]
  (when-not (query-fn :get-account-by-token {:token token})
    (str "Error: your signup token could not be found."
         " Please ensure that you are at the address "
         "included in your welcome email.")))

(defn- mismatched-passwords [p1 p2]
  (when (not= p1 p2)
    "The given passwords do not match"))

(defn- error-messages
  [query-fn params]
  (if-let [bad-token-msg (bad-token query-fn (get params "token"))]
    bad-token-msg
    (if-let [mismatched-msg (mismatched-passwords (get params "password")
                                                  (get params "confirm-password"))]
      mismatched-msg)))

(defn- transact
  [query-fn params]
  (if-let [err-msg (error-messages query-fn params)]
    [false err-msg]
    [true (let [token (get params "token")
                {:strs [full-name mobile password]} params
                {:keys [id]} (query-fn :get-account-by-token {:token token})
                password (get params "password")
                encrypted-password (sc/encrypt password 16384 8 1)
                account (query-fn :get-account-by-id {:id id})
                ;_ (query-fn :update-account-state! {:id id :state_id 3})
                ok? (query-fn :update-user! {:id     id :state_id 3 :full_name full-name
                                             :mobile mobile :password encrypted-password})]
            (when ok? account))]))

(defn error-response [err-msg]
  {:status  400
   :headers {"content-type" "text/plain"}
   :body    err-msg})

(defn success-response []
  {:status 204, :headers {}})

(defn handler
  [{:keys [multipart-params] :as req}]
  (let [{:keys [query-fn send-fn]} (utils/route-data req)
        [ok? result] (transact query-fn multipart-params)
        _ (println multipart-params "Result: " result)]
    (if ok?
      (do
        (email/welcome-customer-intro send-fn (:email result))
        (success-response))
      (error-response result))))
