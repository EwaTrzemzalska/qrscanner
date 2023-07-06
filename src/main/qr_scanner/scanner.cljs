(ns qr-scanner.scanner
  (:require ["html5-qrcode" :refer [Html5QrcodeScanner]]
            [goog.functions :as g.f]
            [re-frame.core :as rf]))


(defn save-url
  [url]
  (rf/dispatch [:add-url url]))

(defn remove-url
  [url-id]
  (rf/dispatch [:remove-url url-id]))

(def debounced-save-url (g.f/rateLimit save-url 5000))

(defn on-scan-success
  [decoded-text _decoded-result]
  (debounced-save-url decoded-text))

(defn on-scan-failure
  [error]
  ;; TODO: error handling
  )

(defn start-qr-code-scanner
  []
  (let [qr-scanner (Html5QrcodeScanner. "qr-reader"
                                        #js
                                         {:fps 10,
                                          :qrbox #js {:width 250, :height 250}}
                                        false)]
    (.render qr-scanner on-scan-success on-scan-failure)))
