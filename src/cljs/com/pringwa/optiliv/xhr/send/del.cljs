(ns com.pringwa.optiliv.xhr.send.del
  (:require [ajax.core :as ajax]
            [com.pringwa.optiliv.config :refer [api-url]]
            day8.re-frame.http-fx
            [kee-frame.core :as k]))

(k/reg-event-fx :logout
                (fn [{:keys [db]} _]
                  {:http-xhrio {:method :delete
                                :uri (api-url "/logout")
                                :format          (ajax/text-request-format)
                                :response-format (ajax/text-response-format)
                                :on-success [:logout-success]
                                :on-failure [:logout-failure]}
                   :db (-> db
                           (assoc-in [:xhr :logout] {:in-flight? true})
                           (dissoc :current-user)
                           ;; prevent login page from redirecting home:
                           (update :xhr dissoc :login)
                           (update :xhr dissoc :current-user))}))

