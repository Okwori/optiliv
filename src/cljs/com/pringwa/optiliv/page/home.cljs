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
                  [:a.is-disabled
                   [:span.icon [:i.mdi.mdi-pan-left {:aria-hidden "true"}]]
                   [:span "Previous"]
                   [:span.icon [:i.mdi.mdi-cancel {:aria-hidden "true"}]]]]
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

                ;; Distance
                [:label.label "Distance Radius"]
                [:div.notification.is-info
                 [:button.delete]
                 [:input.input {:type "number" :placeholder "Distance in meters"}]]

                ;; Place Types
                [:label.label "Play Types"]
                [:div.notification
                 [:button.delete]
                 ;; line 1
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-airport])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :airport
                                                        (not @(rf/subscribe [::data/pt-airport]))])}]
                     [:span] " Airport"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-aquarium])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :aquarium
                                                        (not @(rf/subscribe [::data/pt-aquarium]))])}]
                     [:span] " Aquarium"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-amusement_park])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :amusement_park
                                                        (not @(rf/subscribe [::data/pt-amusement_park]))])}]
                     [:span] " Amusement Park"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-atm])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :atm
                                                        (not @(rf/subscribe [::data/pt-atm]))])}]
                     [:span] " ATM"]]]]
                 ;; line 2
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-bakery])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :bakery
                                                        (not @(rf/subscribe [::data/pt-bakery]))])}]
                     [:span] " Bakery"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-bank])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :bank
                                                        (not @(rf/subscribe [::data/pt-bank]))])}]
                     [:span] " Bank"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-bar])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :bar
                                                        (not @(rf/subscribe [::data/pt-bar]))])}]
                     [:span] " Bar"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-beauty_salon])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :beauty_salon
                                                        (not @(rf/subscribe [::data/pt-beauty_salon]))])}]
                     [:span] " Beauty Salon"]]]]
                 ;; line 3
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-book_store])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :book_store
                                                        (not @(rf/subscribe [::data/pt-book_store]))])}]
                     [:span] " Book Store"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-bowling_alley])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :bowling_alley
                                                        (not @(rf/subscribe [::data/pt-bowling_alley]))])}]
                     [:span] " Bowling Alley"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-bus_station])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :bus_station
                                                        (not @(rf/subscribe [::data/pt-bus_station]))])}]
                     [:span] " Bus Station"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-cafe])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :cafe
                                                        (not @(rf/subscribe [::data/pt-cafe]))])}]
                     [:span] " Cafe"]]]]
                 ;; line 4
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-campground])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :campground
                                                        (not @(rf/subscribe [::data/pt-campground]))])}]
                     [:span] " Campground"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-casino])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :casino
                                                        (not @(rf/subscribe [::data/pt-casino]))])}]
                     [:span] " Casino"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-church])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :church
                                                        (not @(rf/subscribe [::data/pt-church]))])}]
                     [:span] " Church"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-city_hall])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :city_hall
                                                        (not @(rf/subscribe [::data/pt-city_hall]))])}]
                     [:span] " City Hall"]]]]
                 ;; line 5
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-clothing_store])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :clothing_store
                                                        (not @(rf/subscribe [::data/pt-clothing_store]))])}]
                     [:span] " Clothing Store"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-department_store])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :department_store
                                                        (not @(rf/subscribe [::data/pt-department_store]))])}]
                     [:span] " Department Store"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-grocery_or_supermarket])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :grocery_or_supermarket
                                                        (not @(rf/subscribe [::data/pt-grocery_or_supermarket]))])}]
                     [:span] " Grocery/Supermarket"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-gym])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :grocery_or_supermarket
                                                        (not @(rf/subscribe [::data/pt-gym]))])}]
                     [:span] " Gym"]]]]
                 ;; line 6
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-hospital])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :hospital
                                                        (not @(rf/subscribe [::data/pt-hospital]))])}]
                     [:span] " Hospital"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-library])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :library
                                                        (not @(rf/subscribe [::data/pt-library]))])}]
                     [:span] " Library"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-mosque])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :mosque
                                                        (not @(rf/subscribe [::data/pt-mosque]))])}]
                     [:span] " Mosque"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-museum])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :museum
                                                        (not @(rf/subscribe [::data/pt-museum]))])}]
                     [:span] " Museum"]]]]
                 ;; line 7
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-night_club])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :night_club
                                                        (not @(rf/subscribe [::data/pt-night_club]))])}]
                     [:span] " Night Club"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-park])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :park
                                                        (not @(rf/subscribe [::data/pt-park]))])}]
                     [:span] " Park"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-pet_store])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :pet_store
                                                        (not @(rf/subscribe [::data/pt-pet_store]))])}]
                     [:span] " Pet Store"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-police])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :police
                                                        (not @(rf/subscribe [::data/pt-police]))])}]
                     [:span] " Police"]]]]
                 ;; line 8
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-post_office])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :post_office
                                                        (not @(rf/subscribe [::data/pt-post_office]))])}]
                     [:span] " Post Office"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-restaurant])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :restaurant
                                                        (not @(rf/subscribe [::data/pt-restaurant]))])}]
                     [:span] " Restaurant"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-school])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :school
                                                        (not @(rf/subscribe [::data/pt-school]))])}]
                     [:span] " School"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-shopping_mall])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :shopping_mall
                                                        (not @(rf/subscribe [::data/pt-shopping_mall]))])}]
                     [:span] " Shopping Mall"]]]]
                 ;; line 9
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-spa])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :spa
                                                        (not @(rf/subscribe [::data/pt-spa]))])}]
                     [:span] " Spa"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-stadium])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :stadium
                                                        (not @(rf/subscribe [::data/pt-stadium]))])}]
                     [:span] " Stadium"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-subway_station])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :subway_station
                                                        (not @(rf/subscribe [::data/pt-subway_station]))])}]
                     [:span] " Subway Station"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-taxi_stand])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :taxi_stand
                                                        (not @(rf/subscribe [::data/pt-taxi_stand]))])}]
                     [:span] " Taxi Stand"]]]]
                 ;; line 10
                 [:div.columns
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-train_station])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :train_station
                                                        (not @(rf/subscribe [::data/pt-train_station]))])}]
                     [:span] " Train Station"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type      "checkbox"
                              :checked   @(rf/subscribe [::data/pt-veterinary_care])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :veterinary_care
                                                        (not @(rf/subscribe [::data/pt-veterinary_care]))])}]
                     [:span] " Veterinary Care"]]]

                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"
                              :checked   @(rf/subscribe [::data/pt-zoo])
                              :on-change #(rf/dispatch [::events/change-place-types
                                                        :zoo
                                                        (not @(rf/subscribe [::data/pt-zoo]))])}]
                     [:span] " Zoo"]]]
                  [:div.column
                   ;[:div.control
                   ; [:label.checkbox
                   ;  [:input {:type "checkbox"
                   ;           :checked   @(rf/subscribe [::data/pt-library])
                   ;           :on-change #(rf/dispatch [::events/change-place-types
                   ;                                     :library
                   ;                                     (not @(rf/subscribe [::data/pt-library]))])}]
                   ;  [:span] " ATM"]]
                   ]]]

                [:h3.is-danger "In-active Parameters"]
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
                [:label.label "Crime Rate"]
                [:div.notification.is-danger
                 [:button.delete]
                 [:div.columns
                  [:div.column
                   [:div.select.is-rounded
                    [:select [:option "choose"]
                     ;[:option "High"] [:option "Medium"] [:option "Low"]
                     ]]]]
                 ]

                ;;Sun direction
                [:label.label "Sun Direction (Home facing)"]
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
                     [:span] " West"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"}]
                     [:span] " South"]]]
                  [:div.column
                   [:div.control
                    [:label.checkbox
                     [:input {:type "checkbox"}]
                     [:span] " North"]]]]]]]]]]]])
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
           [:div.columns
            [:div.column
             [:section.hero
              [:div.hero-body
               [:div.container
                [:div#app.row.columns.is-multiline
                 [:div.column.is-5 {:v-for "card in cardData" ::key "card.id"}
                  [:div.card.large
                   [:div.card-image
                    [:figure.image.is-16by9
                     [:img {:src "card.image" :alt "Image"}]]]
                   [:div.card-content
                    [:div.media
                     [:div.media-content
                      [:p.title.is-5.has-text-weight-bold "{{card.user.price}}"]
                      [:p.subtitle.is-6.is-inline.is-centered [:span.mdi.mdi-bed-queen] "{{card.user.beds}} |"]
                      [:p.subtitle.is-6.is-inline.centered-content [:span.mdi.mdi-shower] "{{card.user.baths}}  |"]
                      [:p.subtitle.is-6.is-inline.centered-content [:span.mdi.mdi-ruler-square] "{{card.user.dimensions}}"]
                      [:p.subtitle.is-6 "{{card.user.address}}"]]]]]]]]]]]
            [:div.column
             [:section.hero
              [:div.hero-body
               [:div.container
                [:div.card.large
                 [:div.media
                  [:div.media-content
                   [:div#googleMap
                    [:script "function myMap() {
                                                 var mapProp= {
                                                     center:new google.maps.LatLng(32.5252,-93.7502),
                                                     zoom:12,
                                                 };
                                                 var map = new google.maps.Map(document.getElementById(\"googleMap\"),mapProp);
                                             }"]
                    [:script {:src "https://maps.googleapis.com/maps/api/js?key=AIzaSyCo9newQ_qNyc1HUK_LcpOtaQlUXTjc2co&callback=myMap"}]]]]]]]]]]
           ])]])))
