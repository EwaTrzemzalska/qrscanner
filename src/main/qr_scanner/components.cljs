(ns qr-scanner.components
  (:require [reagent-mui.material.list :as mui.list]
            [reagent-mui.material.box :as mui.box]
            [reagent-mui.material.list-subheader :as mui.list-subheader]
            [reagent-mui.material.list-item :as mui.list-item]
            [reagent-mui.material.list-item-icon :as mui.list-item-icon]
            [reagent-mui.material.paper :as mui.paper]
            [reagent-mui.material.list-item-text :as mui.list-item-text]
            [reagent-mui.material.typography :as mui.typography]
            [reagent-mui.material.list-item-secondary-action :as mui.list-item-secondary-action]
            [reagent-mui.icons.delete :as icons.delete]
            [reagent-mui.icons.link :as icons.link]
            [reagent-mui.material.icon-button :as mui.icon-button]
            [re-frame.core :as rf]
            [qr-scanner.scanner :as scanner])
  (:require-macros [qr-scanner.utilities :as utilities]))

(defn delete-url [id]
  [:div
   [mui.icon-button/icon-button {:on-click (fn [e]
                                             (.stopPropagation e)
                                             (scanner/remove-url id))}
    [icons.delete/delete]]])

(defn history []
  (let [urls @(rf/subscribe [:urls])]
    [mui.paper/paper {:variant :outlined
                      :sx {:p 2}}
     [mui.typography/typography {:variant "h3"} "📸 Scan away"]
     [mui.typography/typography {:cariant "body"} "Welcome to the scanner app. Use the scanner on the right to scan QR codes. Links will appear in the list below for you to click."]
     [mui.list/list {:dense true}
      [mui.list-subheader/list-subheader "Scanned links"]
      (for [{:keys [url id]} urls]
        [mui.list-item/list-item {:key id
                                  :component "a"
                                  :href url
                                  :target "_blank"
                                  :rel "noopener noreferrer"}
         [mui.list-item-icon/list-item-icon
          [icons.link/link]]
         [mui.list-item-text/list-item-text
          url]
         [mui.list-item-secondary-action/list-item-secondary-action
          [delete-url id]]])]
     [mui.box/box {:sx {:color "text.secondary"
                        :font-size 12
                        :mt 2}}
      "App version: " (utilities/app-version)]]))
