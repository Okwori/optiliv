(ns com.pringwa.optiliv.page.buy
  (:require
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.events :as events]
    [com.pringwa.optiliv.config :refer [api-url]]
    [com.pringwa.optiliv.fragment.header :refer [header]]
    [com.pringwa.optiliv.fragment.select-user-groups :refer [select-user-groups]]
    [com.pringwa.optiliv.fragment.unauthorized :refer [unauthorized]]
    [com.pringwa.optiliv.layout :as layout]
    [clojure.string :as str]
    [reagent.core :as r]
    [re-frame.core :as rf]))


(defn buy-page []
  (let [current-user-type @(rf/subscribe [::data/current-user-type])
        current-user-name (rf/subscribe [::data/current-user-name])
        current-user-name-coll (str/split (or @current-user-name "My Account") #" ")]
    (if-not (= "Optiliv" current-user-type)
      [unauthorized]
      [layout/with-verified-email-check
       [layout/standard "buy-page"
        [header (first current-user-name-coll) (last current-user-name-coll) false]
        [:section.hero.is-hero-bar.is-main-hero
         [:div.hero-body
          [:div.level
           [:div.level-left
            [:div.level-item.is-hero-content-item
             [:div [:h1.title.is-spaced [:b "TODO!"]]]]]]]]]])))
