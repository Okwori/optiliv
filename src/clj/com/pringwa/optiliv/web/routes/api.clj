(ns com.pringwa.optiliv.web.routes.api
  (:require
    [com.pringwa.optiliv.web.controllers.current-user :as current-user]
    [com.pringwa.optiliv.web.controllers.health :as health]
    [com.pringwa.optiliv.web.controllers.login :as login]
    [com.pringwa.optiliv.web.controllers.logout :as logout]
    [com.pringwa.optiliv.web.middleware.exception :as exception]
    [com.pringwa.optiliv.web.middleware.formats :as formats]
    [integrant.core :as ig]
    [reitit.coercion.malli :as malli]
    [reitit.ring.coercion :as coercion]
    [reitit.ring.middleware.muuntaja :as muuntaja]
    [reitit.ring.middleware.parameters :as parameters]
    [reitit.swagger :as swagger]))

(def route-data
  {:coercion   malli/coercion
   :muuntaja   formats/instance
   :swagger    {:id ::api}
   :middleware [;; query-params & form-params
                parameters/parameters-middleware
                  ;; content-negotiation
                muuntaja/format-negotiate-middleware
                  ;; encoding response body
                muuntaja/format-response-middleware
                  ;; exception handling
                coercion/coerce-exceptions-middleware
                  ;; decoding request body
                muuntaja/format-request-middleware
                  ;; coercing response bodys
                coercion/coerce-response-middleware
                  ;; coercing request parameters
                coercion/coerce-request-middleware
                  ;; exception handling
                exception/wrap-exception]})

;; Routes
(defn api-routes [_opts]
  [["/swagger.json"
    {:get {:no-doc  true
           :swagger {:info {:title "com.pringwa.optiliv API"}}
           :handler (swagger/create-swagger-handler)}}]
   ["/health"
    {:get health/healthcheck!}]
   ["/v1" {}
    ["/current-user" {:get {:handler current-user/handler}}]
    ["/login" {:post {:handler login/handler}}]
    ["/logout" {:delete logout/handler}]]
   ])

(derive :reitit.routes/api :reitit/routes)

(defmethod ig/init-key :reitit.routes/api
  [_ {:keys [base-path]
      :or   {base-path ""}
      :as   opts}]
  [base-path route-data (api-routes opts)])
