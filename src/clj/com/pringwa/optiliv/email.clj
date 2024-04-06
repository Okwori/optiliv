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
    :content (str "<!doctype html>"
                  "\n<html>"
                  "\n    <head>"
                  "\n        <meta charset=\"UTF-8\">"
                  "\n        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.1\">"
                  "\n        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.4/css/bulma.min.css\">"
                  "\n        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>"
                  "\n        <title>OptiLiv: Find Your Optimal Home</title>"
                  "\n        <style>"
                  "\n          html{"
                  "\n            font-family: 'Montserrat', sans-serif;"
                  "\n          }"
                  "\n          .section .container{"
                  "\n            padding:0px 0px;"
                  "\n            margin-top: 0px;}"
                  "\n          .card-content {"
                  "\n            background-color: transparent;"
                  "\n            padding: 0px;}"
                  "\n          .box {"
                  "\n            margin: 120px 0px;"
                  "\n            padding: 100px 50px;}"
                  "\n          .column{margin-top:20px;}"
                  "\n          .columns.is.centered{justify-content: center;}"
                  "\n          img{ "
                  "\n            display: block;"
                  "\n            margin-left: auto;"
                  "\n            margin-right: auto;}"
                  "\n          h4{padding-top: 30px;}"
                  "\n          .code{"
                  "\n            color:#f8c104;"
                  "\n            font-size: 40px;"
                  "\n          }"
                  "\n          .button{"
                  "\n            margin:20px 0px 20px;"
                  "\n            padding: 0px 20px;"
                  "\n            background-color:#f8c104;}"
                  "\n          </style>"
                  "\n    </head>"
                  "\n    <body>  "
                  "\n        <section class=\"is-fullheight\">"
                  "\n            <div class=\"container\">"
                  "\n              <div class=\"column is-4 is-offset-4\">"
                  "\n                <div class=\"box\">"
                  "\n                  <img src=\"cid:logo\" style=\"max-height: 70px\">"
                  "\n                  <h4 class=\"title is-4 has-text-centered has-text-weight-bold\">Invitation!</h4>"
                  "\n                  <p>You have been invited to join OptiLiv! Click here to create your account:</p><br>"
                  "\n                   <a href=\"http://localhost:3000/signup?token=\"" token ">http://localhost:3000/signup?token=" token
                  "\n                   </a><br>"
                  "\n                  <div class=\"code has-text-weight-bold\">123456</div><br>"
                  "\n                  <p>If this you did not request access to OptiLiv, ignore this email.</p>"
                  "\n                </div>"
                  "\n              </div>"
                  "\n          </div> "
                  "\n        </section>"
                  "\n    </body>"
                  "\n</html>")}
   {:type    :attachment
    :content (java.io.File. "resources/public/img/logo2.png")
    :content-id "logo"}])

(defn welcome-customer [send-email email token]
  (let [subject "You got an invitation to OptiLiv "]
    (send-email email subject (welcome-customer-body token) nil nil)))

(defn- welcome-customer-intro-body []
  [{:type    "text/html"
    :content (str "<!doctype html>
<html>
    <head>
        <meta charset=\"UTF-8\">
        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">
        <link rel=\"stylesheet\" href=\"https://unpkg.com/bulma@0.9.4/css/bulma.min.css\"/>
        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
        <title>OptiLiv: Find Your Optimal Home</title>
        <style>
            html{ font-family: 'Montserrat', sans-serif;}
            .section{margin:0px 50px;}
            .column{padding: 30px;}
            .media-content{padding: 5px;}
            .card-image{padding-top:20px}
            img{display: block;
                margin: 0px auto 0px auto;}
            a{color:#f8c104;
            font-weight: bold;
            font-size: 170%;}
        </style>
    </head>
    <body>
        <section class=\"section\">
            <div class=\"container has-text-centered\">
                <img src=\"images/logo9.png\" style=\"max-height: 80px\">
                <h2 class=\"title has-text-weight-bold\">WELCOME TO OPTILIV</h2>
                <p>Whether you're renting, buying or looking for an agent. We are here to find your optimal home. </p>

                <div class=\"columns is-centered\" >
                    <div class=\"column\">
                        <div class=\"card\">
                            <div class=\"card-image\">
                                <figure class=\"image\">
                                    <img src=\"images/renticon2.png\" style=\"max-height: 100px; max-width: 100px;\" alt=\"Placeholder image\">
                                </figure>
                            </div>
                            <div class=\"card-content\">
                                <div class=\"media\">
                                    <div class=\"media-content\">
                                        <a href=\"\">Rent</a> <span class=\"title is-4\"> a Home</span>
                                    </div>
                                </div>
                                <div class=\"content\">
                                    Explore our selection of rental properties in various neighborhoods. Find the ideal home that fits your lifestyle and budget.
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class=\"column\">
                        <div class=\"card\">
                            <div class=\"card-image\">
                                <figure class=\"image \">
                                    <img src=\"images/buyicon2.png\" style=\"max-height: 100px; max-width: 100px;\" alt=\"Placeholder image\">
                                </figure>
                            </div>
                            <div class=\"card-content\">
                                <div class=\"media-content\">
                                    <a href=\"\">Buy</a> <span class=\"title is-4\"> a Home</span>
                                </div>
                                <div class=\"content\">
                                    Start your journey to homeownership with us. From starter homes to luxury estates, we offer a wide range
                                    of properties for sale to help you find your dream home.
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class=\"column\">
                        <div class=\"card\">
                            <div class=\"card-image\">
                                <figure class=\"image\">
                                    <img src=\"images/agent.png\" style=\"max-height: 100px; max-width: 100px;\" alt=\"Placeholder image\">
                                </figure>
                            </div>
                            <div class=\"card-content\">
                                <div class=\"media-content\">
                                    <a href=\"\">Find</a> <span class=\"title is-4\"> an Agent</span>
                                </div>

                                <div class=\"content\">
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus nec iaculis mauris.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>")
    }
   {:type    :attachment
    :content (java.io.File. "resources/public/img/logo2.png")}])

(defn welcome-customer-intro [send-email email]
  (let [subject "Welcome to OptiLiv"]
    (send-email email subject (welcome-customer-intro-body) nil nil)))

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

