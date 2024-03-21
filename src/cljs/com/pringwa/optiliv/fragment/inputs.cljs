(ns com.pringwa.optiliv.fragment.inputs)

(defn field
  ([name type label]
   (field name type label false))
  ([name type label required?]
   (field name type label required? nil))
  ([name type label required? placeholder]
   (field name type label required? placeholder {}))
  ([name type label required? placeholder extra-opts]
   (field name type label required? placeholder extra-opts nil))
  ([name type label required? placeholder extra-opts abbr]
   [:div.field
    [:label.label {:for name} (if required? (str label " *") label)]
    [:div.control
     (let [input [:input.input (merge {:id name, :type type, :name name,
                                       :placeholder placeholder, :required required?}
                                      extra-opts)]]
       (if abbr
         [:abbr {:title abbr} input]
         input))]]))

(defn textarea [name label]
  [:div.field
   [:label.label {:for name} label]
   [:div.control
    [:textarea.textarea {:id name, :name name}]]])
