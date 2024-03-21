(ns com.pringwa.optiliv.xhr.success.put
  (:require
    [kee-frame.core :as k]))

(k/reg-event-db
  :forgot-password-success
  (fn [db [form-data]]
    (assoc-in db [:xhr :forgot-password]
              {:in-flight? false, :error nil, :success? true})))

(k/reg-event-db
  :change-password-success
  (fn [db _]
    (assoc-in db [:xhr :change-password]
              {:in-flight? false, :error nil, :success? true})))
