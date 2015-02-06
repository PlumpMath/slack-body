(ns slack-body.body)

(def body-parts (atom {:head #{":cat:" ":skull:" ":rage1:"
                               ":rage2:" ":rage3:" ":rage4:"}
                       :body #{":shirt:"}
                       :legs #{":jeans:"}}))

(defn random-part
  "Get a random body part."
  [part]
  (first (shuffle (part @body-parts))))

; (map random-part [:head :body :legs])

(defn generate
  "Build a body from head, body and legs."
  []
  (map random-part [:head :body :legs]))

; (generate)

(defn add-part
  "Add an emoji body part."
  [part emoji-shot]
  (swap! body-parts assoc part
         (conj (part @body-parts) emoji-shot)))

