(ns com.pringwa.optiliv.page.home
  (:require
    [clojure.string :as string]
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.events :as events]
    [com.pringwa.optiliv.config :refer [api-url]]
    [com.pringwa.optiliv.fragment.forgot-password-modal
     :refer [forgot-password-modal]]
    [com.pringwa.optiliv.fragment.xhr :as xhrs]
    [com.pringwa.optiliv.layout :as layout]
    [reagent.core :as r]
    [re-frame.core :as rf]))


(defn home-page []
  [:h2 "Welcome!"])
