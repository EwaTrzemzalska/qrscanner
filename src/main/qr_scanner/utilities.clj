(ns qr-scanner.utilities
  (:require [clojure.java.shell :as shell]))

(defn get-short-sha-string
  []
  (-> (shell/sh "git" "rev-parse" "--short" "HEAD")
      :out
      .trim))

(defn get-commit-date
  [sha]
  (-> (shell/sh "git" "show" "-s" "--format=%ci" sha)
      :out
      .trim))

(defn get-app-version []
  (let [sha (get-short-sha-string)]
    (str sha " - " (get-commit-date sha))))

(defmacro app-version [] (get-app-version))
