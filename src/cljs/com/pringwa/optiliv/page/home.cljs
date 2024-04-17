(ns com.pringwa.optiliv.page.home
  (:require
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.events :as events]
    [com.pringwa.optiliv.config :refer [api-url]]
    [com.pringwa.optiliv.fragment.forgot-password-modal
     :refer [forgot-password-modal]]
    [com.pringwa.optiliv.fragment.header
     :refer [header]]
    [com.pringwa.optiliv.fragment.unauthorized :refer [unauthorized]]
    [com.pringwa.optiliv.fragment.xhr :as xhrs]
    [com.pringwa.optiliv.layout :as layout]
    [clojure.string :as str]
    [reagent.core :as r]
    [re-frame.core :as rf]))


(defn home-page []
  (let [current-user-type @(rf/subscribe [::data/current-user-type])
        current-user-name (rf/subscribe [::data/current-user-name])
        current-user-name-coll (str/split (or @current-user-name "My Account") #" ")
        active-slide-tab @(rf/subscribe [::data/active-slide-tab])]
    (if-not (or (= "Optiliv" current-user-type)
                (= "Agents" current-user-type)
                (= "Customers" current-user-type))
      [unauthorized]
      [layout/with-verified-email-check
       [layout/standard "home-page"
        [header (first current-user-name-coll) (last current-user-name-coll) false]
        [:section.hero.is-hero-bar.is-main-hero
         (when (not active-slide-tab)
          [:div.hero-body
           [:div.level
            [:div.level-left
             [:div.level-item.is-hero-content-item
              [:div [:h3.title.is-spaced [:b "Tell us your preferences"]]]]]]])
         (when active-slide-tab
           [:div.hero-body
            [:div.level
             [:div.level-left
              [:div.level-item.is-hero-content-item
               [:div [:h3.title.is-spaced [:b "Properties found"]]]]]]])]
        (when (not active-slide-tab)
         [:section.section.is-main-section
          [:div.container
           [:div.columns.is-centered
            [:div.column
             [:div.card.has-card-header-background
              [:div.card-content
               [:div.tabs.is-fullwidth
                [:ul
                 [:li
                  {:disabled true}
                  [:a
                   [:span.icon [:i.mdi.mdi-pan-left {:aria-hidden "true"}]]
                   [:span "Previous"]]]
                 [:li
                  [:a
                   [:span.icon [:i.mdi.mdi-skip-next {:aria-hidden "true"}]]
                   [:span "Skip"]]]
                 [:li
                  [:a
                   {:on-click #(rf/dispatch [::events/put-next-slide])}
                   [:span "Next"]
                   [:span.icon [:i.mdi.mdi-pan-right {:aria-hidden "true"}]]]]]]
               [:div.field
                [:label.label "Play Types"]
                [:div.notification.is-success
                 [:button.delete]
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox" :on-change #(rf/dispatch [::events/change-place-types :airport])}]
                     [:span] " Airport"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"}]
                     [:span] " Library"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"}]
                     [:span] " School"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"}]
                     [:span] " Hospital"]]]]]
                ;; DIstance
                [:label.label "Distance Radius"]
                [:div.notification.is-info
                 [:button.delete]
                 [:input.input {:type "text" :placeholder "Distance in meters"}]]

                ;; Polution
                [:label.label "Pollution Levels"]
                [:label.label "Air"]
                [:div.columns
                 [:div.column
                  [:input.slider.is-fullwidth {:type "range" :min "0" :max "100" :value "35"}]]]
                [:label.label "Noise"]
                [:div.columns
                 [:div.column
                  [:input.slider.is-fullwidth {:type "range" :min "0" :max "100" :value "65"}]]]

                ;; Crime
                [:label.label "Crime"]
                [:div.notification.is-danger
                 [:button.delete]
                 [:div.columns
                  [:div.column
                   [:div.select.is-rounded
                    [:select [:option "choose"] [:option "High"] [:option "Medium"] [:option "Low"]]]]]
                 ]

                ;;Sun direction
                [:label.label "Sun Direction"]
                [:div.notification.is-warning
                 [:button.delete]
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"}]
                     [:span] " Due East"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"}]
                     [:span] " North"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"}]
                     [:span] " South"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"}]
                     [:span] " West"]]]]]]]]]]]])
        (when active-slide-tab
          [:div.hero
           [:div.hero-body
            [:div.filer-bar
             [:div.columns
              [:div.custom-fields
               [:div.field.has-addons.has-addons-right
                [:div.control
                 [:input.input.is-rounded {:type "text" :placeholder "City, Neighborhood, ZIP, Address"}]]
                [:p.control
                 [:a.button.is-rounded [:span.mdi.mdi-magnify]]]]]
              [:div.custom-fields
               [:div.field
                [:div.control
                 [:div.select.is-rounded
                  [:select [:option "For Sale"] [:option "For Rent"] [:option "Sold"]]]]]]
              [:div.custom-fields
               [:div.field
                [:div.control
                 [:div.select.is-rounded
                  [:select [:option "Beds"] [:option {:value "1"} "1"] [:option {:value "2"} "2"]
                   [:option {:value "3"} "3"] [:option {:value "4"} "4"] [:option {:value "5"} "5"]]]]]]
              [:div.custom-fields
               [:div.field
                [:div.control
                 [:div.select.is-rounded
                  [:select
                   [:option "Baths"]
                   [:option {:value "1"} "1"]
                   [:option {:value "2"} "2"]
                   [:option {:value "3"} "3"]
                   [:option {:value "4"} "4"]
                   [:option {:value "5"} "5"]]]]]]
              [:div.custom-fields
               [:div.field
                [:div.control
                 [:div.select.is-rounded
                  [:select
                   [:option {:value "-1"} "Price"]
                   [:option {:value "50,000"} "$50,000"]
                   [:option {:value "100,000"} "$100,000"]
                   [:option {:value "150,000"} "$150,000"]
                   [:option {:value "200,000"} "$200,000"]
                   [:option {:value "250,000"} "$250,000"]
                   [:option {:value "300,000"} "$300,000"]]]]]]
              [:div.custom-fields
               [:div.field
                [:div.control.custom-control
                 [:div.select.is-rounded
                  [:select
                   [:option "Property Type"]
                   [:option "House"]
                   [:option "Apartment"]
                   [:option "Condo"]
                   [:option "Townhouse"]
                   [:option "Lot/Land"]]]]]]
              [:div.custom-fields
               [:div.field
                [:div.control.custom-control
                 [:button.button.is-warning.is-rounded {:onclick "toggleFilters()"} "All Filters"]]]]]]]
           ;[:div.columns
           ; [:div.column
           ;  [:section.hero
           ;   [:div.hero-body
           ;    [:div.container
           ;     [:div#app.row.columns.is-multiline
           ;      [:div.column.is-5 {:v-for "card in cardData" ::key "card.id"}
           ;       [:div.card.large
           ;        [:div.card-image
           ;         [:figure.image.is-16by9
           ;          [:img {:src "card.image" :alt "Image"}]]]
           ;        [:div.card-content
           ;         [:div.media
           ;          [:div.media-content
           ;           [:p.title.is-5.has-text-weight-bold "{{card.user.price}}"]
           ;           [:p.subtitle.is-6.is-inline.is-centered [:span.mdi.mdi-bed-queen] "{{card.user.beds}} |"]
           ;           [:p.subtitle.is-6.is-inline.centered-content [:span.mdi.mdi-shower] "{{card.user.baths}}  |"]
           ;           [:p.subtitle.is-6.is-inline.centered-content [:span.mdi.mdi-ruler-square] "{{card.user.dimensions}}"]
           ;           [:p.subtitle.is-6 "{{card.user.address}}"]]]]]]]]]]]
           ; [:div.column
           ;  [:section.hero
           ;   [:div.hero-body
           ;    [:div.container
           ;     [:div.card.large
           ;      [:div.media
           ;       [:div.media-content
           ;        [:div#googleMap
           ;         [:script "function myMap() {
           ;                                      var mapProp= {
           ;                                          center:new google.maps.LatLng(32.5252,-93.7502),
           ;                                          zoom:12,
           ;                                      };
           ;                                      var map = new google.maps.Map(document.getElementById(\"googleMap\"),mapProp);
           ;                                  }"]
           ;         [:script {:src "https://maps.googleapis.com/maps/api/js?key=AIzaSyCo9newQ_qNyc1HUK_LcpOtaQlUXTjc2co&callback=myMap"}]]]]]]]]]]
           ])]])))
