(ns com.pringwa.optiliv.page.change-password
  (:require
    [com.pringwa.optiliv.config :refer [api-url]]
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.fragment.unauthorized :refer [unauthorized]]
    [com.pringwa.optiliv.layout :as layout]
    [kee-frame.core :as k]
    [reagent.core :as r]
    [re-frame.core :as rf]))

(k/reg-event-db
  ::flag-mismatch-error
  [(rf/path :page :page-type/change-password)]
  (fn [_ _]
    {:mismatch-error true}))

(k/reg-event-db
  ::clear-mismatch-error
  [(rf/path :page :page-type/change-password)]
  (fn [_ _]
    {:mismatch-error false}))

(k/reg-event-fx
  ::submit
  (fn [_ [form-data]]
    (let [p1 (.get form-data "password")
          p2 (.get form-data "confirmation")]
      (if (= p1 p2)
        {:dispatch-n [[::clear-mismatch-error]
                      [:change-password form-data]]}
        {:dispatch [::flag-mismatch-error]}))))

(defn- status []
  (r/with-let
    [mismatch-error (rf/subscribe [::data/mismatch-error2])
     xhr-status (rf/subscribe [::data/xhr :change-password])]
    (if @mismatch-error
      [:div.has-text-danger [:p "Error: your passwords don't match."]]
      (let [{:keys [in-flight? success? error]} @xhr-status]
        (cond
          in-flight? "⏳"                                    ; this is an emoji character, in case you can't see it
          error [:div.has-text-danger [:p error]]
          success? [:div.tag.is-success
                    [:p "Password successfully changed. "
                     [:a {:href     "#"
                          :on-click #(rf/dispatch
                                       [:navigate-to-authenticated-home])}
                      "Go home."]]]
          :else "")))))

(defn- change-password-form [email]
  (let [password-input-opts {:type        "password"
                             :placeholder "********"
                             :required    true}]
    [:<>
     [:form {:method    "put"
             :action    (api-url "/change-password")
             :on-submit #(do
                           (.preventDefault %)
                           (rf/dispatch [::submit
                                         (js/FormData. (.-target %))]))}
      [:div.field.is-horizontal
       [:div.field-label.is-normal
        [:label.label "Current Email"]]
       [:div.field
        [:div.control
         [:input.input {:type "email", :name "current-email" :value email, :disabled true}]]]]
      [:div.field.is-horizontal
       [:div.field-label.is-normal
        [:label.label "Password"]]
       [:div.field
        [:div.control
         [:input.input (assoc password-input-opts :name "password")]]]]
      [:div.field.is-horizontal
       [:div.field-label.is-normal
        [:label.label "Confirm Password"]]
       [:div.field
        [:div.control
         [:input.input (assoc password-input-opts :name "confirmation")]]]]
      [:hr]
      [:div.field.is-horizontal
       [:div.field-label.is-normal]
       [:div.field
        [:div.field.is-grouped
         [:div.control
          [:input.button.is-primary {:type "submit", :value "Change Password"}]]]]]]
     [:br]
     [status]
     [:br]]))

(defn change-password-page []
  (r/with-let
    [email (rf/subscribe [::data/email])]
    (if-not @email
      [unauthorized]
      [layout/modal "change-password"
       [:section.section.is-main-section
        [:div.container
         [:div.columns.is-centered
          [:div.column.is-two-fifths
           [:div.card.has-card-header-background
            [:header.card-header.has-background-primary
             [:p.card-header-title
              [:span.icon [:i.mdi.mdi-settings.default]]
              "Change Password"]]
            [:div.card-content
             [change-password-form @email]]]]]]]])))
