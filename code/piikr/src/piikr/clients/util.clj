(ns piikr.clients.util
  (:use [clojure.walk :only (keywordize-keys)])
  (:require [clojure.data.json :as json]))

(defn serialize-response-body [response]
  (-> (:body response)
    (json/read-str)
    (keywordize-keys)))

(defn fill-template [url keywords]
  (reduce #(.replace %1 (str "{" (name %2) "}") (%2 keywords)) url (keys keywords)))