(ns com.pringwa.optiliv.fragment.select-user-groups
  (:require [com.pringwa.optiliv.data :as data]
            [re-frame.core :as rf]))

(defn user-group->option [group]
  (let [{:keys [id name]} group]
    [:option {:value id, :key id} name]))

(defn- top-option [groups]
  (let [xhr @(rf/subscribe [::data/xhr :user-groups])]
    [:option {:value ""}
     (cond
       (:in-flight? xhr) "(fetching user groups...)"
       (:error xhr) (str "(error fetching user groups: "
                         (:error xhr) ")")
       (empty? groups) "(you must add a user group )"
       :else "--- Select a user group ---")]))

(defn select-user-groups
  [selected-id]
  (let [groups @(rf/subscribe [::data/user-groups-vec])
        select-tag [:select {:name         "user-group-id"
                             :defaultValue selected-id
                             :required     true}
                    [top-option groups]]]
    [:div.select
     (into select-tag
           (map user-group->option groups))]))
