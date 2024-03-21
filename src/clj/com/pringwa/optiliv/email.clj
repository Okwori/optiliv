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

(defn- email-changed-body [new-email]
  (str "Somebody changed the email address associated with your OptiLiv account.\n\n"
       "It used to be this address, but now it is " new-email ".\n\n"
       "If you didn't do this, your account may be compromised. Reply to this email and ask for help.\n\n"
       "Otherwise, please check your other email account to find an email verification link.\n\n"
       "(If you made this change in mistake, you can log in with the same password and the email address above and change your email address back to this one.)\n\n"
       "Thanks,\n"
       "The OptiLiv Team\n"))

(defn- verify-email-body [token]
  (str "You either changed your OptiLiv email address to this one or someone else requested that we re-send this email.
        \n\n"
       "To continue using the OptiLiv web app, you need to verify that you own this email address.\n\n"
       "To do so, just visit this link:\n"
       "https://localhost:3000/verify-email?token=" token "\n\n"
       "If you don't know what any of this means, somebody may have mistyped their email address.
       Feel free to reply to this email and let us know, and we'll take care of it.\n\n"
       "Thanks,\n"
       "The OptiLiv Team\n"))

(defn change-email [send-email old-email new-email token]
  (send-email old-email "OptiLiv email changed"
              (email-changed-body new-email) nil nil)
  (send-email new-email "[OptiLiv] Verify this email address"
              (verify-email-body token) nil nil))

(defn verify-email [send-email email token]
  (send-email email "[OptiLiv] Verify this email address"
              (verify-email-body token) nil nil))

(defn- verified-email-body []
  (str "You have successfully verified this email address.\n\n"
       "You may now continue to use the Premium Prep app.\n\n"
       "Thanks,\n"
       "The OptiLiv Team\n"))

(defn verified-email [send-email email]
  (send-email email "[OptiLiv] Email address verified"
              (verified-email-body) nil nil))

