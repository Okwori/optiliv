(ns com.pringwa.optiliv.page.signup
  (:require
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.events :as events]
    [com.pringwa.optiliv.config :refer [api-url]]
    [com.pringwa.optiliv.fragment.inputs :refer [field textarea]]
    [com.pringwa.optiliv.fragment.header :refer [header]]
    [com.pringwa.optiliv.fragment.unauthorized :refer [unauthorized]]
    [com.pringwa.optiliv.fragment.xhr :as xhrs]
    [com.pringwa.optiliv.layout :as layout]
    [clojure.string :as str]
    [kee-frame.core :as k]
    [reagent.core :as r]
    [re-frame.core :as rf]))

(defn- success-status [_]
  [:div.has-text-success {:style {:margin-top "1rem"}}
   [:p "Signup successful! Now, "
    [:a {:href (k/path-for [:login])}
     "login"]
    "."]])

(defn- status-in-view [in-flight? success? error on-success on-error]
  (let [elements (cond
                   in-flight? "⏳"
                   error [on-error error]
                   success? [on-success])
        scroll-to-bottom #(js/window.scrollTo 0 js/document.body.scrollHeight)]
    ;(js/window.setTimeout scroll-to-bottom 100)
    elements))

(defn status [xhr-key on-success on-error]
  (r/with-let
    [xhr-status (rf/subscribe [::data/xhr xhr-key])]
    (let [{:keys [in-flight? success? error]} @xhr-status]
      (when (or in-flight? success? error)
        [status-in-view in-flight? success? error on-success on-error]))))

(defn- bad-token-error []
  [:div.has-text-danger
   [:p "Error: your signup token could not be found."]
   [:p "Please ensure that you are at the address included in your welcome "
    "email."]])

(defn- error-status [error-message]
  [:div.has-text-danger
   [:p "There was an error with the attempt to create a new user. The error "
    "response from the server follows:"]
   [:p (interpose [:br] (str/split error-message #"\n"))]])

(defn- signup-form []
  (r/with-let
    [token (rf/subscribe [::data/signup-token])
     email (rf/subscribe [::data/signup-email])
     full-name (rf/subscribe [::data/signup-full-name])
     mobile (rf/subscribe [::data/signup-mobile])
     current-user-name (rf/subscribe [::data/current-user-name])
     current-user-name-coll (str/split (or @current-user-name "My Account") #" ")]
    [layout/standard "signup-page"
     [header (first current-user-name-coll) (last current-user-name-coll) false]
     [:section.hero.is-hero-bar.is-main-hero
      [:div.hero-body
       [:div.level
        [:div.level-left
         [:div.level-item.is-hero-content-item
          [:div [:h1.title.is-spaced "Set Up Your OptiLiv Account"]]]]]]]
     [:section.section.is-main-section
      [:form {:method    "post"
              :action    (api-url "/signup")
              :on-submit #(do
                            (.preventDefault %)
                            (rf/dispatch [:signup
                                          (js/FormData. (.-target %))]))}
       [:input {:type "hidden", :name "token", :value @token}]
       [:div.box
        [:div.notification.is-card-toolbar
         [:div.level
          [:div.level-left
           [:div.level-item
            [:span.subtitle.is-4.has-text-primary "Basic Info"]]]]]
        [:div.columns
         ;(when full-name?
         [:div.column
          [field "full-name" "text" "Full Name" false nil
           {:disabled false
            :default-value @full-name}
           "You cannot change this yet, "]]
         ;)
         [:div.column
          [field "email" "text" "Email" false nil
           {:disabled true
            :default-value @email}
           "You cannot change this yet, "]]]
        [:div.columns
         [:div.column
          [field "password" "password" "Password" false nil]]
         [:div.column
          [field "confirm-password" "password" "Confirm Password" false nil]]]
        [:div.columns
         [:div.column
          [field "mobile" "text" "Mobile" false nil
           {:disabled false
            :default-value @mobile}]]
         [:div.column]]
        [:div.columns
         [:div.column
          [:input.button.is-primary {:type "submit", :value "Create"}]]]]
       [status :signup success-status error-status]]]]))

(defn signup-page []
  (r/with-let
    [xhr-status (rf/subscribe [::data/xhr :verify-token])]
    (let [{:keys [in-flight? success? error]} @xhr-status]
      (cond
        in-flight? "⏳ Loading…"
        error [bad-token-error]
        success? [signup-form]
        :else ""))))
