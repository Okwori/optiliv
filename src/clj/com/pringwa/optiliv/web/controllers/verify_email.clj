(ns com.pringwa.optiliv.web.controllers.verify-email
  (:require [com.pringwa.optiliv.email :as email]
            [com.pringwa.optiliv.web.routes.utils :as utils]))

(defn- verify-email [query-fn send-fn user]
  (let [{:keys [id email]} user
        _ (query-fn :update-account-account! {:id id :email email :token nil :state 3})]
    (email/verified-email send-fn email)
    {:status 204, :headers {}}))

(defn handler
  [{:keys [query-params] :as req}]
  (let [token (query-params "token")
        {:keys [query-fn send-fn]} (utils/route-data req)]
    (if-let [user (query-fn :get-account-by-token {:token token})]
      (verify-email query-fn send-fn user)
      {:status 400
       :headers {}
       :body (str "Error: the given token could not be found in the "
                  "database.\n\nPlease ensure that you are at the "
                  "address included in your verification email. If so, "
                  "you may need to send a new verification email.\n")})))
