(ns com.pringwa.optiliv.xhr.success.get
  (:require [com.pringwa.optiliv.xhr.util :refer [make-event-name]]
            [com.pringwa.optiliv.util :refer [by-id]]
            [kee-frame.core :as k]))

(defn reg-xhr-success-event [event-base xhr-subkey xform-fx]
  (let [event-name (make-event-name event-base :success)]
    (k/reg-event-fx
      event-name
      (fn [{:keys [db]} [data]]
        (let [xhr-state {:in-flight? false, :error nil, :success? true}]
          (xform-fx {:db (assoc-in db [:xhr xhr-subkey] xhr-state)}
                    data))))))

(reg-xhr-success-event
  :current-user/load :current-user
  (fn [fx {:keys [type full_name email id roles state]}]
    (assoc-in fx [:db :current-user]
              {:email email, :full-name full_name :type type,
               :id    id :roles roles :state state})))

(reg-xhr-success-event
  :register/load-user-groups :user-groups
  (fn [fx data]
    (-> fx
        (assoc-in [:db :user-groups] (by-id :id data))
        (assoc :dispatch-later
               [{:ms 3000, :dispatch [:clear-xhr :user-groups]}]))))

(reg-xhr-success-event
  :verify-token :verify-token
  (fn [fx data]
    (-> fx
        (assoc-in [:db :page :page-type/signup :email] (:email data))
        (assoc-in [:db :page :page-type/signup :full_name] (:full_name data))
        (assoc-in [:db :page :page-type/signup :mobile] (:mobile data)))))

(reg-xhr-success-event
  :verify-email :verify-email
  (fn [fx _data]
    (if (get-in fx [:db :current-user :email])
      (assoc-in fx [:db :current-user :state] "initialized")
      fx)))
