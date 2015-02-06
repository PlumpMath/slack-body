(ns slack-body.slack
  (:require [org.httpkit.client :as http-kit]))

(defn post [slack-url channel from message]
  (http-kit/post slack-url
                 {:query-params {:channel (str "#" channel)}
                  :body message}))
