(ns com.pringwa.optiliv.events
  (:require com.pringwa.optiliv.xhr
            [kee-frame.core :as k]
            [re-frame.core :as rf :refer [path]]
            [ajax.core :as ajax]))

(k/reg-event-fx
  :navigate-to
  (fn [cofx args]
    {:navigate-to args}))

(k/reg-event-fx
  :navigate-to-authenticated-home
  (fn [{:keys [db]} _]
    {:navigate-to (case (-> db :current-user :type)
                    "Optiliv" [:home]
                    "Agents" [:home]
                    "Customers" [:home]
                    [:login])}))

(k/reg-event-db
  ::set-modal-visibility
  [(path :modal)]
  (fn [_ [visible? modal-type extra-modal-state-map]]
    (if-not visible?
      {:modal/type :modal-type/hidden}
      (assoc extra-modal-state-map :modal/type modal-type))))

(k/reg-event-db
  :clear-xhr
  (fn [db [& subkeys]]
    (assoc-in db (into [:xhr] subkeys)
              {:in-flight? false, :error nil, :success? false})))

(k/reg-event-db
  :put-next-slide
  (fn [db _]
    (assoc db :active-slide-tab true)))

(k/reg-event-fx
  ::load-next-slide-and-properties
  (fn [{:keys [db]} _]
    {:dispatch-n [[:load-property (-> db :place-type)]]
     :db         (assoc-in db [:xhr :properties] {:in-flight? true})}))

(rf/reg-fx
  :reset-form
  (fn [form-id]
    (-> form-id js/document.getElementById .reset)))

(k/reg-event-db
  ::change-active-tab
  (fn [db [page-type tab]]
    (-> db
        (assoc-in [:page page-type :tab-select :tab] tab)
        (assoc-in [:page page-type :tab-select :active?] true))))

(k/reg-event-fx
  ::switch-tab
  (fn [cofx [page-type new-tab]]
    (let [current-tab (get-in cofx [:db :page page-type :tab-select :tab])]
      (if (not= current-tab new-tab)
        {:dispatch [::change-active-tab page-type new-tab]}))))

(k/reg-event-db
  ::change-place-types
  (fn [db [place-type val]]
    (-> db
        (assoc-in [:place-type place-type] val))))

(k/reg-event-db
  ::previous-slide
  (fn [db [attempt?]]
    (-> db
        (assoc-in [:previous-slide] attempt?))))
