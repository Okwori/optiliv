# OptiLiv

An application to help you find the most optimal place to live!
 
## Prerequisites

- [Java Development Kit (version 11+)](https://bell-sw.com/pages/downloads/)
- [Clojure CLI tools (version 1.11+)](https://clojure.org/guides/install_clojure)
- [Postgresql](https://www.postgresql.org/download)
- [Node](https://nodejs.org/en/download)

## Setup
The system configuration live within `resources/system.edn`

### Database Configuration
Enter your Postgres DB details in the map within `:db.sql/connection` key of `:system/env`.

### Email 
The email config lives within the key ` :email/send-fn`, the options:

- ` :email-type` The default is `:email-type/stdout-print` which prints the email to console. Or real email with
`:email-type/smtp` which will require setting the `:smtp-server`, `:smtp-port`, `:smtp-username`,
  and `:smtp-password`

## Running
### Dev
To start a web server and frontend for the application, run in two terminals:

    make run
    make frontend

Alternatively, run:
    
    clj -M:dev
    npx shadow-cljs watch app

By default, application runs on `http://localhost:3000`

### Prod
TODO...

## Test
TODO...

## License

Copyright © 2024 Simon Okwori, Danielle Zabala, Natasha Fields, Jason Berard
