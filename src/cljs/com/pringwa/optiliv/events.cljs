(ns com.pringwa.optiliv.events
  (:require com.pringwa.optiliv.xhr
            [kee-frame.core :as k]
            [re-frame.core :as rf :refer [path]]
            [ajax.core :as ajax]))


(k/reg-event-fx :navigate-to
                (fn [cofx args]
                  {:navigate-to args}))

(k/reg-event-db ::set-modal-visibility
                [(path :modal)]
                (fn [_ [visible? modal-type extra-modal-state-map]]
                  (if-not visible?
                    {:modal/type :modal-type/hidden}
                    (assoc extra-modal-state-map :modal/type modal-type))))

(k/reg-event-db :clear-xhr
                (fn [db [& subkeys]]
                  (assoc-in db (into [:xhr] subkeys)
                            {:in-flight? false, :error nil, :success? false})))


