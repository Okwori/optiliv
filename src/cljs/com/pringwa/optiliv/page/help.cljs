(ns com.pringwa.optiliv.page.help
  (:require
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.events :as events]
    [com.pringwa.optiliv.fragment.header :refer [header]]
    [com.pringwa.optiliv.fragment.tab :refer [tab]]
    [com.pringwa.optiliv.fragment.unauthorized :refer [unauthorized]]
    [com.pringwa.optiliv.layout :as layout]
    [clojure.string :as str]
    [reagent.core :as r]
    [re-frame.core :as rf]))

(defn help-page []
  (let [current-user-type @(rf/subscribe [::data/current-user-type])
        current-user-name (rf/subscribe [::data/current-user-name])
        current-user-name-coll (str/split (or @current-user-name "My Account") #" ")
        active-tab (rf/subscribe [::data/tab-select-active-tab :page-type/help])
        active-status-text (or (when @active-tab
                                 (str/replace (name @active-tab) "-" " ")) "Review")]
    (if-not (or (= "Optiliv" current-user-type)
                (= "Agents" current-user-type)
                (= "Customers" current-user-type))
      [unauthorized]
      [layout/with-verified-email-check
       [layout/standard "help-page"
        [header (first current-user-name-coll) (last current-user-name-coll) false]
        [:section.hero.is-hero-bar.is-main-hero
         [:div.hero-body
          [:div.level
           [:div.level-left
            [:div.level-item.is-hero-content-item
             [:div [:h1.title.is-spaced [:b "Help"]]]]]]]]
        [:section.section.is-main-section
         [tab {:tab-class "is-boxed is-centered"
               :page-type :page-type/help
               :tabs      {:Privacy-Policy       {:label "Privacy Policy"}
                           :Terms-And-Conditions {:label "Terms and Conditions"}
                           :Review               {:label "Customer Reviews"}
                           :FAQ                  {:label "FAQ"}}}]
         [:div#tab-content
          (when (= @active-tab :Privacy-Policy)
           [:div#privacy-policy
            [:div.card.has-table.has-mobile-sort-spaced
             [:header.card-header
              [:p.card-header-title.subtitle.is-4.has-text-grey active-status-text]]
             [:div.card-content
              [:<>
               [:p "Last Revised: March 3, 2024"]
               [:p "OptiLiv Real-Estate Marketplace doing business as OptiLiv is committed to protecting the privacy and accuracy of confidential information to the extent possible, subject to provisions of state and federal law. Other than as required by laws that guarantee public access to certain types of information, or in response to subpoenas or other legal instruments that authorize access, personal information is not actively shared. In particular, we do not re-distribute or sell personal information collected on our web servers. OptiLiv values the privacy of our users. This Online Privacy Policy Agreement (“Privacy Policy”) will help you understand how we collect and use personal information from those who visit our website or make use of our online facilities and services. This policy has been designed to ensure our users of our commitment and obligation with handling our user’s personal data."]
               [:h2 "Table of Contents"]
               [:ol
                [:li [:a {:href "#information-collected"} "Information Collected"]]
                [:li [:a {:href "#use-of-information"} "Use of Information"]]
                [:li [:a {:href "#sharing-of-information"} "Sharing of Information"]]
                [:li [:a {:href "#data-from-minors"} "Data from Minors"]]
                [:li [:a {:href "#user-rights"} "User Rights"]]
                [:li [:a {:href "#revision-policy"} "Revision Policy"]]
                [:li [:a {:href "#external-sites"} "External/Third-Party Sites"]]
                [:li [:a {:href "#acceptance-of-terms"} "Acceptance of Terms"]]
                [:li [:a {:href "#contact-us"} "Contact Us"]]]
               [:section#information-collected
                [:h2 "1. Information Collected"] "We collect personal and non-personal data from you when you use our services. Personal data that we collect includes identifiers such as your
    name, email address, IP address or device identifier, or essentially any data that can be linked back to you. This information will be something
    that you provide us with using our registration form in order for us to collect. Non-personal data is data that cannot be reasonably linked back to you.
    The usage of non-personal data allows us to gather information to create analytics about our service and make reports based on trends seen within our users."
                [:br] [:br]
                [:b "OptiLiv servers collect the following analytics:"]
                [:br] "Internet Protocol (IP) address"
                [:br] "Device being used"
                [:br] "web pages requested"
                [:br] "referring web page"
                [:br] "browser used"
                [:br] "date and time"
                [:br] "Internet service provider"
                [:br]
                [:br] "OptiLiv will have other means of collecting personal and non-personal information from you as the user such as the use of cookies,
    webforms, surveys, or registration forms. In order to conduct the collection of non-personal data, OptiLiv will be using a third-party
    service that will track and monitor responses and trends that you as the user will engage by doing so by using our services. This third-party
    application has services that are governed by the privacy policies of the providers and are not within OptiLiv Real–Estate Marketplace’s control." [:br]
                [:br]
                [:b "Personal Information Provided by You."]
                [:br] "The personal information that we collect from you as the user depends on what information you enter through your registration process.
    The personal information we collect may include the following:"
                [:br] "Names"
                [:br] "Email addresses"
                [:br] "Usernames"
                [:br] "Passwords"
                [:br] "Contact preferences"
                [:br] "Billing Addresses"
                [:br] "debit/credit card numbers" [:br]]
               [:section#use-of-information
                [:h2 "2. Use of Information"]]
               [:section#sharing-of-information
                [:h2 "3. Sharing of Information"]]
               [:section#data-from-minors
                [:h2 "4. Data from Minors"]]
               [:section#user-rights
                [:h2 "5. User Rights"]]
               [:section#revision-policy
                [:h2 "6. Revision Policy"]]
               [:section#external-sites
                [:h2 "7. External/Third-Party Sites"]]
               [:section#acceptance-of-terms
                [:h2 "8. Acceptance of Terms"]]
               [:section#contact-us
                [:h2 "9. Contact Us"]
                [:p "If you have any questions or concerns regarding the Online Privacy Policy Agreement related to our website, please feel free to contact us at the following email address:"]
                [:p "Email:" [:a {:href "mailto:pringwa.inc@gmail.com"} "pringwa.inc@gmail.com"]]
                [:p "OptiLiv Real-Estate Marketplace" [:br] "One University Pl, Shreveport, Louisiana, 71115"]]]]]])
          (when (= @active-tab :Terms-And-Conditions)
            [:h3 "TODO!"])
          (when (= @active-tab :Review)
            [:h3 "TODO!"])
          (when (= @active-tab :FAQ)
            [:h3 "TODO!"])]]]])))
