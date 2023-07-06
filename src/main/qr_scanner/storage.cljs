(ns qr-scanner.storage
  (:require [cognitect.transit :as t]))

(def transit-writer (t/writer :json))

(def transit-reader (t/reader :json))

(defn clj->string [coll]
  (t/write transit-writer coll))

(defn string->clj [string]
  (t/read transit-reader string))

(defn save [item-name value]
  (assert (or (keyword? item-name)
              (string? item-name))
          "Item name should be a string or a keyword.")
  (.setItem js/localStorage
            (name item-name)
            (clj->string value)))

(defn retrieve [item-name]
  (assert (or (keyword? item-name)
              (string? item-name))
          "Item name should be a string or a keyword.")
  (string->clj (.getItem js/localStorage (name item-name))))
