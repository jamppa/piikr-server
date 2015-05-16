(ns piikr.clients.foursquare-test
  (:use midje.sweet)
  (:require
    [piikr.clients.foursquare :as fs]))

(fact "should finalize api url with appended client and version details"
  (fs/with-client-params "http://foo.bar?foo=bar")
    => "http://foo.bar?foo=bar&client_id=TL4BVSAGMYCK0CBJUSSPAU2T21Y1YA3ATWCJ24QSBASPOCL2&client_secret=WLWWC4XEHT43BBADDWDSLBAMHZAIFCMP15XLXYV0ABHH1NQS&v=20150208")

(fact "should return venue explore api endpoint"
  (fs/explore-api-endpoint 1.234 1.234) => "https://api.foursquare.com/v2/venues/explore?ll=1.234,1.234&limit=25&section=nightlife&sortByDistance=1")

(fact "should return venue explore api endpoint with section"
  (fs/explore-api-endpoint 1.234 1.234 "food") => "https://api.foursquare.com/v2/venues/explore?ll=1.234,1.234&limit=25&section=food&sortByDistance=1")

(fact "should return list of recommended venues near"
  (fs/explore-venues-near 60.192059 24.945831) => truthy)