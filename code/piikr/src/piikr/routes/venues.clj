(ns piikr.routes.venues
  (:use compojure.core)
  (:require
    [piikr.core.venues :as venues]
    [liberator.core :as liberator]))

(defn- find-venues [lon lat]
  (venues/find-near lon lat))

(liberator/defresource find-venues-near [lon lat]
  :available-media-types ["application/json"]
  :allowed-methods [:get]
  :handle-ok (find-venues lon lat))

(defroutes venue-routes
  (GET "/venues" [lon lat] (find-venues-near lon lat)))