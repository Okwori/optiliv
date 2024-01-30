(ns com.pringwa.optiliv.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init       (fn []
                 (log/info "\n-=[optiliv starting]=-"))
   :start      (fn []
                 (log/info "\n-=[optiliv started successfully]=-"))
   :stop       (fn []
                 (log/info "\n-=[optiliv has shut down successfully]=-"))
   :middleware (fn [handler _] handler)
   :opts       {:profile :prod}})
