(ns com.pringwa.optiliv.handler.logout-spec
  (:require [com.pringwa.optiliv.handler.logout :as sut]
            [speclj.core :refer [around context describe it should-be
                                 should-contain should-have-invoked should= stub
                                 with with-stubs]]
            [speclj.stub :refer [first-invocation-of]]))

(describe "the logout handler"
  (with-stubs)

  (it "returns a 204 response"
    (should= 204 (:status (sut/handler nil))))

  (it "destroys the session from the database")

  (it "clears the user's cookie"
    (let [resp (sut/handler nil)]
      (should-contain :session resp)
      (should-be nil? (:session resp)))))
