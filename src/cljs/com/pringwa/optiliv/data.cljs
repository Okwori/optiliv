(ns com.pringwa.optiliv.data
  (:require [re-frame.core :refer [reg-sub subscribe]]))

(reg-sub
  ::current-user
  (fn [db _] (:current-user db)))

(reg-sub
  ::modal
  (fn [db _] (get db :modal)))

(reg-sub
  ::page
  (fn [db _] (get db :page)))

(reg-sub
  ::xhr-base
  (fn [db _] (:xhr db)))

(reg-sub
  ::current-user-id
  :<- [::current-user]
  (fn [current-user _] (:id current-user)))

(reg-sub
  ::current-user-name
  :<- [::current-user]
  (fn [current-user _] (:full-name current-user)))

(reg-sub
  ::current-user-type
  :<- [::current-user]
  (fn [current-user _] (:type current-user)))

(reg-sub
  ::current-user-roles
  :<- [::current-user]
  (fn [current-user _] (:roles current-user)))

(reg-sub
  ::email
  :<- [::current-user]
  (fn [current-user _] (:email current-user)))

(reg-sub
  ::page-state
  :<- [::page]
  (fn [page-substate [_ page-type]] (get page-substate page-type)))

(reg-sub
  ::modal-type
  :<- [::modal]
  (fn [modal-state _]
    (get modal-state :modal/type)))

(reg-sub
  ::modal-visible?
  :<- [::modal-type]
  (fn [actual-modal-type [_ given-modal-type]]
    (= actual-modal-type given-modal-type)))

(reg-sub
  ::modal-extra-state
  :<- [::modal]
  (fn [modal-state _]
    (dissoc modal-state :modal/type)))

(reg-sub
  ::xhr
  :<- [::xhr-base]
  (fn [xhr-state [_ & subkeys]]
    (get-in xhr-state subkeys)))

(reg-sub
  ::mismatch-error
  :<- [::page-state :page-type/reset-password]
  (fn [page-state _]
    (:mismatch-error page-state)))

(reg-sub
  ::mismatch-error2
  :<- [::page-state :page-type/change-password]
  (fn [page-state _]
    (:mismatch-error page-state)))

(reg-sub
  ::show-mobile-menu?
  :<- [::page-state :page-type/home]
  (fn [page-state _]
    (:show-mobile-menu? page-state)))
