(ns qr-scanner.core
  (:require qr-scanner.state
            [reagent.dom :as r.dom]
            [qr-scanner.components :as components]
            [qr-scanner.scanner :as scanner]
            [re-frame.core :as rf]
            [qr-scanner.storage :as storage]))


(defn load-saved-urls
  []
  (rf/dispatch [:set-urls (or (storage/retrieve "urls") '())]))

(defn app []
  [components/history])

(defn ^:dev/after-load start-reagent-app
  []
  (let [node (.getElementById js/document "app")] (r.dom/render app node)))

(defn init
  []
  (scanner/start-qr-code-scanner)
  (load-saved-urls)
  (start-reagent-app))

(defn ^:dev/before-load stop [] (js/console.log "stop"))
