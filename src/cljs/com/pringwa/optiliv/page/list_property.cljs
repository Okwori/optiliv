(ns com.pringwa.optiliv.page.list-property
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
    [re-frame.core :as rf]))

(defn list-property-page []
  (let [current-user-type @(rf/subscribe [::data/current-user-type])
        current-user-name (rf/subscribe [::data/current-user-name])
        current-user-name-coll (str/split (or @current-user-name "My Account") #" ")]
    (if-not (= "Optiliv" current-user-type)
      [unauthorized]
      [layout/with-verified-email-check
       [layout/standard "list-property-page"
        [header (first current-user-name-coll) (last current-user-name-coll) false]
        [:section.hero.is-hero-bar.is-main-hero
         [:div.hero-body
          [:div.level
           [:div.level-left
            [:div.level-item.is-hero-content-item
             [:div [:h1.title.is-spaced [:b "List a Property"]]]]]]]]
        [:section.section
         [:div.container
          [:form {:action "submit_listing.php" :method "post" :enctype "multipart/form-data"}
           [:div.field
            [:label.label {:for "address"} "Property Address:"]
            [:div.control
             [:input#address.input {:type "text" :name "address" :required "true"}]]]
           [:div.field
            [:label.label {:for "price"} "Listing Price:"]
            [:div.control
             [:input#price.input {:type "text" :name "price" :required "true"}]]]
           [:div.field
            [:label.label {:for "property_type"} "Property Type:"]
            [:div.control
             [:div.select
              [:select#property_type {:name "property_type"}
               [:option {:value "single_family"} "Single Family Home"]
               [:option {:value "condo"} "Condo"]
               [:option {:value "townhouse"} "Townhouse"]
               [:option {:value "multi_family"} "Multi-family"]]]]]
           [:div.field
            [:label.label {:for "bedrooms"} "Number of Bedrooms:"]
            [:div.control
             [:input#bedrooms.input {:type "number" :name "bedrooms" :required "true"}]]]
           [:div.field
            [:label.label {:for "bathrooms"} "Number of Bathrooms:"]
            [:div.control
             [:input#bathrooms.input {:type "number" :name "bathrooms" :required "true"}]]]
           [:div.field
            [:label.label {:for "square_footage"} "Square Footage:"]
            [:div.control
             [:input#square_footage.input {:type "number" :name "square_footage" :required "true"}]]]
           [:div.field
            [:label.label {:for "lot_size"} "Lot Size (if applicable):"]
            [:div.control
             [:input#lot_size.input {:type "text" :name "lot_size"}]]]
           [:div.field
            [:label.label {:for "year_built"} "Year Built:"]
            [:div.control
             [:input#year_built.input {:type "number" :name "year_built" :required "true"}]]]
           [:div.field
            [:label.label {:for "description"} "Description of Property:"]
            [:div.control
             [:textarea#description.textarea {:name "description" :rows "4" :cols "50" :required "true"}]]]
           [:div.field
            [:label.label {:for "contact_name"} "Agent/Owner Name:"]
            [:div.control
             [:input#contact_name.input {:type "text" :name "contact_name" :required "true"}]]]
           [:div.field
            [:label.label {:for "agency"} "Agency (if applicable):"]
            [:div.control
             [:input#agency.input {:type "text" :name "agency"}]]]
           [:div.field
            [:label.label {:for "phone"} "Phone Number:"]
            [:div.control
             [:input#phone.input {:type "tel" :name "phone" :required "true"}]]]
           [:div.field
            [:label.label {:for "email"} "Email Address:"]
            [:div.control
             [:input#email.input {:type "email" :name "email" :required "true"}]]]
           [:div.field
            [:label.label "Preferred Method of Contact:"]
            [:div.control
             [:label.radio
              [:input {:type "radio" :name "contact_method" :value "phone" :checked "true"}] "Phone"]
             [:label.radio
              [:input {:type "radio" :name "contact_method" :value "email"}] "Email"]
             [:label.radio
              [:input {:type "radio" :name "contact_method" :value "text"}] "Text"]]]
           [:div.field
            [:label.label {:for "additional_comments"} "Additional Comments or Information:"]
            [:div.control
             [:textarea#additional_comments.textarea {:name "additional_comments" :rows "4" :cols "50"}]]]
           [:div.field
            [:label.label {:for "photos"} "Upload Photos:"]
            [:div.control
             [:input#photos.input {:type "file" :name "photos[]" :multiple "true" :accept "image/*"}]]]
           [:div.field
            [:div.control
             [:input.button.is-primary {:type "submit" :value "Submit"}]]]]]]]])))


