(ns com.pringwa.optiliv.env
  (:require
    [clojure.tools.logging :as log]
    [com.pringwa.optiliv.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init       (fn []
                 (log/info "\n-=[optiliv starting using the development or test profile]=-"))
   :start      (fn []
                 (log/info "\n-=[optiliv started successfully using the development or test profile]=-"))
   :stop       (fn []
                 (log/info "\n-=[optiliv has shut down successfully]=-"))
   :middleware wrap-dev
   :opts       {:profile       :dev
                :persist-data? true}})
