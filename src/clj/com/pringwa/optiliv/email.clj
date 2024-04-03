(ns com.pringwa.optiliv.email
  (:require
    [integrant.core :as ig]
    [postal.core :refer [send-message]]))

(def ^:private email-from-addr "OptiLiv <notifications@premiumprep.com>")

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
    :content (str "<!doctype html> "
                  "<html> "
                  "<head> "
                  "<meta charset=\"UTF-8\"> "
                  "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.1\"> "
                  "<link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'> "
                  "<title>OptiLiv: Find Your Optimal Home</title> "
                  "<style> "
                  "html{ "
                  "font-family: 'Montserrat', sans-serif;"
                  "}"
                  ".section .container{ "
                  "padding:0px 0px; "
                  "margin-top: 0px;} "
                  ".card-content { "
                  "background-color: transparent; "
                  "padding: 0px;}"
                  ".box { "
                  "margin: 120px 0px; "
                  "padding: 100px 50px;} "
                  ".column{margin-top:20px;} "
                  ".columns.is.centered{justify-content: center;} "
                  "img{ "
                  "display: block; "
                  "margin-left: auto; "
                  "margin-right: auto;} "
                  "h4{padding-top: 30px;} "
                  ".code{" "color:#f8c104; "
                  "font-size: 40px;" "} "
                  ".button{
                          margin:20px 0px 20px;
                          padding: 0px 20px;
                          background-color:#f8c104;}
                          </style> "
                  "</head> "
                  "<body> "
                  "<section class=\"is-fullheight\"> "
                  "<div class=\"container\"> "
                  "<div class=\"column is-4 is-offset-4\"> "
                  "<div class=\"box\"> "
                  "<img src=\"cid:logo2.png\" style=\"max-height: 70px\" alt=\"OptiLiv\"> "
                  "<h4 class=\"title is-4 has-text-centered has-text-weight-bold\">Invitation!</h4>
                  <p>You have been invited to join OptiLiv! \n Click here to create your account:</p><br>
                  <a href=\"http://localhost:3000/signup?token=" token "\">"
                  "http://localhost:3000/signup?token=" token "
                  </a><br>
                  <p>If this you did not request access to OptiLiv, ignore this email.</p>
                  </div>
                  </div>
                  </div>
                  </section>
                  </body>
                  </html>")}
   {:type    :attachment
    :content (java.io.File. "resources/public/img/logo2.png")}])

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
       "https://localhost:3000/signup?token=" token "\n\n"
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

