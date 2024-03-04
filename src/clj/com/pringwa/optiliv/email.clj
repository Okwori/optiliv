(ns com.pringwa.optiliv.email
  (:require [clojure.string :as string]
            [clojure.instant :refer [read-instant-date]]
            [integrant.core :as ig]
            [postal.core :refer [send-message]]))

(def ^:private email-from-addr "OptiLiv <notifications@optiliv.com>")

(defmethod ig/init-key :email/send-fn
  [_ config]
  (let [{:keys [email-type smtp-username smtp-password smtp-server smtp-port]} config]
    (case email-type
      :email-type/stdout-print (fn [to subject body cc reply-to]
                                 (prn 'EMAIL to subject body cc reply-to))
      :email-type/smtp (let [smtp-config {:user smtp-username
                                          :pass smtp-password
                                          :host smtp-server
                                          :port smtp-port
                                          :tls  true}]
                         (fn [to subject body cc reply-to]
                           (let [base-mail-map {:from    email-from-addr :to to
                                                :subject subject :body body}
                                 add-cc (if cc (assoc base-mail-map :cc cc) base-mail-map)
                                 add-reply-to (if reply-to (assoc add-cc :reply-to reply-to) add-cc)]
                             (send-message smtp-config
                                           add-reply-to))))
      (throw (new RuntimeException
                  (format "Invalid value for :email-type (%s). See README."
                          (pr-str email-type)))))))

(defn- welcome-customer-body [token]
  [{:type    "text/html"
    :content (str "<p>Great news!</p>
    <p>Thanks so much,<br>
    OptiLiv</p>")}])

(defn welcome-customer [send-email email token]
  (let [subject "Welcome to OptiLiv "]
    (send-email email subject (welcome-customer-body token) nil nil)))


