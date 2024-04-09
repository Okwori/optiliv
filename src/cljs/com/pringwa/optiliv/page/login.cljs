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

     ;<div style="background: url(/img/mountains.png)"></div>
     [:section.hero.is-info.is-fullheight.has-background-image
      {:style {:background "url(/img/mountains.png)"}}
      [:ul.nav
       [:li  "Log In"]
       [:li  "Sign up"]
       [:li  "Forgot password"]
       [:li  "Subscribe"]
       [:li  "Contact us"]]
      [:div.wrapper
       [:div.rec-prism
        [:div.face.face-top
         [:div.content
          [:h2 "Subscribe"]
          [:small "Enter your email so we can send you the latest updates!"]
          [:form
           [:div.field-wrapper
            [:input {:type "text" :name "email" :placeholder "email" :required true}]
            [:label "e-mail"]]
           [:div.field-wrapper
            [:input {:type "submit"}]]]]]
        [:div.face.face-front
         [:div.content
          [:h2 "Log in"]
          ;[:form
          ; [:div.field
          ;  [:label.label {:for ""} "Email"]
          ;  [:div.control.has-icons-left
          ;   [:input.input {:type "email" :placeholder "e.g. bobsmith@gmail.com" :required true}]
          ;   [:span.icon.is-small.is-left
          ;    [:i.mdi.mdi-email-box]]]]
          ; [:div.field
          ;  [:label.label "Password"]
          ;  [:div.control.has-icons-left
          ;   [:input.input {:type "password" :placeholder "*******" :required true}]
          ;   [:span.icon.is-small.is-left
          ;    [:i.mdi.mdi-lock]]]]
          ; [:div.field
          ;  [:label.checkbox
          ;   [:input {:type "checkbox"}] "Remember me"]
          ;  [:span.psw
          ;   {:style "margin-top: 5px; margin-bottom: 0px; text-align: left;"}
             ;"Forgot Password?"]]
           ;[:div.divider {:style "display: flex; justify-content: center; align-items: center;"} "OR"]
           ;[:div.social-buttons
           ; [:button.button.is-fullwidth
           ;  {:style "background-color: #fff; border-color: #dbdbdb; display: flex;"}
             ;[:span.icon
             ; [:i.mdi.mdi-google]]
             ;[:span "Log in with Google"]]
            ;[:button.button.is-fullwidth
            ; {:style "background-color: #fff; border-color: #dbdbdb; display: flex;"}
             ;[:span.icon
             ; [:i.mdi.mdi-facebook]]
             ;[:span "Log in with Facebook"]]
            ;[:button.button.is-fullwidth
            ; {:style "background-color: #fff; border-color: #dbdbdb; display: flex;"}
             ;[:span.icon
             ; [:i.mdi.mdi-apple]]
             ;[:span "Log in with Apple"]]]
           ;[:div.field-wrapper
           ; [:input {:type "submit"
           ;          :style "margin-top:-20px"
                     ;}]]
           ;[:span.signup
           ; {:style "margin-top:5px"}
            ;"Not a user?  Sign up"]]



          ]]
        [:div.face.face-back
         [:div.content
          [:h2 "Forgot your password?"]
          [:small "Enter your email so we can send you a reset link for your password"]
          [:form
           [:div.field-wrapper
            [:input {:type "text" :name "email" :placeholder "email" :required true}]
            [:label "e-mail"]]
           [:div.field-wrapper
            [:input {:type "submit"}] "\">"]]]]
        ;[:div.face.face-right
        ; [:div.content
        ;  [:h2 "Sign up"]
        ;  [:form
        ;   [:div.field-wrapper {:style "margin-top: -1px"}
        ;    [:input {:type "text" :name "username" :placeholder "username" :required true}]
        ;    [:label "username"]]
        ;   [:div.field-wrapper
        ;    [:input {:type "text" :name "email" :placeholder "email" :required true}]
        ;    [:label "e-mail"]]
        ;   [:div.field-wrapper
        ;    [:input {:type         "password" :name "password"
        ;             :placeholder  "password"
        ;             :pattern      "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
        ;             :title "Password must include at least 1 uppercase, 1 lowercase, 1 number, 1 special character, and be at least 8 characters long."}]
        ;    [:label "password"]]
        ;   [:div.field-wrapper
        ;    [:input {:type "password" :name "password2" :placeholder "password" :required true}]
        ;    [:label "re-enter password"]]
        ;   [:label.checkbox {:style "font-size:10px"}
        ;    [:input {:type "checkbox" :required true}] "I have read and agreed to the" [:a {:href "#"} "Terms and Conditions"]]
        ;   [:div.field-wrapper {:style "margin-top: 5px; margin-bottom: -15px"}
        ;    [:input {:type "submit"}]]
        ;   [:span.singin "Already a user?  Sign in"]]]]

        [:div.face.face-left
         [:div.content
          [:h2 "Contact us"]
          [:form
           [:div.field-wrapper
            [:input {:type "text" :name "name" :placeholder "name" :required true}]
            [:label "Name"]]
           [:div.field-wrapper
            [:input {:type "text" :name "email" :placeholder "email" :required true}]
            [:label "e-mail"]]
           [:div.field-wrapper
            [:textarea {:placeholder "your message" :required true}]
            [:label "your message"]]
           [:div.field-wrapper
            [:input {:type "submit"}]]]]]

        [:div.face.face-bottom
         [:div.content
          [:div.thank-you-msg "Thank you!"]]]]]]
     ]))
