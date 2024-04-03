(ns com.pringwa.optiliv.page.agents
  (:require
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.fragment.header :refer [header]]
    [com.pringwa.optiliv.fragment.unauthorized :refer [unauthorized]]
    [com.pringwa.optiliv.layout :as layout]
    [clojure.string :as str]
    [re-frame.core :as rf]))

(defn agents-page []
  (let [current-user-type @(rf/subscribe [::data/current-user-type])
        current-user-name (rf/subscribe [::data/current-user-name])
        current-user-name-coll (str/split (or @current-user-name "My Account") #" ")]
    (if-not (or (= "Agents" current-user-type)
                (= "Optiliv" current-user-type))
      [unauthorized]
      [layout/with-verified-email-check
       [layout/standard "agents-page"
        [header (first current-user-name-coll) (last current-user-name-coll) false]
        [:style ".hero-body{
            padding:5px;
        }
        .columns{
            height: 100%;
        }
        .input.input{
            width: 260px;
            margin-left: 10px;
        }
        .control{
            margin: 20px 2px 10px 2px;
        }
        .custom-modal-content {
            margin-top: 40px;
        }
        .resetBtn{
            margin-top: 100px;
        }
        #googleMap{
            height:70vh;
            width: 120%;
            padding: 10px;
            justify-content: right;
            float:right;
        }"]
        [:section.hero
         [:div.hero-body
          [:div.container.is-fluid
           [:nav.navbar.is-transparent.has-shadow.is-fluid {:role "navigation" :aria-label "main navigation"}
            [:div.navbar-brand
             [:a.navbar-item.logo {:href "https://"}
              ;[:img.py-2.px-2 {:src "/img/logo.png" :alt "site logo" :style "max-height: 80px"}]
              ]
             [:a.navbar-burger.burger {:role "button" :aria-label "menu" :aria-expanded "false" :data-target "navbarMenu"}
              [:span {:aria-hidden "true"}]
              [:span {:aria-hidden "true"}]
              [:span {:aria-hidden "true"}]]]
            ]]]]
        [:section.hero
         [:div.hero-body
          [:div.filter-bar
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
                [:select
                 [:option "For Sale"]
                 [:option "For Rent"]
                 [:option "Sold"]]]]]]
            [:div.custom-fields
             [:div.field
              [:div.control
               [:div.select.is-rounded
                [:select
                 [:option "Beds"]
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
                 [:option {:value "300,000"} "$300,000"]
                 [:option {:value "350,000"} "$350,000"]
                 [:option {:value "400,000"} "$400,000"]
                 [:option {:value "450,000"} "$450,000"]
                 [:option {:value "500,000"} "$500,000"]
                 [:option {:value "550,000"} "$550,000"]
                 [:option {:value "600,000"} "$600,000"]
                 [:option {:value "650,000"} "$650,000"]
                 [:option {:value "700,000"} "$700,000"]
                 [:option {:value "750,000"} "$750,000"]
                 [:option {:value "800,000"} "$800,000"]
                 [:option {:value "850,000"} "$850,000"]
                 [:option {:value "900,000"} "$900,000"]
                 [:option {:value "950,000"} "$950,000"]
                 [:option {:value "1,000,000"} "$1M"]
                 [:option {:value "1,250,000"} "$1.25M"]
                 [:option {:value "1,500,000"} "$1.50M"]
                 [:option {:value "1,750,000"} "$1.75M"]
                 [:option {:value "2,000,000"} "$2M"]]]]]]
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
               [:button.button.is-warning.is-rounded
                ;{:onclick "toggleFilters()"}
                "All Filters"
                ]]]]]
           [:div#filters-modal.modal
            [:div.modal-background
             [:div.modal-content.custom-modal-content
              [:div.box
               [:h3.title.is-4.has-text-centered "All Filters"]
               [:hr.navbar-divider]
               [:div.columns
                [:div.column.is-6
                 [:h6.title.is-6 "Price"]
                 [:div.select
                  [:select
                   [:option {:value "-1"} "No Min"]
                   [:option {:value "50,000"} "$50,000"]
                   [:option {:value "100,000"} "$100,000"]
                   [:option {:value "150,000"} "$150,000"]
                   [:option {:value "200,000"} "$200,000"]
                   [:option {:value "250,000"} "$250,000"]
                   [:option {:value "300,000"} "$300,000"]
                   [:option {:value "350,000"} "$350,000"]
                   [:option {:value "400,000"} "$400,000"]
                   [:option {:value "450,000"} "$450,000"]
                   [:option {:value "500,000"} "$500,000"]
                   [:option {:value "550,000"} "$550,000"]
                   [:option {:value "600,000"} "$600,000"]
                   [:option {:value "650,000"} "$650,000"]
                   [:option {:value "700,000"} "$700,000"]
                   [:option {:value "750,000"} "$750,000"]
                   [:option {:value "800,000"} "$800,000"]
                   [:option {:value "850,000"} "$850,000"]
                   [:option {:value "900,000"} "$900,000"]
                   [:option {:value "950,000"} "$950,000"]
                   [:option {:value "1,000,000"} "$1M"]
                   [:option {:value "1,250,000"} "$1.25M"]
                   [:option {:value "1,500,000"} "$1.50M"]
                   [:option {:value "1,750,000"} "$1.75M"]
                   [:option {:value "2,000,000"} "$2M"]]]
                 [:div.select
                  [:select
                   [:option {:value "-1"} "No Max"]
                   [:option {:value "50,000"} "$50,000"]
                   [:option {:value "100,000"} "$100,000"]
                   [:option {:value "150,000"} "$150,000"]
                   [:option {:value "200,000"} "$200,000"]
                   [:option {:value "250,000"} "$250,000"]
                   [:option {:value "300,000"} "$300,000"]
                   [:option {:value "350,000"} "$350,000"]
                   [:option {:value "400,000"} "$400,000"]
                   [:option {:value "450,000"} "$450,000"]
                   [:option {:value "500,000"} "$500,000"]
                   [:option {:value "550,000"} "$550,000"]
                   [:option {:value "600,000"} "$600,000"]
                   [:option {:value "650,000"} "$650,000"]
                   [:option {:value "700,000"} "$700,000"]
                   [:option {:value "750,000"} "$750,000"]
                   [:option {:value "800,000"} "$800,000"]
                   [:option {:value "850,000"} "$850,000"]
                   [:option {:value "900,000"} "$900,000"]
                   [:option {:value "950,000"} "$950,000"]
                   [:option {:value "1,000,000"} "$1M"]
                   [:option {:value "1,250,000"} "$1.25M"]
                   [:option {:value "1,500,000"} "$1.50M"]
                   [:option {:value "1,750,000"} "$1.75M"]
                   [:option {:value "2,000,000"} "$2M"]]] [:br] [:br]
                 [:h6.title.is-6 "Square Feet"]
                 [:div.select
                  [:select
                   [:option {:value "-1"} "No Max"]
                   [:option {:value "500"} "500 sqft"]
                   [:option {:value "750"} "750 sqft"]
                   [:option {:value "1,000"} "1,000 sqft"]
                   [:option {:value "1,500"} "1,500 sqft"]
                   [:option {:value "2,000"} "2,000 sqft"]
                   [:option {:value "2,500"} "2,500 sqft"]
                   [:option {:value "3,000"} "3,000 sqft"]
                   [:option {:value "3,500"} "3,500 sqft"]
                   [:option {:value "4,000"} "4,000 sqft"]
                   [:option {:value "4,500"} "4,500 sqft"]
                   [:option {:value "5,000"} "5,000 sqft"]
                   [:option {:value "5,500"} "5,500 sqft"]
                   [:option {:value "6,000"} "6,000 sqft"]
                   [:option {:value "6,500"} "6,500 sqft"]
                   [:option {:value "7,000"} "7,000 sqft"]
                   [:option {:value "7,500"} "7,500 sqft"]
                   [:option {:value "8,000"} "8,000 sqft"]
                   [:option {:value "8,500"} "8,500 sqft"]
                   [:option {:value "9,000"} "9,000 sqft"]
                   [:option {:value "9,500"} "9,500 sqft"]
                   [:option {:value "10,000"} "10,000 sqft"]]]
                 [:div.select
                  [:select
                   [:option {:value "-1"} "No Max"]
                   [:option {:value "500"} "500 sqft"]
                   [:option {:value "750"} "750 sqft"]
                   [:option {:value "1,000"} "1,000 sqft"]
                   [:option {:value "1,500"} "1,500 sqft"]
                   [:option {:value "2,000"} "2,000 sqft"]
                   [:option {:value "2,500"} "2,500 sqft"]
                   [:option {:value "3,000"} "3,000 sqft"]
                   [:option {:value "3,500"} "3,500 sqft"]
                   [:option {:value "4,000"} "4,000 sqft"]
                   [:option {:value "4,500"} "4,500 sqft"]
                   [:option {:value "5,000"} "5,000 sqft"]
                   [:option {:value "5,500"} "5,500 sqft"]
                   [:option {:value "6,000"} "6,000 sqft"]
                   [:option {:value "6,500"} "6,500 sqft"]
                   [:option {:value "7,000"} "7,000 sqft"]
                   [:option {:value "7,500"} "7,500 sqft"]
                   [:option {:value "8,000"} "8,000 sqft"]
                   [:option {:value "8,500"} "8,500 sqft"]
                   [:option {:value "9,000"} "9,000 sqft"]
                   [:option {:value "9,500"} "9,500 sqft"]
                   [:option {:value "10,000"} "10,000 sqft"]]] [:br] [:br]
                 [:h6.title.is-6 "Year Built"]
                 [:div.select
                  [:select
                   [:option {:value "-1"} "Any"]
                   [:option {:value "-1"} "Any"]
                   [:option {:value "1900"} "1900"]
                   [:option {:value "1920"} "1920"]
                   [:option {:value "1950"} "1950"]
                   [:option {:value "1960"} "1960"]
                   [:option {:value "1970"} "1970"]
                   [:option {:value "1980"} "1980"]
                   [:option {:value "1990"} "1990"]
                   [:option {:value "2000"} "2000"]
                   [:option {:value "2005"} "2005"]
                   [:option {:value "2010"} "2010"]
                   [:option {:value "2015"} "2015"]
                   [:option {:value "2016"} "2016"]
                   [:option {:value "2017"} "2017"]
                   [:option {:value "2018"} "2018"]
                   [:option {:value "2019"} "2019"]
                   [:option {:value "2020"} "2020"]
                   [:option {:value "2021"} "2021"]
                   [:option {:value "2022"} "2022"]
                   [:option {:value "2023"} "2023"]]]
                 [:div.select
                  [:select
                   [:option {:value "-1"} "Any"]
                   [:option {:value "1900"} "1900"]
                   [:option {:value "1920"} "1920"]
                   [:option {:value "1950"} "1950"]
                   [:option {:value "1960"} "1960"]
                   [:option {:value "1970"} "1970"]
                   [:option {:value "1980"} "1980"]
                   [:option {:value "1990"} "1990"]
                   [:option {:value "2000"} "2000"]
                   [:option {:value "2005"} "2005"]
                   [:option {:value "2010"} "2010"]
                   [:option {:value "2015"} "2015"]
                   [:option {:value "2016"} "2016"]
                   [:option {:value "2017"} "2017"]
                   [:option {:value "2018"} "2018"]
                   [:option {:value "2019"} "2019"]
                   [:option {:value "2020"} "2020"]
                   [:option {:value "2021"} "2021"]
                   [:option {:value "2022"} "2022"]
                   [:option {:value "2023"} "2023"]]] [:br] [:br]
                 [:h6.title.is-6 "Basement"]
                 [:label.checkbox [:input#basement {:type "checkbox"}] "Has Basement"] [:br] [:br]
                 [:h6.title.is-6 "Amenities"]
                 [:label.checkbox [:input#swimmingPool {:type "checkbox"}] "Swimming Pool"] [:br]
                 [:label.checkbox [:input#parking {:type "checkbox"}] "Parking"] [:br]
                 [:label.checkbox [:input#laundry {:type "checkbox"}] "Laundry"] [:br]
                 [:label.checkbox [:input#petAmenities {:type "checkbox"}] "Pet Amenities"] [:br]
                 [:label.checkbox [:input#outdoorSpace {:type "checkbox"}] "Outdoor Space"] [:br]
                 [:label.checkbox [:input#furnished {:type "checkbox"}] "Furnished"] [:br]
                 [:label.checkbox [:input#centralAC {:type "checkbox"}] "Central A/C"] [:br]
                 [:label.checkbox [:input#centralHeat {:type "checkbox"}] "Central Heat"] [:br] [:br]
                 [:div.control
                  [:button.button.is-link.is-warning "Apply"]]]
                [:div.column.is-6
                 [:h6.title.is-6 "Beds"]
                 [:div.buttons
                  [:button.button "Any"]
                  [:button.button "1"]
                  [:button.button "2"]
                  [:button.button "3"]
                  [:button.button "4"]
                  [:button.button "5+"]]
                 [:h6.title.is-6 "Baths"]
                 [:div.buttons
                  [:button.button "Any"]
                  [:button.button "1"]
                  [:button.button "2"]
                  [:button.button "3"]
                  [:button.button "4"]
                  [:button.button "5+"]]
                 [:h6.title.is-6 "Hobbies/Interests"]
                 [:label.checkbox [:input#restaurants {:type "checkbox"}] "Restaurants"] [:br]
                 [:label.checkbox [:input#gym {:type "checkbox"}] "Gym"] [:br]
                 [:label.checkbox [:input#school {:type "checkbox"}] "School"] [:br]
                 [:label.checkbox [:input#clubs {:type "checkbox"}] "Clubs"] [:br]
                 [:label.checkbox [:input#stadiums {:type "checkbox"}] "Stadiums"] [:br]
                 [:label.checkbox [:input#parks {:type "checkbox"}] "Parks"] [:br]
                 [:label.checkbox [:input#hospital {:type "checkbox"}] "Hospitals"] [:br]
                 [:label.checkbox [:input#publicTranspo {:type "checkbox"}] "Public Transportation"] [:br] [:br]
                 [:div.control
                  [:button.button.is-pulled-right.resetBtn
                   ;{:onclick ""}
                   "Reset Filters"]]]]]]
             [:button.modal-close.is-large
              {:aria-label "close"
               ;:onclick    "toggleFilters()"
               }]]]]]]
        [:div.columns
         [:div.column
          [:section.hero
           [:div.hero-body
            [:div.container
             [:div#app.row.columns.is-multiline
              [:div.column.is-5
               ;{:v-for "card in cardData" ::key "card.id"}
               [:div.card.large
                [:div.card-image
                 [:figure.image.is-16by9
                  ;[:img {::src "card.image" :alt "Image"}]
                  ]]
                [:div.card-content
                 [:div.media
                  [:div.media-content
                   [:p.title.is-5.has-text-weight-bold "price"]
                   [:p.subtitle.is-6.is-inline.is-centered [:span.mdi.mdi-bed-queen] "bed |"]
                   [:p.subtitle.is-6.is-inline.centered-content [:span.mdi.mdi-shower] "bath  |"]
                   [:p.subtitle.is-6.is-inline.centered-content [:span.mdi.mdi-ruler-square] "dimensions"]
                   [:p.subtitle.is-6 "address"]]]]]]]]]]]
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
        [:script {:src "cardsData.js"}]
        ;[:script "var app = new Vue({
        ;    el: '#app',
        ;    data: {
        ;        cardData: cardsData}
        ;})
        ;
        ;function toggleFilters() {
        ;    const modal = document.getElementById('filters-modal');
        ;    modal.classList.toggle('is-active');
        ;}"]
        ;[:section.hero.is-hero-bar.is-main-hero
        ; [:div.hero-body
        ;  [:div.level
        ;   [:div.level-left
        ;    [:div.level-item.is-hero-content-item
        ;     [:div [:h1.title.is-spaced [:b "Welcome Agent!"]]]]]]]]
        ;[:section.section.is-main-section
        ; [:div.container
        ;  [:div.columns.is-centered
        ;   [:div.column.is-two-fifths
        ;    [:div.card.has-card-header-background
        ;     [:header.card-header.has-background-primary
        ;      [:p.card-header-title
        ;       [:span.icon [:i.mdi.mdi-account-plus]]
        ;       ""]]
        ;     [:div.card-content
        ;      ]]]]]]
        ]])))
