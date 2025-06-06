(ns com.pringwa.optiliv.root
  (:require
    [com.pringwa.optiliv.events :as events]
    [com.pringwa.optiliv.page.agents :refer [agents-page]]
    [com.pringwa.optiliv.page.buy :refer [buy-page]]
    [com.pringwa.optiliv.page.change-email :refer [change-email-page]]
    [com.pringwa.optiliv.page.change-password :refer [change-password-page]]
    [com.pringwa.optiliv.page.customers :refer [customers-page]]
    [com.pringwa.optiliv.page.help :refer [help-page]]
    [com.pringwa.optiliv.page.list-property :refer [list-property-page]]
    [com.pringwa.optiliv.page.login :refer [login-page]]
    [com.pringwa.optiliv.page.home :refer [home-page]]
    [com.pringwa.optiliv.page.register :refer [register-page]]
    [com.pringwa.optiliv.page.rent :refer [rent-page]]
    [com.pringwa.optiliv.page.signup :refer [signup-page]]
    [com.pringwa.optiliv.page.verify-email :refer [verify-email-page]]
    com.pringwa.optiliv.xhr
    [kee-frame.core :as k]))

(def routes
  [["/" :home]
   ["/change-email" :change-email]
   ["/change-password" :change-password]
   ["/agents" :agents]
   ["/buy" :buy]
   ["/customers" :customers]
   ["/help" :help]
   ["/list-property" :list-property]
   ["/login" :login]
   ["/logout" :logout]
   ["/register" :register]
   ["/rent" :rent]
   ["/signup" :signup]
   ["/verify-email" :verify-email]])

(k/reg-controller
  :agents
  {:params #(when (= :agents (-> % :data :name)) true)
   :start  (fn [] nil)}) ;;TODO

(k/reg-controller
  :buy
  {:params #(when (= :buy (-> % :data :name)) true)
   :start  [:register/load-user-groups]}) ;;TODO

(k/reg-controller
  :customers
  {:params #(when (= :customers (-> % :data :name)) true)
   :start  (fn [] nil)})

(k/reg-controller
  :home
  {:params #(when (= :home (-> % :data :name)) true)
   :start  (fn [] nil)})

(k/reg-controller
  :change-email
  {:params #(when (= :change-email (-> % :data :name)) true)
   :start  (fn [] nil)})

(k/reg-controller
  :change-password
  {:params #(when (= :change-password (-> % :data :name)) true)
   :start  (fn [] [:clear-xhr :change-password])})

(k/reg-controller
  :help
  {:params #(when (= :help (-> % :data :name)) true)
   :start  [:register/load-user-groups]})

(k/reg-controller
  :list-property
  {:params #(when (= :list-property (-> % :data :name)) true)
   :start  [:register/load-user-groups]}) ;;TODO

(k/reg-controller
  :login
  {:params #(when (= :login (-> % :data :name)) true)
   :start  (fn [] nil)})

(k/reg-controller
  :logout
  {:params #(when (= :logout (-> % :data :name)) true)
   :start  (fn [] [:logout])})

(k/reg-controller
  :register
  {:params #(when (= :register (-> % :data :name)) true)
   :start  [:register/load-user-groups]})

(k/reg-controller
  :rent
  {:params #(when (= :rent (-> % :data :name)) true)
   :start  [:register/load-user-groups]}) ;;TODO

(k/reg-controller
  :signup
  {:params #(when (= :signup (-> % :data :name)) true)
   :start  (fn [] [:verify-token])})

(k/reg-controller
  :verify-email
  {:params #(when (= :verify-email (-> % :data :name)) true)
   :start  (fn [] [:verify-email])})

(defn root-component []
  (k/switch-route (fn [route] (get-in route [:data :name]))
                  :login [login-page]
                  :change-email [change-email-page]
                  :change-password [change-password-page]
                  :agents [agents-page]
                  :buy [buy-page]
                  :customers [customers-page]
                  :list-property [list-property-page]
                  :logout "Signing off... see you!"
                  :help [help-page]
                  :home [home-page]
                  :register [register-page]
                  :rent [rent-page]
                  :signup [signup-page]
                  :verify-email [verify-email-page]
                  nil [:div "..loading!"]))
