(ns com.pringwa.optiliv.core
    (:require
      [com.pringwa.optiliv.ajax :as ajax]
      com.pringwa.optiliv.events
      [com.pringwa.optiliv.root :as root]
      com.pringwa.optiliv.xhr
      kee-frame.scroll
      [kee-frame.core :as kf]
      [re-frame.core :as rf]
      [reagent.core :as r]
      [reagent.dom :as d]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to Optiliv!"]])

;; -------------------------
;; Initialize app

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (kf/start! {:routes         root/routes
              :hash-routing?  false
              #_#_
                      :log            {:level        :debug
                                       :ns-blacklist ["kee-frame.event-logger"]}
              :initial-db     {}
              :root-component [root/root-component]})
  ;(d/render [home-page] (.getElementById js/document "app"))
  (rf/dispatch [:current-user/load]))

(defn ^:export ^:dev/once init! []
  ;(ajax/load-interceptors!)
  (mount-root))
