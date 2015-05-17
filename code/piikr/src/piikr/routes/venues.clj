(ns piikr.routes.venues
  (:use 
    compojure.core
    ring.util.response)
  (:require
    [piikr.core.venues :as venues]))

(defn- find-venues [lon lat]
  (-> (venues/find-near lon lat)
    response))

(defn- find-venues-by-category [lon lat cat]
  (-> (venues/find-near-by-category lon lat cat)
    response))

(defroutes venue-routes

  (GET "/venues" [lon lat :as req]
    (find-venues lon lat))

  (GET "/venues/:category" [lon lat category :as req]
    (find-venues-by-category lon lat category)))