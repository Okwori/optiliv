(ns com.pringwa.optiliv.fragment.forgot-password-modal
  (:require [com.pringwa.optiliv.data :as data]
            [com.pringwa.optiliv.fragment.modal :refer [close header modal]]
            [reagent.core :as r]
            [re-frame.core :as rf]))

(defn submit-forgot-password-form [event]
  (.preventDefault event)
  (let [form-el (.-target event)
        form-data (js/FormData. form-el)]
    (rf/dispatch [:forgot-password form-data])))

(defn- status []
  (r/with-let [xhr-status (rf/subscribe [::data/xhr :forgot-password])]
              (let [{:keys [in-flight? success? error]} @xhr-status]
                (cond
                  in-flight? "⏳"
                  error [:div.has-text-danger [:p error]]
                  success?
                  [:div.has-text-success
                   [:p ""]]                                           ;;TODO Fix!
                  :else ""))))

(defn forgot-password-main-section []
  [:section.modal-card-body
   [:div.field
    [:label "Account Email"]
    [:div.control
     [:input.input {:type "email", :name "email", :required true}]]]
   [status]])

(defn forgot-password-footer []
  (letfn [(on-submit-click [e]
            (let [submit-el (js/document.getElementById
                              "forgot-password-submit")]
              (.click submit-el)))]
    [:footer.modal-card-foot
     [:a.button {:on-click close} "Cancel"]
     [:input#forgot-password-submit {:type "submit", :style {:display "none"}}]
     [:a.button.is-primary {:on-click on-submit-click} "Submit"]]))

(defn forgot-password-panel []
  [:div.modal-card
   [header "Trouble logging in?"]
   [:form#forgot-password-modal.modal-form {:on-submit
                                            submit-forgot-password-form}

    [forgot-password-main-section]
    [forgot-password-footer]]])

(defn forgot-password-modal []
  [modal :modal-type/forgot-password
   [forgot-password-panel]])

