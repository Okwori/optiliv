(ns com.pringwa.optiliv.web.controllers.change-email
  (:require [com.pringwa.optiliv.email :as email]
            [com.pringwa.optiliv.web.routes.utils :as utils]
            [crypto.random :refer [url-part]]
            [clojure.string :as str]))

(def ^:private ok-response
  {:status 204, :headers {}})

(def ^:private conflict-response
  {:status 409
   :headers {}
   :body (str "Error: that email address is already taken.\n\n"
              "If you forgot your password, you can reset it.\n")})

(defn- conflict? [query-fn email]
  (boolean (:email (query-fn :get-account {:email email}))))

(defn- re-send-verification-email [query-fn send-fn email]
  (let [user (query-fn :get-account {:email email})
        existing-token (:token user)]
    (email/verify-email send-fn email existing-token)
    ok-response))

(defn- change-email [query-fn send-fn old-email new-email token]
  (let [user-id (:id (query-fn :get-account {:email old-email}))]
    (query-fn :update-account! {:id user-id :email new-email :state 2 :token token})
    (email/change-email send-fn old-email new-email token)
    ok-response))

(defn handler
  [{:keys [multipart-params session] :as req}]
  (let [{:keys [query-fn send-fn]} (utils/route-data req)]
   (if-not session
     {:status 403, :headers {}}
     (let [old-email (:email session)
           new-email (str/lower-case (multipart-params "email"))]
       (if (= old-email new-email)
         (re-send-verification-email query-fn send-fn old-email)
         (if (conflict? query-fn new-email)
           conflict-response
           (change-email query-fn send-fn old-email new-email (url-part 64))))))))
