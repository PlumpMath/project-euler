(ns project-euler.pe024
  "Lexicographic permutations"
  {:problem-page "https://projecteuler.net/problem=24"}
  (:require [project-euler.util :refer [logging-execution-time]]
            [clojure.math.combinatorics :as combo]
            [clojure.tools.logging :as log]
            [clojure.test :as test :refer [with-test is]]))

(with-test
  (defn ^{:answer "2783915460"} main
    []
    (logging-execution-time
     (let [elements [0 1 2 3 4 5 6 7 8 9]]
       (apply str
              (combo/nth-permutation elements (dec 1000000))))))
  (is (= (-> #'main meta :answer) (main))))
