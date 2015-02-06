(defproject slack-body "1.0.0-SNAPSHOT"
  :description "Demo Clojure web app"
  :url "http://slack-body.herokuapp.com"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [http-kit "2.1.16"]
                 [compojure "1.1.8"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [environ "0.5.0"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]
            [lein-ring "0.9.1"]]
  :ring {:handler slack-body.web/app}
  :hooks [environ.leiningen.hooks]
  :uberjar-name "slack-body.jar"
  :profiles {:production {:env {:production true}}})
