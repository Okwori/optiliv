(ns com.pringwa.optiliv.web.routes.pages
  (:require
    [com.pringwa.optiliv.web.middleware.exception :as exception]
    [com.pringwa.optiliv.web.pages.layout :as layout]
    [integrant.core :as ig]
    [reitit.ring.middleware.muuntaja :as muuntaja]
    [reitit.ring.middleware.parameters :as parameters]
    [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]))

(defn wrap-page-defaults []
  (let [error-page (layout/error-page
                     {:status 403
                      :title "Invalid anti-forgery token"})]
    #(wrap-anti-forgery % {:error-response error-page})))

(defn home [request]
  (layout/render request "home.html"))

(defn cws [request]
  (layout/render request "loginpage.html"))

(defn cfs [request]
  (layout/render request "cfs.html"))

(defn cfs2 [request]
  (layout/render request "cfs2.html"))

;; Routes
(defn page-routes [_opts]
  [["/" {:get home}]
   ["/agents" {:get home}]
   ["/buy" {:get home}]
   ["/change-email" {:get home}]
   ["/change-password" {:get home}]
   ["/customers" {:get home}]
   ["/cws" {:get cws}]
   ["/cfs" {:get cfs}]
   ["/cfs2" {:get cfs2}]
   ["/help" {:get home}]
   ["/list-property" {:get home}]
   ["/login" {:get home}]
   ["/logout" {:get home}]
   ["/home" {:get home}]
   ["/register" {:get home}]
   ["/rent" {:get home}]
   ["/signup" {:get home}]
   ["/verify-email" {:get home}]])

(defn route-data [opts]
  (merge
   opts
   {:middleware
    [;; Default middleware for pages
     (wrap-page-defaults)
     ;; query-params & form-params
     parameters/parameters-middleware
     ;; encoding response body
     muuntaja/format-response-middleware
     ;; exception handling
     exception/wrap-exception]}))

(derive :reitit.routes/pages :reitit/routes)

(defmethod ig/init-key :reitit.routes/pages
  [_ {:keys [base-path]
      :or   {base-path ""}
      :as   opts}]
  (layout/init-selmer! opts)
  [base-path (route-data opts) (page-routes opts)])

