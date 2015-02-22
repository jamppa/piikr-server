(ns piikr.core.venues-test
  (:use midje.sweet)
  (:require
    [piikr.core.venues :as venues]
    [piikr.clients.foursquare :as fs]
    [piikr.clients.instagram :as insta]))

(def fs-venue {:id "123qwe"})
(def venue-with-recent-media (merge fs-venue {:media {}}))

(fact "should find venues with recent media near location"
  (venues/find-near 60.19 24.45) => [venue-with-recent-media]
  (provided
    (fs/explore-venues-near 60.19 24.45) => [fs-venue] :times 1
    (insta/with-location-id fs-venue) => fs-venue :times 1
    (insta/with-recent-media fs-venue) => venue-with-recent-media :times 1))