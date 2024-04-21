(ns com.pringwa.optiliv.web.model.map
  (:require [com.pringwa.optiliv.web.model.google-map :as m]
            [com.pringwa.optiliv.web.model.calc :as calc]
            [com.pringwa.optiliv.util :as util])
  (:import [com.google.maps PlacesApi
                            GeoApiContext
                            GeocodingApi
                            DistanceMatrixApi]
           [com.google.maps.model LatLng RankBy PlaceType]))

; Google Console API
(def API-KEY "")

; Maximum distance(in meters) for search for a particular location
(def RADIUS 50000)

(defn define-context
  "Define API Key context"
  [api-key]
  (doto (GeoApiContext.)
    (.setApiKey API-KEY)))

(defn coordinates->keys
  "Gets the Latitude and Longitude Key pairs from the address->coordinates(which converts an address to GPS coordinate point
  of Latitude and Longitude, including its Address, City, and Country) and sets it to an object construct of the location pairs"
  [address context]
  (m/latlng (select-keys (m/address->coordinates
                           context
                           address) [:lat :lng])))

(defn do-nearby-search
  "Returns the closet PlaceType(https://developers.google.com/places/web-service/supported_types) to the location of the
   Property whose ID was entered as an arg"
  [address context place-type]
  (try
    (let [r (first (. (. (. (. (PlacesApi/nearbySearchQuery
                                 context
                                 (coordinates->keys address context))
                               rankby RankBy/DISTANCE) type place-type) await) results))]
      {:lat (-> r
                .geometry
                .location
                .lat)
       :lng (-> r
                .geometry
                .location
                .lng)})
    (catch NullPointerException e (select-keys (m/address->coordinates
                                                 context
                                                 address)
                                               [:lat :lng]))))

(defn calculate-distance-matrix
  "Returns the distance and duration (with the specific PlaceType) between the location of the Property whose ID was specified and the nearest
  facility(PlaceType - https://developers.google.com/places/web-service/supported_types)"
  [property-id context place-type]
  (let [r (. (. (. (DistanceMatrixApi/newRequest
                     context)
                   origins (into-array [(coordinates->keys property-id context)]))
                destinations (into-array [(m/latlng
                                            (do-nearby-search property-id context place-type))]))
             await)]

    {place-type
     {:distance (-> r
                    .rows
                    first
                    .elements
                    first
                    .distance
                    .inMeters)
      :duration (-> r
                    .rows
                    first
                    .elements
                    first
                    .duration
                    .humanReadable)}}))

(defn calculate-distance-matrix-bez-placetype
  "Returns the distance and duration between the location and the nearest
  facility(PlaceType - https://developers.google.com/places/web-service/supported_types)"
  [property-id context place-type]
  (let [r (. (. (. (DistanceMatrixApi/newRequest
                     context)
                   origins (into-array [(coordinates->keys property-id context)]))
                destinations (into-array [(m/latlng (do-nearby-search property-id context place-type))])) await)]

    {:distance (-> r
                   .rows
                   first
                   .elements
                   first
                   .distance
                   .inMeters)
     :duration (-> r
                   .rows
                   first
                   .elements
                   first
                   .duration
                   .humanReadable)}))

(defn get-priority-placestypes
  "Returns a list of vectors of PlaceTypes as specified in the vector passed as arg"
  [vec]
  (->> (util/trimm vec)
       (map (fn [n]
              (PlaceType/valueOf (.toUpperCase n))))))

(defn check-places-not-found-bez-placetype
  "Checks and Returns Place Type: \"PlaceType\" Not Found if the PlaceType is not within the 50000m radius.
  For example PlaceType: ..."
  [property-id context place-type]
  (if (.equals (:distance (calculate-distance-matrix-bez-placetype property-id context place-type) String) 0)
    {:distance (str "Place Type: " place-type " Not Found") :duration (str "Place Type: " place-type " Not Found")}
    (calculate-distance-matrix-bez-placetype property-id context place-type)))

(defn do-calculate-distance-matrix-for-priority-placetypes-bez-placetype
  "Returns Distance/Duration pair for places specified as priority places"
  [address, coll]
  (map (fn [n]
         (check-places-not-found-bez-placetype
           address
           (define-context API-KEY)
           n))
       (get-priority-placestypes coll)))

(defn get-distances-coll
  "Returns the distances only in meters after calculating the matrix for a collection of place types"
  [address, coll]
  (-> (mapv :distance
            (do-calculate-distance-matrix-for-priority-placetypes-bez-placetype address
                                                                                (util/trimm coll)))
      (util/trim-not-found)))

(defn get-rating
  "Returns the rating(in %ages) of the property of which ID was specified and PlaceType collection"
  [base-distance, address, coll]
  (let [r (-> (get-distances-coll address coll)
               (calc/calculate-points base-distance))]
    {:rating r}))

