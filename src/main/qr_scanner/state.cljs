(ns qr-scanner.state
  (:require [re-frame.core :as rf]
            [qr-scanner.url :as url]
            [qr-scanner.storage :as storage]))


(rf/reg-sub
 :urls
 (fn [db]
   (:urls db)))

(rf/reg-event-db
 :set-urls
 (fn [db [_ urls]]
   (assoc db :urls urls)))

(rf/reg-event-fx
 :remove-url
 (fn [{:keys [db]} [_ url-id]]
   (let [current-urls (:urls db)
         new-urls (remove #(= url-id (:id %)) current-urls)]
     {:db (assoc db :urls new-urls)
      :set-storage-urls new-urls})))

(rf/reg-event-fx
 :add-url
 (fn [{:keys [db]} [_ url]]
   (let [url-element (url/new-url-element url)
         new-db (update db :urls conj url-element)]
     {:db new-db
      :set-storage-urls (:urls new-db)})))

(rf/reg-fx
 :set-storage-urls
 (fn [new-urls]
   (storage/save "urls" new-urls)))
