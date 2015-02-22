(ns piikr.routes.venues
  (:use 
    compojure.core
    ring.util.response)
  (:require
    [piikr.core.venues :as venues]))

(defn- find-venues [lon lat]
  (-> (venues/find-near lon lat)
    response))

(defroutes venue-routes
  (GET "/venues" [lon lat :as req] (find-venues lon lat)))