(ns qr-scanner.url)

(defn new-url-element [url]
  {:id (random-uuid)
   :url url})
