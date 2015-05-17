(ns piikr.core.venues
  (:require
    [piikr.clients.foursquare :as fs]
    [piikr.clients.instagram :as insta]))

(defn- with-insta-location [venues]
  (filter #(contains? % :insta-location-id) (pmap #(insta/with-location-id %) venues)))

(defn- with-recent-insta-media [venues]
  (filter #(not (nil? (get-in % [:media :images :standard_resolution :url]))) (pmap #(insta/with-recent-media %) venues)))

(defn find-near [lon lat]
  (-> (fs/explore-venues-near lon lat)
    with-insta-location
    with-recent-insta-media))

(defn find-near-by-category [lon lat cat]
  (-> (fs/explore-venues-near-by-category lon lat cat)
    with-insta-location
    with-recent-insta-media))