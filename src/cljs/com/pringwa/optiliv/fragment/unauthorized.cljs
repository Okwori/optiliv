(ns com.pringwa.optiliv.fragment.unauthorized
  (:require [com.pringwa.optiliv.data :as data]
            [kee-frame.core :as k]
            [re-frame.core :as rf]))

(defn- unauthorized-message []
  [:p.subtitle.has-text-danger "You are not authorized to view this page. "
   [:a {:href (k/path-for [:login])} "Go home."]])

(defn- wait-for-current-user-load-then
  [success-markup]
  (let [xhr-status @(rf/subscribe [::data/xhr :current-user])
        {:keys [in-flight? success? error]} xhr-status]
    (cond
      in-flight? "Loading… ⏳"
      error (rf/dispatch [:navigate-to :login])
      success? success-markup)))

(defn unauthorized []
  [wait-for-current-user-load-then
   [unauthorized-message]])

