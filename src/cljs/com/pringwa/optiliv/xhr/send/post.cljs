(ns com.pringwa.optiliv.xhr.send.post
  (:require [ajax.core :as ajax]
            [com.pringwa.optiliv.config :refer [api-url]]
            day8.re-frame.http-fx
            [kee-frame.core :as k]))

(defn ^:private post-config
  ([path form-data on-success on-failure]
   (post-config path form-data on-success on-failure nil))
  ([path form-data on-success on-failure overrides]
   (merge {:method          :post
           :uri             (api-url path)
           :body            form-data
           :format          :multipart
           :response-format (ajax/transit-response-format)
           :on-success      on-success
           :on-failure      on-failure}
          overrides)))

(k/reg-event-fx
  :login
  (fn [cofx [form-data]]
    {:http-xhrio (post-config "/login" form-data
                              [:login-success form-data]
                              [:login-failure])
     :db         (assoc-in (:db cofx) [:xhr :login] {:in-flight? true})}))

(k/reg-event-fx
  :change-email
  (fn [{:keys [db]} [form-data]]
    ;; stifle duplicate requests
    (when-not (get-in db [:xhr :change-email :in-flight?])
      {:http-xhrio (post-config "/change-email" form-data
                                [:change-email-success]
                                [:change-email-failure]
                                {:response-format (ajax/text-response-format)})
       :db         (assoc-in db [:xhr :change-email] {:in-flight? true})})))
