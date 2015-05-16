(ns piikr.server
  (:require
    [org.httpkit.server :as httpkit])
  (:use piikr.handler)
  (:gen-class))

(def server (atom nil))

(defn -main [& args]
  (println "Starting Piikr server...")
  (reset! server (httpkit/run-server #'app {:port 3000})))