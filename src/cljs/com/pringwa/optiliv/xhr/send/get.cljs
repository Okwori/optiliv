(ns com.pringwa.optiliv.xhr.send.get
  (:require [ajax.core :as ajax]
            [com.pringwa.optiliv.config :refer [api-url]]
            [com.pringwa.optiliv.xhr.util :refer [make-event-name]]
            day8.re-frame.http-fx
            [kee-frame.core :as k]
            [re-frame.core :as rf]))

(defn ^:private get-config [path key-base]
  {:method          :get
   :uri             (api-url path)
   :response-format (ajax/transit-response-format)
   :on-success      [(make-event-name key-base :success)]
   :on-failure      [(make-event-name key-base :failure)]})

(defn ^:private get-effect-map [cofx api-path event-base xhr-subkey]
  {:http-xhrio (get-config api-path event-base)
   :db         (assoc-in (:db cofx) [:xhr xhr-subkey] {:in-flight? true})})

(k/reg-event-fx
  :current-user/load
  (fn [cofx]
    (get-effect-map cofx "/current-user" :current-user/load :current-user)))
