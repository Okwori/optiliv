(ns com.pringwa.optiliv.xhr.failure
  (:require [com.pringwa.optiliv.xhr.util :refer [make-event-name]]
            [kee-frame.core :as k]))

(defn reg-xhr-failure-event [event-base xhr-subkey]
  (let [event-name (make-event-name event-base :failure)]
    (k/reg-event-db event-name
                    (fn [db [error]]
                      (assoc-in db [:xhr xhr-subkey]
                                {:in-flight? false
                                 :error (let [response (:response error)]
                                          (if (map? response)
                                            (:error response)
                                            response))
                                 :success? false})))))

(reg-xhr-failure-event :current-user/load :current-user)

(k/reg-event-db :login-failure
                (fn [db [error]]
                  (assoc-in db [:xhr :login]
                            {:in-flight? false
                             :error (:status-text error)
                             :success? false})))

