(ns piikr.clients.foursquare
  (:use 
    piikr.clients.util)
  (:require
    [org.httpkit.client :as http]))

(def base-api "https://api.foursquare.com")
(def explore-endpoint-template (str base-api "/v2/venues/explore?ll={lat},{lon}&limit=25&section={section}&sortByDistance=1"))
(def client-params-template "&client_id={clientId}&client_secret={clientSecret}&v={version}")

(def client-id "TL4BVSAGMYCK0CBJUSSPAU2T21Y1YA3ATWCJ24QSBASPOCL2")
(def client-secret "WLWWC4XEHT43BBADDWDSLBAMHZAIFCMP15XLXYV0ABHH1NQS")
(def version "20150208")

(defn with-client-params [url]
  (-> (str url client-params-template)
    (fill-template {:clientId client-id :clientSecret client-secret :version version})))

(defn explore-api-endpoint 
  ([lon lat]
    (explore-api-endpoint lon lat "nightlife"))
  ([lon lat section]
    (fill-template explore-endpoint-template {:lat (str lat) :lon (str lon) :section section})))

(defn items [fs-response]
  (if-let [groups (first (get-in fs-response [:response :groups]))]
    (:items groups) []))

(defn stripped-venue [fs-venue]
  (select-keys fs-venue [:id :name :location]))

(defn venues [resp]
  (let [body  (serialize-response-body resp)
        items (items body)]
    (map #(stripped-venue (:venue %)) items)))

(defn explore-venues-near [lon lat]
  (let [api-endpoint (-> (explore-api-endpoint lon lat) with-client-params)
        resp (http/get api-endpoint)]
    (venues @resp)))

(defn explore-venues-near-by-category [lon lat cat]
  (let [api-endpoint (-> (explore-api-endpoint lon lat cat) with-client-params)
        resp (http/get api-endpoint)]
    (venues @resp)))