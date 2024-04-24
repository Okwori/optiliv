(ns com.pringwa.optiliv.web.controllers.list-property
  (:require
    [com.pringwa.optiliv.email :as email]
    [com.pringwa.optiliv.web.routes.utils :as utils]))

(defn handler
  [{:keys [multipart-params session] :as req}]
  (if session
    (let [{:keys [query-fn send-fn]} (utils/route-data req)
          {:strs [name price address area description property_type_id
                  structure_id furniture_id]} multipart-params
          _ (query-fn :list-property! {:name            name :price (Double/parseDouble price) :address address
                                       :area            (Integer/parseInt area) :adjusted_by nil :user_id nil
                                       :description description :city_area_id nil
                                       :property_type_id (Integer/parseInt property_type_id)
                                       :image_url nil, :structure_id (Integer/parseInt structure_id)
                                       :furniture_id (Integer/parseInt furniture_id)})
          ;_ (email/welcome-customer send-fn email token)
          ]
      {:status 201,
       :body   {}})
    (utils/error-response 403 "forbidden")))
