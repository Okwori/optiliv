(ns com.pringwa.optiliv.web.controllers.logout
  (:require [clojure.tools.logging :as log]
            [com.pringwa.optiliv.web.routes.utils :as utils]))

(defn- logout [session req]
  (let [{:keys [identity]} session
        {:keys [query-fn]} (utils/route-data req)]
    (when session
      (query-fn :delete-session! {:identity identity}))))

(defn handler
  [{:keys [session] :as req}]
  (when (and (not-empty session) (:identity session))
    (logout session req))
  {:status  204,
   :headers {},
   :session (dissoc session :identity :roles)})
