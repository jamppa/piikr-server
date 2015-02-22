(ns piikr.core.venues
  (:require
    [piikr.clients.foursquare :as fs]
    [piikr.clients.instagram :as insta]))

(defn- with-insta-location [venues]
  (filter #(contains? % :insta-location-id) (map #(insta/with-location-id %) venues)))

(defn- with-recent-insta-media [venues]
  (map #(insta/with-recent-media %) venues))

(defn find-near [lon lat]
  (-> (fs/explore-venues-near lon lat)
    with-insta-location
    with-recent-insta-media))