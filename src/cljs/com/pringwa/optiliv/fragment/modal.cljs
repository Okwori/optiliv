(ns com.pringwa.optiliv.fragment.modal
  (:require [com.pringwa.optiliv.data :as data]
            [com.pringwa.optiliv.events :as events]
            [reagent.core :as r]
            [re-frame.core :as rf]))

(defn close [_e]
  (rf/dispatch [::events/set-modal-visibility false]))

(def ignore-clicks
  {:on-click (fn [e] (.stopPropagation e))})

(defn header [text]
  [:header.modal-card-head
   [:p.modal-card-title [:span.subtitle.has-text-primary text]]
   [:button.delete {:on-click close :aria-label "close"}]])

(defn modal [modal-type child-hiccup]
  (r/with-let [visible? (rf/subscribe [::data/modal-visible? modal-type])]
              (when @visible?
                [:div {:class (str "modal is-active")}
                 [:div.modal-background {:on-click close}]
                 [:div ignore-clicks child-hiccup]])))
