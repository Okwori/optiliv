(ns com.pringwa.optiliv.page.rent
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
    [reagent.dom :as rdom]
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

; Another attempt, using props and lifecycle to udpate marker position
(defn gmap-component []
  (let [gmap    (atom nil)
        options (clj->js {"zoom" 10})
        update  (fn [comp]
                  (let [{:keys [latitude longitude]} (r/props comp)
                        latlng (js/google.maps.LatLng. 32.5252, -93.7502)]
                    (.setPosition (:marker @gmap) latlng)
                    (.panTo ^js (:map @gmap) latlng)))]

    (r/create-class
      {:reagent-render (fn []
                         [:div
                          [:h4 "Map"]
                          [:div#map-canvas {:style {:height "600px"}}]])

       :component-did-mount (fn [comp]
                              (let [canvas  (.getElementById js/document "map-canvas")
                                    gm      (js/google.maps.Map. canvas options)
                                    marker  (js/google.maps.Marker. (clj->js {:map gm :title "Drone"}))]
                                (reset! gmap {:map gm :marker marker}))
                              (update comp))

       :component-did-update update
       :display-name "gmap-component"})))

(defn gmap-wrapper []
  (let [pos (rf/subscribe [::data/distance-radius])]
    (fn []
      [gmap-component @pos])))

(defn rent-page []
  (let [current-user-type @(rf/subscribe [::data/current-user-type])
        current-user-name (rf/subscribe [::data/current-user-name])
        current-user-name-coll (str/split (or @current-user-name "My Account") #" ")]
    (if-not (= "Optiliv" current-user-type)
      [unauthorized]
      [layout/with-verified-email-check
       [layout/standard "rent-page"
        [header (first current-user-name-coll) (last current-user-name-coll) false]
        [:section.hero.is-hero-bar.is-main-hero
         [:div.hero-body
          [:div.level
           [:div.level-left
            [:div.level-item.is-hero-content-item
             [:div [:h1.title.is-spaced [:b "Invite a User!"]]]]]]]]
        [gmap-wrapper]]])))
