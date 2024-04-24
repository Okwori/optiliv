(ns com.pringwa.optiliv.page.list-property
  (:require
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.events :as events]
    [com.pringwa.optiliv.config :refer [api-url]]
    [com.pringwa.optiliv.fragment.header :refer [header]]
    [com.pringwa.optiliv.fragment.unauthorized :refer [unauthorized]]
    [com.pringwa.optiliv.layout :as layout]
    [clojure.string :as str]
    [reagent.core :as r]
    [re-frame.core :as rf]))

(defn- submit-form [event]
  (.preventDefault event)
  (let [form-el (.-target event)
        form-data (js/FormData. form-el)]
    (rf/dispatch [:list-property form-data])))

(defn- error-status [error-message]
  [:div.has-text-danger
   [:p "There was an error with the attempt to list a property. The error "
    "response from the server follows:"]
   [:p (interpose [:br] (str/split error-message #"\n"))]])

(defn- register-status []
  (let [create-xhr-status (rf/subscribe [::data/xhr :list-property])]
    (fn []
      (let [{:keys [in-flight? error success?]} @create-xhr-status]
        (cond
          in-flight? "⏳"
          error [error-status error]
          success?
          [:div.has-text-success
           "Property listed successfully"])))))

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
          [:form {:action    (api-url "/list-property")
                  :method    "post"
                  ;:enctype   "multipart/form-data"
                  :on-submit #(do
                                (.preventDefault %)
                                (rf/dispatch [:list-property (js/FormData. (.-target %))]))}
           [:div.columns
            [:div.column
             [:div.field
              [:label.label {:for "address"} "Name:"]
              [:div.control
               [:input.input {:type "text" :name "name" :required true}]]]
             [:div.field
              [:label.label {:for "price"} "Price:"]
              [:div.control
               [:input.input {:type "number" :name "price" :required true}]]]
             [:div.field
              [:label.label {:for "bathrooms"} "Number of Bathrooms:"]
              [:div.control
               [:input.input {:type "number" :name "bathrooms" :required false}]]]]
            [:div.column
             [:div.field
              [:label.label {:for "address"} "Address:"]
              [:div.control
               [:input#address.input {:type "text" :name "address" :required true}]]]
             [:div.field
              [:label.label {:for "square_footage"} "Area (Square Footage):"]
              [:div.control
               [:input.input {:type "number" :name "area" :required false}]]]
             [:div.field
              [:label.label {:for "photos"} "Upload Photos:"]
              [:div.control
               [:input.input {:type "file" :name "image_url" :multiple "true" :accept "image/*"}]]]]
            [:div.column
             [:div.field
              [:label.label {:for "Property Type"} "Property Type:"]
              [:div.control
               [:div.select
                [:select {:name "property_type_id"}
                 [:option {:value ""} "Select property type"]
                 [:option {:value 1} "Apartment"]
                 [:option {:value 2} "House"]
                 [:option {:value 3} "Shop"]
                 [:option {:value 4} "Office Space"]]]]]
             [:div.field
              [:label.label {:for "Structure"} "Structure"]
              [:div.control
               [:div.select
                [:select {:name "structure_id"}
                 [:option {:value ""} "Select property type"]
                 [:option {:value 1} "Studio"]
                 [:option {:value 2} "1 Bedroom"]
                 [:option {:value 3} "1.5 Bedroom"]
                 [:option {:value 4} "2 Bedrooms"]
                 [:option {:value 4} "2.5 Bedrooms"]
                 [:option {:value 4} "3 Bedrooms"]]]]]
             [:div.field
              [:label.label {:for "Furniture"} "Furniture"]
              [:div.control
               [:div.select
                [:select#property_type {:name "furniture_id"}
                 [:option {:value ""} "Furnished?"]
                 [:option {:value 1} "Furnished"]
                 [:option {:value 2} "Partly Furnished"]
                 [:option {:value 3} "Unfurnished"]]]]]]]
           [:div.columns
            [:div.column.is-full
             [:div.field
              [:label.label {:for "description"} "Description:"]
              [:div.control
               [:textarea.textarea {:name "description" :rows "4" :cols "50"}]]]
             [:div.field
              [:div.control
               [:input.button.is-primary {:type "submit" :value "Submit"}]]]]]]
          [register-status]]]]])))
