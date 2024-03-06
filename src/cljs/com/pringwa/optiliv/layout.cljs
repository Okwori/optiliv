(ns com.pringwa.optiliv.layout)

(defn standard
  [page-id & children]
  [:div {:id page-id}
   (into [:<>] children)])

(defn modal
  ([identifier child] (modal identifier child nil))
  ([identifier child last-child]
   (let [id #(hash-map :id (str identifier "-" %))]
     [:div (id "page")
      [:section.section.is-main-section
       (conj [:div.container (id "modal")] child)]
      last-child])))
