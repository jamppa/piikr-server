(ns piikr.clients.util-test
  (:use midje.sweet)
  (:require
    [piikr.clients.util :as util]))

(fact "should fill given template with values"
  (util/fill-template "/{foo}/{bar}" {:foo "kikkelis" :bar "kokkelis"}) => "/kikkelis/kokkelis")