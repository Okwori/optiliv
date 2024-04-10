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
  ::new-user-password
  (fn [db _] (:new-user-password db)))

(reg-sub
  ::user-groups
  (fn [db _] (:user-groups db)))

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
  (fn [current-user _] (:full_name current-user)))

(reg-sub
  ::current-user-type
  :<- [::current-user]
  (fn [current-user _] (:type current-user)))

(reg-sub
  ::current-user-roles
  :<- [::current-user]
  (fn [current-user _] (:roles current-user)))

(reg-sub
  ::current-user-state
  :<- [::current-user]
  (fn [current-user _] (:state current-user)))

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
  ::signup-token
  :<- [::page-state :page-type/signup]
  (fn [page-state _]
    (:token page-state)))

(reg-sub
  ::signup-token
  :<- [::page-state :page-type/signup]
  (fn [page-state _]
    (:token page-state)))

(reg-sub
  ::signup-email
  :<- [::page-state :page-type/signup]
  (fn [page-state _]
    (:email page-state)))

(reg-sub
  ::signup-full-name
  :<- [::page-state :page-type/signup]
  (fn [page-state _]
    (:full_name page-state)))

(reg-sub
  ::signup-mobile
  :<- [::page-state :page-type/signup]
  (fn [page-state _]
    (:mobile page-state)))

(reg-sub
  ::show-mobile-menu?
  :<- [::page-state :page-type/home]
  (fn [page-state _]
    (:show-mobile-menu? page-state)))

(reg-sub
  ::user-groups-vec
  :<- [::user-groups]
  (fn [groups _]
    (if (= groups {})
      []
      (vals groups))))

(reg-sub
  ::tab-select
  (fn [[_ page-type] _] (subscribe [::page-state page-type]))
  (fn [page-state _] (get page-state :tab-select)))

(reg-sub
  ::tab-select-active-tab
  (fn [[_ page-type] _] (subscribe [::tab-select page-type]))
  (fn [tab-select _] (get tab-select :tab)))

(reg-sub
  ::tab-select-active?
  (fn [[_ page-type] _] (subscribe [::tab-select page-type]))
  (fn [tab-select _] (get tab-select :active? true)))
