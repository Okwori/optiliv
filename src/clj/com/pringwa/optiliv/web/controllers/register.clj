(ns com.pringwa.optiliv.web.controllers.register
  (:require
    [com.pringwa.optiliv.email :as email]
    [com.pringwa.optiliv.web.routes.utils :as utils]
    [crypto.random :refer [url-part]]))

(defn handler
  [{:keys [multipart-params session] :as req}]
  (if session
    (let [{:keys [query-fn send-fn]} (utils/route-data req)
          token (url-part 64)
          {:strs [email full-name mobile user-group-id]} multipart-params
          account-type-id (Integer/parseInt user-group-id)
          _ (println "Here: " email full-name token mobile account-type-id)
          _ (query-fn :create-user! {:email           email :password nil :full_name full-name
                                     :mobile          mobile :token token :active false :last_login nil
                                     :account_type_id account-type-id :account_state_id 1})
          _ (println "GOT IT!!")
          _ (email/welcome-customer send-fn email token)]
      {:status 201,
       :body   {}})
    (utils/error-response 403 "forbidden")))
