(ns app-driver
  (:require [clojure.string :as str]
            [etaoin.api :as et]
            [etaoin.keys :as keys]))

(def ^:private server-port 3333)
(def ^:private base-url (str "http://localhost:" server-port))
(def ^:private db-name "pp-integration-test")
(def superuser-email "superuser@email.com")
(def superuser-password "a really secure password!!!11")
(def ^:dynamic *d*) ; the Etaoin driver
(def ^:dynamic ^:private *system*) ; the running backend system
(def ^:private sent-emails (atom nil))

(def test-system-config)

(defn with-system [body-fn]
  )

(defn get-db [])

(defn get-sent-emails []
  @sent-emails)

(defn get-path [d]
  (let [url (et/get-url d)]
    (assert (.startsWith url base-url))
    (subs url (count base-url))))

(defn logout [d])
