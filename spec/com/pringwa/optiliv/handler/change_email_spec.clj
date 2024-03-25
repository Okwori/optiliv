(ns com.premiumprep.app.handler.change-email-spec
  (:require [com.premiumprep.app.handler.change-email :as sut]
            [com.premiumprep.app.model.account :as account]
            [crypto.random :as crypto]
            [datomic.client.api :as d]
            [speclj.core :refer [around context describe it should-have-invoked
                                 should-not-have-invoked should= stub with
                                 with-stubs]]
            [com.premiumprep.app.email :as email]))
