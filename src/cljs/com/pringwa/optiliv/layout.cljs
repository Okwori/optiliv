(ns com.pringwa.optiliv.layout
  (:require
    [com.pringwa.optiliv.data :as data]
    [com.pringwa.optiliv.page.unverified-email
     :refer [unverified-email-page-inner]]
    [reagent.core :as r]
    [re-frame.core :as rf]))

(defn standard
  [page-id & children]
  [:div {:id page-id}
   (into [:<>] children)])

(defn modal
  ([identifier child] (modal identifier child nil))
  ([identifier child last-child]
   (let [id #(hash-map :id (str identifier "-" %))]
     [:div (id "page")
      [:section.section.is-main-section
       (conj [:div.container (id "modal")] child)]
      last-child])))

(defn- unverified-page []
  [modal "unverified-email"
   [unverified-email-page-inner]])

(defn with-verified-email-check [verified-email-content]
  (r/with-let
    [user-state (rf/subscribe [::data/current-user-state])]
    (if (= "email-unverified" @user-state)
      [unverified-page]
      verified-email-content)))
