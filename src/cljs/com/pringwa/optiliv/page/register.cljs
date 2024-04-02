(ns com.pringwa.optiliv.page.register
  (:require
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.events :as events]
    [com.pringwa.optiliv.config :refer [api-url]]
    [com.pringwa.optiliv.fragment.header
     :refer [header]]
    [com.pringwa.optiliv.fragment.unauthorized :refer [unauthorized]]
    [com.pringwa.optiliv.fragment.xhr :as xhrs]
    [com.pringwa.optiliv.layout :as layout]
    [clojure.string :as str]
    [reagent.core :as r]
    [re-frame.core :as rf]))


(defn register-page []                                      ;TODO!
  (let [current-user-type @(rf/subscribe [::data/current-user-type])
        current-user-name (rf/subscribe [::data/current-user-name])
        current-user-name-coll (str/split (or @current-user-name "My Account") #" ")
        token (rf/subscribe [::data/signup-token])
        email (rf/subscribe [::data/signup-email])]
    (if-not (= "Optiliv" current-user-type)
      [unauthorized]
      [layout/with-verified-email-check
       [layout/standard "register-page"
        [header (first current-user-name-coll) (last current-user-name-coll) false]
        [:section.hero.is-hero-bar.is-main-hero
         [:div.hero-body
          [:div.level
           [:div.level-left
            [:div.level-item.is-hero-content-item
             [:div [:h1.title.is-spaced [:b "Invite a User!"]]]]]]]]
        [:section.section.is-main-section
         [:div.container
          [:div.columns.is-centered
           [:div.column.is-two-fifths
            [:div.card.has-card-header-background
             [:header.card-header.has-background-primary
              [:p.card-header-title
               [:span.icon [:i.mdi.mdi-account-plus]]
               "New User"]]
             [:form {:method    "post"
                     :action    (api-url "/register")
                     :on-submit #(do
                                   (.preventDefault %)
                                   (rf/dispatch [:register (js/FormData. (.-target %))]))}
              [:input {:type "hidden", :name "token", :value @token}]
              [:div.field.is-horizontal
               [:div.field-label.is-normal
                [:label.label "Email"]]
               [:div.field
                [:div.control
                 [:input.input {:type "email"}]]]]]]]]]]]])))
