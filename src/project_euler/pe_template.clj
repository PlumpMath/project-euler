(ns project-euler.pe-template
  "Description from PE page"
  {:problem-page "https://projecteuler.net/problem=N"}
  (:require [project-euler.util :refer [logging-execution-time]]
            [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(with-test
  (defn ^{:answer 72} main
    []
    (logging-execution-time 72))
  (is (= (-> #'main meta :answer) (main))))
