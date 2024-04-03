(ns com.pringwa.optiliv.page.customers
  (:require
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.events :as events]
    [com.pringwa.optiliv.config :refer [api-url]]
    [com.pringwa.optiliv.fragment.header :refer [header]]
    [com.pringwa.optiliv.fragment.unauthorized :refer [unauthorized]]
    [com.pringwa.optiliv.fragment.xhr :as xhrs]
    [com.pringwa.optiliv.layout :as layout]
    [clojure.string :as str]
    [reagent.core :as r]
    [re-frame.core :as rf]))

(defn customers-page []
  (let [current-user-type @(rf/subscribe [::data/current-user-type])
        current-user-name (rf/subscribe [::data/current-user-name])
        current-user-name-coll (str/split (or @current-user-name "My Account") #" ")]
    (if-not (= "Customers" current-user-type)
      [unauthorized]
      [layout/with-verified-email-check
       [layout/standard "customers-page"
        [header (first current-user-name-coll) (last current-user-name-coll) false]
        [:section.hero.is-hero-bar.is-main-hero
         [:div.hero-body
          [:div.level
           [:div.level-left
            [:div.level-item.is-hero-content-item
             [:div [:h1.title.is-spaced [:b "Welcome Customer"]]]]]]]]
        [:section.section.is-main-section
         [:div.container
          [:div.columns.is-centered
           [:div.column.is-two-fifths
            [:div.card.has-card-header-background
             [:header.card-header.has-background-primary
              [:p.card-header-title
               [:span.icon [:i.mdi.mdi-account-plus]]
               ""]]
             [:div.card-content
              ]]]]]]]])))
