(ns com.pringwa.optiliv.fragment.header
  (:require
    [com.pringwa.optiliv.data :as data]
    [kee-frame.core :as k]
    [reagent.core :as r]
    [re-frame.core :as rf]))

;;;; TODO fix!
(k/reg-event-db ::expand-mobile-menu
                [(rf/path :page :page-type/adjudicator)]
                (fn [_ _] {:show-mobile-menu? true}))

(k/reg-event-db ::collapse-mobile-menu
                [(rf/path :page :page-type/adjudicator)]
                (fn [_ _] {:show-mobile-menu? false}))


(defn nav-link
  [class path text icon-class]
  (let [href (cond (keyword? path) (k/path-for [path])
                   (string? path) path
                   :else "#")
        show-mobile-menu? (rf/subscribe [::data/show-mobile-menu?])]
    [:a
     {:href     href
      :class    class
      :on-click (if show-mobile-menu?
                  (do
                    #(rf/dispatch [:navigate-to path])
                    #(rf/dispatch [::collapse-mobile-menu]))
                  #(rf/dispatch [:navigate-to path]))}
     [:span.icon [:i {:class icon-class}]]
     [:span text]]))

(defn- nav-link-icon
  [class path span-class icon-class]
  (let [href (if path (k/path-for [path]) "#")
        show-mobile-menu? (rf/subscribe [::data/show-mobile-menu?])]
    [:<>
     [:a
      {:href     href
       :class    class
       :on-click (if show-mobile-menu?
                   (do
                     #(rf/dispatch [:navigate-to path])
                     #(rf/dispatch [::collapse-mobile-menu]))
                   #(rf/dispatch [:navigate-to path]))}
      [:span.icon {:class span-class} [:i {:class icon-class}]]]]))

(defn- account-submenu [fname lname]
  [:div.navbar-item.has-dropdown.has-dropdown-with-icons.has-divider.has-user-avatar.is-hoverable
   [:a.navbar-link.is-arrowless
    [:div.is-user-avatar
     [:img {:src (str "https://api.dicebear.com/7.x/initials/svg?seed=" (first fname) (first lname))
            :alt (str fname " " lname)}]]
    [:div.is-user-name [:span (str fname " " lname)]]
    [:span.icon [:i.mdi.mdi-chevron-down]]]
   [:div.navbar-dropdown
    [nav-link "navbar-item" :change-email "Change Email" "mdi mdi-email-edit-outline"]
    [nav-link "navbar-item" :change-password "Change Password" "mdi mdi-lock-reset"]
    [:hr.navbar-divider]
    [nav-link "navbar-item" :logout "Log out" "mdi mdi-logout"]]])

(defn header [fname lname burger?]
  (r/with-let
    [type (rf/subscribe [::data/current-user-type])]
    (let [show-mobile-menu? (rf/subscribe [::data/show-mobile-menu?])]
      [:nav.navbar.is-fixed-top
       [:div.navbar-brand
        (when burger?
          [:a {:class       "navbar-item is-desktop-icon-only is-hidden-touch"
               :data-target "aside-main" :title "Expand"}
           [:span.icon [:i.mdi.mdi-backburger.mdi-24px]]]
          [:a {:class "navbar-item is-hidden-desktop"}
           [:span.icon [:i.mdi.mdi-backburger.mdi-24px]]])
        [:a.navbar-item {:on-click #(rf/dispatch [:navigate-to-authenticated-home])}
         [:img {:src "img/logo.png"
                :alt "OptiLiv"}]]]
       [:div.navbar-brand.is-right
        [nav-link-icon "navbar-item is-hidden-desktop" :report nil "mdi mdi-bell"]
        [:a.navbar-item.is-hidden-desktop {:data-target "navbar-menu"}
         [:span.icon {:on-click (if @show-mobile-menu? #(rf/dispatch [::collapse-mobile-menu])
                                                       #(rf/dispatch [::expand-mobile-menu]))}
          [:i {:class (str "mdi " (if @show-mobile-menu? "mdi-close" "mdi-dots-vertical"))}]]]]

       [:div#navbar-menu {:class (str "navbar-menu fadeIn animated faster " (when @show-mobile-menu? "is-active"))}
        [:div.navbar-end
         (when (= @type "Optiliv")
          [nav-link "navbar-item has-divider" :register "Register" "mdi mdi-account-plus-outline"])
         ;[nav-link "navbar-item has-divider" "/cfs" "Find Agents" "mdi mdi-account-plus-outline"]
         [nav-link "navbar-item has-divider" :list-property "List" "mdi mdi-home-plus"]
         ;[nav-link "navbar-item has-divider" :buy "Buy" "mdi mdi-cash-multiple"]
         ;[nav-link "navbar-item has-divider" :rent "Rent" "mdi mdi-cash"]
         [nav-link "navbar-item has-divider" :help "Help" "mdi mdi-help"]]
        [account-submenu fname lname]]])))
