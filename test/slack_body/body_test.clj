(ns slack-body.body-test
  (:require [clojure.test :refer :all]
            [slack-body.body :refer :all]))

(deftest grab-random-part
  (testing "a random body part is found"
    (are [part] (is (random-part part))
         :head
         :body
         :legs)))


(deftest generate-body
  (testing "a three part body should be generated"
           (is (= (count (generate)) 3))))
