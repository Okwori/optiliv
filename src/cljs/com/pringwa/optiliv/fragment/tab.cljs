(ns com.pringwa.optiliv.fragment.tab
  (:require [com.pringwa.optiliv.data :as data]
            [com.pringwa.optiliv.events :as events]
            [re-frame.core :as rf]))

(defn- tabhead [{:keys [tabs page-type]} active-tab-id active?]
  (letfn [(attrs [id]
            {:on-click #(rf/dispatch [::events/switch-tab page-type id])
             :class (if (not= id active-tab-id) "" "is-active")
             :data-target (name id)})
          (label-markup [id label]
            (cond
              (not= id active-tab-id) [:a label]
              active? [:a [:b label]]))]
   [:ul
    (for [[id {:keys [label]}] tabs]
      ^{:key id} [:li (attrs id)
                  (label-markup id label)])]))

(defn tab [tab-opts & tab-contents]
  (let [{:keys [tabs page-type tab-class]} tab-opts
        active-tab @(rf/subscribe [::data/tab-select-active-tab page-type])
        active? @(rf/subscribe [::data/tab-select-active? page-type])
        active-tab-id (or active-tab (-> tabs first key))]
    [:div {:class (str "tabs " tab-class)}
     [tabhead tab-opts active-tab-id active?]]))
