(ns com.pringwa.optiliv.handler.login-spec
  (:require [clojurewerkz.scrypt.core :as sc]
            [com.pringwa.optiliv.handler.login :as sut]
            [clojure.test :refer :all]
            [speclj.core :refer [around context describe it should-have-invoked
                                 should= stub with with-stubs]]))

(describe "the login handler"                               ;TODO define body
          (with-stubs)

          (it "returns 400 when the authentication was not successful")

          (context "when valid credentials are supplied"

                   (it "invokes the authenticate helper function")

                   (it "returns a 200 status code")

                   (it "returns a body that includes the id, email, and account type")

                   (it "adds session info")))
;
