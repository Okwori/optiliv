(ns com.pringwa.optiliv.page.change-email
  (:require
   [com.pringwa.optiliv.config :refer [api-url]]
   [com.pringwa.optiliv.data :as data]
   [com.pringwa.optiliv.fragment.unauthorized :refer [unauthorized]]
   [com.pringwa.optiliv.layout :as layout]
   [kee-frame.core :as k]
   [reagent.core :as r]
   [re-frame.core :as rf]))

(defn- status []
  (r/with-let [xhr-status (rf/subscribe [::data/xhr :change-email])]
    (let [{:keys [in-flight? success? error]} @xhr-status]
      (cond
        in-flight? "⏳" ; this is an emoji character, in case you can't see it
        error [:div.has-text-danger [:p error]]
        success? [:div.tag.is-success [:p "Email successfully changed. "
                                       [:a {:href (k/path-for [:login])}
                                        "Go home."]]]
        :else ""))))

(defn- change-email-form [email]
  [:form {:method "post"
          :action (api-url "/change-email")
          :on-submit #(do
                        (.preventDefault %)
                        (rf/dispatch [:change-email
                                      (js/FormData. (.-target %))]))}
   [:div.field.is-horizontal
    [:div.field-label.is-normal
     [:label.label "Current Email"]]
    [:div.field
     [:div.control
      [:input.input {:type "email", :name "current-email" :value email, :disabled true}]]]]
   [:div.field.is-horizontal
    [:div.field-label.is-normal
     [:label.label "New Email"]]
    [:div.field
     [:div.control
      [:input.input {:type "email", :name "email", :required true}]]]]
   [:hr]
   [:div.field.is-horizontal
    [:div.field-label]
    [:div.field
     [:div.field.is-grouped
      [:div.control
       [:input.button.is-primary {:type "submit", :value "Submit"}]]]]]
   [status]
   [:br]])

(defn change-email-page []
  (r/with-let [email (rf/subscribe [::data/email])]
    (if-not @email
      [unauthorized]
      [layout/modal "change-email"
       [:section.section.is-main-section
        [:div.container
         [:div.columns.is-centered
          [:div.column.is-two-fifths
           [:div.card.has-card-header-background
            [:header.card-header.has-background-primary
             [:p.card-header-title
              [:span.icon [:i.mdi.mdi-email-edit-outline.default]]
              "Change Email"]]
            [:div.card-content
             [change-email-form @email]]]]]]]])))
