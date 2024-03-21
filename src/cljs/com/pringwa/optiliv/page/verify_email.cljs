(ns com.pringwa.optiliv.page.verify-email
  (:require
   [com.pringwa.optiliv.data :as data]
   [com.pringwa.optiliv.layout :as layout]
   [kee-frame.core :as k]
   [reagent.core :as r]
   [re-frame.core :as rf]))

(defn- status []
  (r/with-let [xhr-status (rf/subscribe [::data/xhr :verify-email])]
    (let [{:keys [in-flight? success? error]} @xhr-status]
      (cond
        in-flight? "Loading… ⏳" ; this is an emoji character, in case you can't see it
        error [:div.has-text-danger [:p error]]
        success? [:div.tag.is-success [:p "Email successfully verified. "
                                [:a {:href (k/path-for [:login])}
                                 "Go home."]]]
        :else ""))))

(defn verify-email-page []
  [layout/modal "verify-email"
   [:<>
    [:h3.subtitle "Verify Email"]
    [status]]])
