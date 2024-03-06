(ns com.pringwa.optiliv.xhr.success.del
  (:require [kee-frame.core :as k]))

(k/reg-event-fx
  :logout-success
  (fn [{:keys [db]} _]
    {:db          (assoc-in db [:xhr :logout]
                            {:in-flight? false, :error nil, :success? true})
     :navigate-to [:login]}))
