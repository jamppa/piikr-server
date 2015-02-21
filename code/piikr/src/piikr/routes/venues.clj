(ns piikr.routes.venues
  (:use compojure.core)
  (:require [liberator.core :as liberator]))

(liberator/defresource find-venues-near [lon lat]
  :available-media-types ["application/json"]
  :allowed-methods [:get]
  :handle-ok {:venues [], :lon lon, :lat lat})

(defroutes venue-routes
  (GET "/venues" [lon lat] (find-venues-near lon lat)))