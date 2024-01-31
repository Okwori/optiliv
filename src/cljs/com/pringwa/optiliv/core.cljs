(ns com.pringwa.optiliv.core
    (:require
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
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export ^:dev/once init! []
  (mount-root))
