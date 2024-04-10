(ns com.pringwa.optiliv.config)

(goog-define DEBUG false)

(goog-define OPTILIV_API_HOST "optiliv.onrender.com")

(js/console.log "api-host" OPTILIV_API_HOST)

(defn api-url [path]
  (str OPTILIV_API_HOST "/api/v1" path))
