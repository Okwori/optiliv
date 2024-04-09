(ns com.pringwa.optiliv.page.login
  (:require
    [com.pringwa.optiliv.config :refer [api-url]]
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.events :as events]
    [com.pringwa.optiliv.fragment.forgot-password-modal
     :refer [forgot-password-modal]]
    [com.pringwa.optiliv.fragment.xhr :as xhrs]
    [com.pringwa.optiliv.layout :as layout]
    [reagent.core :as r]
    [re-frame.core :as rf]))

(defn- forgot-password-link []
  [:div.control
   [:a.button.is-outlined.is-black
    {:on-click #(rf/dispatch [::events/set-modal-visibility true
                              :modal-type/forgot-password {}])}
    "Trouble logging in?"]])

(defn- login-form []
  (r/with-let
    [xhr-status (rf/subscribe [::data/xhr :login])]
    [:form {:method    "post"
            :action    (api-url "/login")
            :on-submit #(do
                          (.preventDefault %)
                          (rf/dispatch [:login (js/FormData.
                                                 (.-target %))]))}
     [:div.field
      [:label.label "Your Email"]
      [:div.control
       [:input.input {:type "email", :name "email" :placeholder "email@example.com"}]]]
     [:div.field
      [:label.label "Your Password"]
      [:div.control
       [:input.input {:type "password", :name "password" :placeholder "********"}]]]
     [:hr]
     [:div.field.is-grouped
      [:div.control
       [:input.button.is-primary {:type "submit", :value "Login"}]]
      [forgot-password-link]]
     [xhrs/xhr-status :login @xhr-status]]))

(defn login-page []
  (r/with-let
    [xhr-status (rf/subscribe [::data/xhr :current-user])]
    [:<>
     [layout/modal "login"
      [:div.columns.is-centered
       [:div.column.is-two-fifths
        [:div.card.has-card-header-background
         [:header.card-header.has-background-primary
          [:p.card-header-title
           [:span.icon [:i.mdi.mdi-lock.default]]
           "Login"]]
         [:div.card-content
          (let [{:keys [in-flight? success?]} @xhr-status]
            (cond
              in-flight? "Loading… ⏳"
              success? (rf/dispatch [:navigate-to-authenticated-home])
              :else [:<>
                     [login-form]
                     [:br]]))]]]]]
     [forgot-password-modal]]
    ))

(defn login-page2 []
  (r/with-let
    [xhr-status (rf/subscribe [::data/xhr :current-user])]
    [:<>
     ;; Your code goes here
     ]))
