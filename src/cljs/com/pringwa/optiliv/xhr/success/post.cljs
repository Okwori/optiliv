(ns com.pringwa.optiliv.xhr.success.post
  (:require [kee-frame.core :as k]))

(k/reg-event-fx
  :login-success
  (fn [cofx [_ data]]
    (let [{:keys [type email id full_name roles state]} data]
      {:db             (-> (:db cofx)
                           (assoc-in [:xhr :login]
                                     {:in-flight? false, :error nil, :success? true})
                           (assoc :current-user
                                  {:email email, :type type, :id id, :full-name full_name
                                   :roles roles :state state}))
       :dispatch       [:navigate-to-authenticated-home]
       :dispatch-later [{:ms       3000
                         :dispatch [:clear-xhr :login]}]})))

(k/reg-event-db
  :change-email-success
  (fn [db _]
    (assoc-in db [:xhr :change-email]
              {:in-flight? false, :error nil, :success? true})))

(k/reg-event-db
  :register-success
  (fn [db _]
    (-> db
        (assoc-in [:xhr :register]
                  {:in-flight? false, :error nil, :success? true}))))

(k/reg-event-db
  :signup-success
  (fn [db [form-data]]
    (assoc-in db [:xhr :signup]
              {:in-flight? false, :error nil, :success? true})))


(k/reg-event-db
  :properties-success
  (fn [db [_]]
    (assoc-in db [:xhr :properties]
              {:in-flight? false, :error nil, :success? true})))
