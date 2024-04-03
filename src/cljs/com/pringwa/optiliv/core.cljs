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
;; Initialize app

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (kf/start! {:routes         root/routes
              :hash-routing?  false
              :initial-db     {}
              :root-component [root/root-component]})
  (rf/dispatch [:current-user/load]))

(defn ^:export ^:dev/once init! []
  (mount-root))
