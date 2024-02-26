(ns com.pringwa.optiliv.root
  (:require
    [com.pringwa.optiliv.events :as events]
    [com.pringwa.optiliv.page.login :refer [login-page]]
    [com.pringwa.optiliv.page.home :refer [home-page]]
    com.pringwa.optiliv.xhr
    [kee-frame.core :as k]))

(def routes
  [["/" :login]
   ["/home" :home]])

(k/reg-controller
  :home
  {:params #(when (= :home (-> % :data :name)) true)
   :start  (fn [] nil)})

(k/reg-controller
  :login
  {:params #(when (= :login (-> % :data :name)) true)
   :start  (fn [] nil)})

(k/reg-controller
  :logout
  {:params #(when (= :logout (-> % :data :name)) true)
   :start  (fn [] [:logout])})

(defn root-component []
  (k/switch-route (fn [route] (get-in route [:data :name]))
                  :login [login-page]
                  :home [home-page]
                  nil [:div "..loading!"]))
