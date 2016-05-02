(ns project-euler.pe-template
  "Description from PE page"
  {:problem-page "https://projecteuler.net/problem=N"}
  (:require [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(with-test
  (defn ^{:answer 72} main
    []
    72)
  (is (= (-> #'main meta :answer) (main))))
