(ns piikr.clients.instagram-test
  (:use midje.sweet)
  (:require [piikr.clients.instagram :as insta]))

(fact "should append client params to given url"
  (insta/with-client-params "http://foo.bar?") => "http://foo.bar?client_id=4d4d4ede5cea4a059c644c2e4b6f64f7&client_secret=228621a3fcf246c4a76362700abd4779")

(fact "should return search locations endpoint"
  (insta/search-locations-endpoint "123qwe") => "https://api.instagram.com/v1/locations/search?foursquare_v2_id=123qwe&")

(fact "should return instagram location when searching it by foursquare id"
  (insta/search-location "4b1809a0f964a520dccb23e3") => (contains {:id "1246349", :name "Maltainen Riekko"}))

(fact "should return nil when location is not found by foursquare id"
  (insta/search-location "123qwe") => nil)

(fact "should return foursquare object with location id"
  (insta/with-location-id {:id "4b1809a0f964a520dccb23e3"}) => {:id "4b1809a0f964a520dccb23e3", :insta-location-id "1246349"})

(fact "should return foursquare object without location id when such is not found"
  (insta/with-location-id {:id "123qwe"}) => {:id "123qwe"})

(fact "should return recent media endpoint"
  (insta/recent-media-endpoint "123456") => "https://api.instagram.com/v1/locations/123456/media/recent?")

(fact "should get recent media object from a given location"
  (insta/recent-media "1246349") => truthy)

(def insta-media {:cool "stuff"})
(fact "should attach recent media to given object with location id"
  (insta/with-recent-media {:insta-location-id "1246349"}) => {:insta-location-id "1246349" :media insta-media}
  (provided
    (insta/recent-media "1246349") => insta-media :times 1))

(fact "should return given object when media not found with location id"
  (insta/with-recent-media {:insta-location-id "1246349"}) => {:insta-location-id "1246349"}
  (provided
    (insta/recent-media "1246349") => nil :times 1))