(ns com.pringwa.optiliv.xhr.success.post
  (:require [kee-frame.core :as k]))

(k/reg-event-fx :login-success
                (fn [cofx [form-data data]]
                  (let [{:keys [type email id full-name roles station-id]} data]
                    {:db (-> (:db cofx)
                             (assoc-in [:xhr :login]
                                       {:in-flight? false, :error nil, :success? true})
                             (assoc :current-user
                                    {:email email, :type type, :id id, :full-name full-name
                                     :roles roles :station station-id}))
                     :dispatch [:navigate-to-authenticated-home]
                     :dispatch-later [{:ms 3000
                                       :dispatch [:clear-xhr :login]}]})))


