(ns piikr.core
  (:require
    [org.httpkit.server :as httpkit])
  (:use piikr.handler))

(def server (atom nil))

(defn -main [& args]
  (println "Starting Piikr server...")
  (reset! server (httpkit/run-server #'app {:port 3000})))