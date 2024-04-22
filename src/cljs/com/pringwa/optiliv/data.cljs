(ns com.pringwa.optiliv.data
  (:require [re-frame.core :refer [reg-sub subscribe]]))

(reg-sub
  ::current-user
  (fn [db _] (:current-user db)))

(reg-sub
  ::active-slide-tab
  (fn [db _] (:active-slide-tab db)))

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
  ::place-type
  (fn [db _] (:place-type db)))

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
  ::signup-email
  :<- [::page-state :page-type/signup]
  (fn [page-state _]
    (:email page-state)))

(reg-sub
  ::signup-full-name
  :<- [::page-state :page-type/signup]
  (fn [page-state _]
    (:full-name page-state)))

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

(reg-sub
  ::pt-airport
  :<- [::place-type]
  (fn [checked? _] (:airport checked?)))

(reg-sub
  ::pt-aquarium
  :<- [::place-type]
  (fn [checked? _] (:aquarium checked?)))

(reg-sub
  ::pt-amusement_park
  :<- [::place-type]
  (fn [checked? _] (:amusement_park checked?)))

(reg-sub
  ::pt-atm
  :<- [::place-type]
  (fn [checked? _] (:atm checked?)))

(reg-sub
  ::pt-bakery
  :<- [::place-type]
  (fn [checked? _] (:bakery checked?)))

(reg-sub
  ::pt-bank
  :<- [::place-type]
  (fn [checked? _] (:bank checked?)))

(reg-sub
  ::pt-bar
  :<- [::place-type]
  (fn [checked? _] (:bar checked?)))

(reg-sub
  ::pt-beauty_salon
  :<- [::place-type]
  (fn [checked? _] (:beauty_salon checked?)))

(reg-sub
  ::pt-book_store
  :<- [::place-type]
  (fn [checked? _] (:book_store checked?)))

(reg-sub
  ::pt-bowling_alley
  :<- [::place-type]
  (fn [checked? _] (:bowling_alley checked?)))

(reg-sub
  ::pt-bus_station
  :<- [::place-type]
  (fn [checked? _] (:bus_station checked?)))

(reg-sub
  ::pt-cafe
  :<- [::place-type]
  (fn [checked? _] (:cafe checked?)))

(reg-sub
  ::pt-campground
  :<- [::place-type]
  (fn [checked? _] (:campground checked?)))

(reg-sub
  ::pt-casino
  :<- [::place-type]
  (fn [checked? _] (:casino checked?)))

(reg-sub
  ::pt-church
  :<- [::place-type]
  (fn [checked? _] (:church checked?)))

(reg-sub
  ::pt-city_hall
  :<- [::place-type]
  (fn [checked? _] (:city_hall checked?)))

(reg-sub
  ::pt-clothing_store
  :<- [::place-type]
  (fn [checked? _] (:clothing_store checked?)))

(reg-sub
  ::pt-department_store
  :<- [::place-type]
  (fn [checked? _] (:department_store checked?)))

(reg-sub
  ::pt-grocery_or_supermarket
  :<- [::place-type]
  (fn [checked? _] (:grocery_or_supermarket checked?)))

(reg-sub
  ::pt-gym
  :<- [::place-type]
  (fn [checked? _] (:gym checked?)))

(reg-sub
  ::pt-hospital
  :<- [::place-type]
  (fn [checked? _] (:hospital checked?)))

(reg-sub
  ::pt-bus_station
  :<- [::place-type]
  (fn [checked? _] (:bus_station checked?)))

(reg-sub
  ::pt-library
  :<- [::place-type]
  (fn [checked? _] (:library checked?)))

(reg-sub
  ::pt-mosque
  :<- [::place-type]
  (fn [checked? _] (:mosque checked?)))

(reg-sub
  ::pt-museum
  :<- [::place-type]
  (fn [checked? _] (:museum checked?)))

(reg-sub
  ::pt-night_club
  :<- [::place-type]
  (fn [checked? _] (:night_club checked?)))

(reg-sub
  ::pt-park
  :<- [::place-type]
  (fn [checked? _] (:park checked?)))

(reg-sub
  ::pt-pet_store
  :<- [::place-type]
  (fn [checked? _] (:pet_store checked?)))

(reg-sub
  ::pt-police
  :<- [::place-type]
  (fn [checked? _] (:police checked?)))

(reg-sub
  ::pt-post_office
  :<- [::place-type]
  (fn [checked? _] (:post_office checked?)))

(reg-sub
  ::pt-restaurant
  :<- [::place-type]
  (fn [checked? _] (:restaurant checked?)))

(reg-sub
  ::pt-school
  :<- [::place-type]
  (fn [checked? _] (:school checked?)))

(reg-sub
  ::pt-shopping_mall
  :<- [::place-type]
  (fn [checked? _] (:shopping_mall checked?)))

(reg-sub
  ::pt-spa
  :<- [::place-type]
  (fn [checked? _] (:spa checked?)))

(reg-sub
  ::pt-stadium
  :<- [::place-type]
  (fn [checked? _] (:stadium checked?)))

(reg-sub
  ::pt-subway_station
  :<- [::place-type]
  (fn [checked? _] (:subway_station checked?)))

(reg-sub
  ::pt-taxi_stand
  :<- [::place-type]
  (fn [checked? _] (:taxi_stand checked?)))

(reg-sub
  ::pt-train_station
  :<- [::place-type]
  (fn [checked? _] (:train_station checked?)))

(reg-sub
  ::pt-university
  :<- [::place-type]
  (fn [checked? _] (:university checked?)))

(reg-sub
  ::pt-veterinary_care
  :<- [::place-type]
  (fn [checked? _] (:veterinary_care checked?)))

(reg-sub
  ::pt-zoo
  :<- [::place-type]
  (fn [checked? _] (:zoo checked?)))

(reg-sub
  ::properties
  (fn [db _] (get db :properties)))

(reg-sub
  ::previous-slide
  (fn [db _] (get db :previous-slide)))

(reg-sub
  ::distance-radius
  (fn [db _] (get db :distance-radius)))
