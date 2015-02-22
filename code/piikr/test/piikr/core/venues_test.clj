(ns piikr.core.venues-test
  (:use
    midje.sweet
    [midje.util :only [testable-privates]])
  (:require
    [piikr.core.venues :as venues]
    [piikr.clients.foursquare :as fs]
    [piikr.clients.instagram :as insta]))

(def fs-venue {:id "123qwe" :insta-location-id "098poi"})
(def venue-with-recent-media (merge fs-venue {:media {}}))

(testable-privates piikr.core.venues with-insta-location)

(fact "should find venues with recent media near location"
  (venues/find-near 60.19 24.45) => [venue-with-recent-media]
  (provided
    (fs/explore-venues-near 60.19 24.45) => [fs-venue] :times 1
    (insta/with-location-id fs-venue) => fs-venue :times 1
    (insta/with-recent-media fs-venue) => venue-with-recent-media :times 1))

(fact "should return only venues with instagram location id"
  (with-insta-location [{:id "1"} {:id "2"}]) => '({:id "1" :insta-location-id "123qwe"})
    (provided
      (insta/with-location-id {:id "1"}) => {:id "1" :insta-location-id "123qwe"} :times 1
      (insta/with-location-id {:id "2"}) => {:id "2"} :times 1))