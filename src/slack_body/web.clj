(ns slack-body.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site api]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.util.response :refer [response]]
            [ring.middleware.params :as params]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [slack-body.body :as body]
            [slack-body.slack :as slack]
            ))

(use '[environ.core])

(def slack-base-url
  (env :slack-url))

(defn body-to-message
  [body-parts user]
  (clojure.string/join "\n" (assoc body-parts 0 (str (get body-parts 0) " -" user))))

(defn body-response [req]
  (let [text (str (get-in req [:params :text]))
        command (get-in req [:params :command])
        channel (get-in req [:params :channel_name])
        requester (get-in req [:params :user_name])]
    (let [{part 1 emoji 2} (re-matches #"(?i)^(\S+)\s+(\S+).*" text)]
      (if (and part emoji)
        (do
          (body/add-part (keyword part) emoji)
          (response (clojure.string/join ", " ((keyword  part)  @body/body-parts))))
        (let [body-parts (body/generate)
              text-string (body-to-message (into [] body-parts) requester)
              username (clojure.string/replace (clojure.string/join "-" body-parts) #":" "")]
          (slack/post slack-base-url channel username text-string)
          (response nil))))))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (pr-str ["Hello" :from 'Heroku])})

(defroutes app-routes
  (GET "/" [] (splash))
  (ANY "/body" [] body-response)
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))


(def app
  (-> (api app-routes)
      (params/wrap-params)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
