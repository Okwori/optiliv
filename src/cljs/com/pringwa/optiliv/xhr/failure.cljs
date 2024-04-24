(ns com.pringwa.optiliv.xhr.failure
  (:require [com.pringwa.optiliv.xhr.util :refer [make-event-name]]
            [kee-frame.core :as k]))

(defn reg-xhr-failure-event [event-base xhr-subkey]
  (let [event-name (make-event-name event-base :failure)]
    (k/reg-event-db event-name
                    (fn [db [error]]
                      (assoc-in db [:xhr xhr-subkey]
                                {:in-flight? false
                                 :error      (let [response (:response error)]
                                               (if (map? response)
                                                 (:error response)
                                                 response))
                                 :success?   false})))))

(reg-xhr-failure-event :change-email :change-email)
(reg-xhr-failure-event :change-password :change-password)
(reg-xhr-failure-event :current-user/load :current-user)
(reg-xhr-failure-event :verify-token :verify-token)
(reg-xhr-failure-event :verify-email :verify-email)
(reg-xhr-failure-event :logout :logout)
(reg-xhr-failure-event :forgot-password :forgot-password)
(reg-xhr-failure-event :register/load-user-groups :user-groups)
(reg-xhr-failure-event :register :register)
(reg-xhr-failure-event :reset-password :reset-password)
(reg-xhr-failure-event :signup :signup)
(reg-xhr-failure-event :properties :properties)
(reg-xhr-failure-event :list-property :list-property)

(k/reg-event-db :login-failure
                (fn [db [error]]
                  (assoc-in db [:xhr :login]
                            {:in-flight? false
                             :error      (:status-text error)
                             :success?   false})))

