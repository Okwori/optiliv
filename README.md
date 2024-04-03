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
- Create a Database `optiliv` in your PostgreSQL instance.
- Enter your Postgres DB details in the map within `:db.sql/connection` key of `:system/env`.

### Email 
The email config lives within the key ` :email/send-fn`, the options:

- `:email-type` The default is `:email-type/stdout-print` which prints the email to console.
- `:email-type/smtp` for real email which will require setting the `:smtp-server`, `:smtp-port`, `:smtp-username`,
  and `:smtp-password`

## Running
### Dev
To start a web server and frontend for the application, run in two terminals:
```shell
make backend
```
```shell
make frontend
```
Alternatively, run:
```shell
clj -M:dev
```
```shell
npx shadow-cljs watch app
```

In the REPL of the first terminal, type to start the webserver:
```clojure
(go) 
```

By default, application runs on `http://localhost:3000`

The default API is available under `http://localhost:3000/api`

To reload changes:

```clojure
(reset)
```

### Prod
TODO...

## Test
### Prerequisites
You also need to install
[chromedriver](https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver).

### One-time testing setup
To allow the test to run seamlessly with Clojure CLI
```clojure
clojure -R:test -e "(doseq [class '(speclj.platform.SpecFailure
                                    speclj.platform.SpecPending)]
                      (compile class))"
```

### WIP
To run Clojure tests:
```shell
make .test
```
To run end-to-end UI test
```shell
make .e2e
```

## License

Copyright © 2024 Simon Okwori, Danielle Zabala, Natasha Fields, Jason Berard
