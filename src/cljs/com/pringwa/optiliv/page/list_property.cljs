(ns com.pringwa.optiliv.page.list-property
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

(defn- submit-form [event]
  (.preventDefault event)
  (let [form-el (.-target event)
        form-data (js/FormData. form-el)]
    (rf/dispatch [:register form-data])))

(defn- error-status [error-message]
  [:div.has-text-danger
   [:p "There was an error with the attempt to create a new user. The error "
    "response from the server follows:"]
   [:p (interpose [:br] (str/split error-message #"\n"))]])

(defn- register-status []
  (let [create-xhr-status (rf/subscribe [::data/xhr :register])]
    (fn []
      (let [{:keys [in-flight? error success?]} @create-xhr-status]
        (cond
          in-flight? "⏳"
          error [error-status error]
          success?
          [:div.has-text-success
           "Success! The user will receive an email with a sign-up link."])))))

(defn list-property-page []
  (let [current-user-type @(rf/subscribe [::data/current-user-type])
        current-user-name (rf/subscribe [::data/current-user-name])
        current-user-name-coll (str/split (or @current-user-name "My Account") #" ")]
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
             [:div.card-content
              [:form {:method    "post"
                      :action    (api-url "/register")
                      :on-submit #(do
                                    (.preventDefault %)
                                    (rf/dispatch [:register (js/FormData. (.-target %))]))}
               [:div.field.is-horizontal
                [:div.field-label.is-normal [:label.label "Email"]]
                [:div.field
                 [:div.control
                  [:input.input {:type "email" :name "email" :required true}]]]]
               [:div.field.is-horizontal
                [:div.field-label.is-normal [:label.label "Full Name"]]
                [:div.field
                 [:div.control
                  [:input.input {:type "text" :name "full-name" :required true}]]]]
               [:div.field.is-horizontal
                [:div.field-label.is-normal [:label.label "Mobile"]]
                [:div.field
                 [:div.control
                  [:input.input {:type "text" :name "mobile"}]]]]
               [:div.field.is-horizontal
                [:div.field-label.is-normal [:label.label "User Group"]]
                [:div.field
                 [:div.control.select
                  [select-user-groups nil]]]]
               [:hr]
               [:div.field.is-horizontal
                [:div.field-label]
                [:div.field
                 [:div.field.is-grouped
                  [:div.control
                   [:input.button.is-primary {:type "submit", :value "Invite"}]]]]]]
              [register-status]]]]]]]]])))
