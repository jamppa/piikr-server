(defproject piikr "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.5"]
                 [compojure "1.3.1"]
                 [liberator "0.12.2"]
                 [ring-middleware-format "0.4.0"]
                 [http-kit "2.1.16"]
                 [clj-jwt "0.0.11"]
                 [clj-time "0.9.0"]
                 [environ "1.0.0"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler piikr.handler/app}
  :main piikr.server
  :profiles
  {:dev
    {:dependencies
      [[javax.servlet/servlet-api "2.5"]
        [ring-mock "0.1.5"]
        [midje "1.6.3"]]
    :plugins [[lein-midje "3.1.3"]]}})
