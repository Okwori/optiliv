(ns com.pringwa.optiliv.config)

(goog-define DEBUG false)

(goog-define OPTILIV_API_HOST "")

(js/console.log "api-host" OPTILIV_API_HOST)

(defn api-url [path]
  (str OPTILIV_API_HOST "/api" path))
