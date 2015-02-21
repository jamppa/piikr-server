(ns piikr.handler
  (:use piikr.routes.venues)
  (:require
    [compojure.core :refer :all]
    [compojure.handler :as handler]
    [compojure.route :as route]
    [ring.middleware.format-params :refer [wrap-restful-params]]
    [ring.middleware.format-response :refer [wrap-restful-response]]))

(defroutes piikr-routes
  (context "/api" [] venue-routes)
  (route/not-found "not found 404"))

(def app
  (->
    (handler/site piikr-routes)
    (wrap-restful-params)
    (wrap-restful-response)))