(ns com.pringwa.optiliv.page.unverified-email
  (:require
   [com.pringwa.optiliv.config :refer [api-url]]
   [com.pringwa.optiliv.data :as data]
   [kee-frame.core :as k]
   [reagent.core :as r]
   [re-frame.core :as rf]))

(defn- status []
  (r/with-let [xhr-status (rf/subscribe [::data/xhr :change-email])]
    (let [{:keys [in-flight? success? error]} @xhr-status]
      (cond
        in-flight? "⏳"
        error [:div.has-text-danger [:p error]]
        success? [:div.tag.is-success [:p "Verification email sent."]]
        :else ""))))

(defn- form []
  (r/with-let [email (rf/subscribe [::data/email])]
    [:form {:method "post"
            :action (api-url "/change-email")
            :on-submit #(do
                          (.preventDefault %)
                          (rf/dispatch [:change-email
                                        (js/FormData. (.-target %))]))}
     [:div.field.is-horizontal
      [:div.field-label.is-normal
       [:label.label "Unverified Email Address"]]
      [:div.field
       [:div.control
        [:input.input {:type "email", :name "display-email"
                 :value @email, :disabled true}]]]]
     [:input {:type "hidden", :name "email", :value @email}]
     [:div.field.is-horizontal
      [:div.field-label.is-normal]
      [:div.field
       [:div.control
        [:input.button.is-primary {:type "submit", :value "Re-send Verification Email"}]]]]
     [status]]))

(defn unverified-email-page-inner []
  [:<>
   [:div.columns.is-centered
    [:div.column.is-two-fifths
     [:div.card.has-card-header-background
      [:header.card-header.has-background-primary
       [:p.card-header-title
        "Unverified Email"]]
      [:div.card-content
       [:header.has-text-grey
        [:p "You must verify your email before using the site. "
         "Please check your email for a verification link."]
        [:p "If you can't find the email, you can send another one using the button below."]]
       [form]]]]]])
