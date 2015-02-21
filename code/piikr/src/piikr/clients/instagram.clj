(ns piikr.clients.instagram
  (:use piikr.clients.util)
  (:require [org.httpkit.client :as http]))

(def base-api "https://api.instagram.com/v1")
(def search-locations-template "/locations/search?foursquare_v2_id={foursquareId}&")
(def recent-media-template "/locations/{locationId}/media/recent?")
(def client-params-template "client_id={clientId}&client_secret={clientSecret}")

(def client-id "4d4d4ede5cea4a059c644c2e4b6f64f7")
(def client-secret "228621a3fcf246c4a76362700abd4779")

(defn search-locations-endpoint [fs-id]
  (-> (str base-api search-locations-template)
    (fill-template {:foursquareId fs-id})))

(defn recent-media-endpoint [location-id]
  (-> (str base-api recent-media-template)
    (fill-template {:locationId location-id})))

(defn with-client-params [url]
  (-> (str url client-params-template)
    (fill-template {:clientId client-id :clientSecret client-secret})))

(defn location [resp]
  (let [body (serialize-response-body resp)]
    (first (:data body))))

(defn search-location [fs-id]
  (let [api-endpoint (-> (search-locations-endpoint fs-id) with-client-params)
        resp (http/get api-endpoint)]
    (location @resp)))

(defn with-location-id [fs-obj]
  (if-let [location (search-location (:id fs-obj))]
    (merge fs-obj {:insta-location-id (:id location)})
    fs-obj))

(defn stripped-media [media]
  (select-keys media [:type :caption :created_time :images :videos]))

(defn media [resp]
  (let [body (serialize-response-body resp)]
    (stripped-media (first (:data body)))))

(defn recent-media [location-id]
  (let [api-endpoint (-> (recent-media-endpoint location-id) with-client-params)
        resp (http/get api-endpoint)]
    (media @resp)))

(defn with-recent-media [obj]
  (if-let [media (recent-media (:insta-location-id obj))]
    (merge obj {:media media})
    obj))