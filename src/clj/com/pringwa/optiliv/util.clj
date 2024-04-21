(ns com.pringwa.optiliv.util
  (:require [clojure.string :as string]))

(defn error-response [status msg]
  {:status status
   :body {:error msg}})

(defn by-id
  [f xs]
  (into {}
        (for [x xs]
          [(f x) x])))

(defn not-nil?
  "Checks if a String is NOT NIL/NULL"
  [str]
  (not (nil? str)))

(defn not-empty?
  "Checks if a String is NOT EMPTY"
  [str]
  (not (empty? str)))

(defn not-found?
  "Checks if a String contains \"Not Found \" "
  [str]
  (not (string/includes? str "Not Found")))

(defn trim-not-found
  "Returns a vector from the given vec collection after removing EMPTY and NIL/NULL strings"
  [vec]
  (filter #(and (number? %) (> % 0)) vec))

(defn trimm
  "Returns a vector from the given vec collection after removing EMPTY and NIL/NULL strings"
  [vec]
  (filter #(and (not-empty? %) (not-nil? %) (not-found? %) ) vec))

