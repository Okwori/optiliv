(ns com.pringwa.optiliv.xhr.send.put
  (:require [ajax.core :as ajax]
            [com.pringwa.optiliv.config :refer [api-url]]
            day8.re-frame.http-fx
            [kee-frame.core :as k]))

(k/reg-event-fx
  :change-password
  (fn [{:keys [db]} [form-data]]
    ;; stifle duplicate requests
    (when-not (get-in db [:xhr :change-password :in-flight?])
      {:http-xhrio {:method          :put
                    :uri             (api-url "/change-password")
                    :body            form-data
                    :format          :multipart
                    :response-format (ajax/text-response-format)
                    :on-success      [:change-password-success]
                    :on-failure      [:change-password-failure]}
       :db         (assoc-in db [:xhr :change-password] {:in-flight? true})})))
