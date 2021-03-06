(ns project-euler.pe012
  "What is the value of the first triangle number to have over five hundred divisors?"
  {:problem-page "https://projecteuler.net/problem=12"}
  (:require [project-euler.util :refer [logging-execution-time factors]]
            [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(def triangles
  "A lazy seq of triangle numbers"
  ((fn tfun [sum n]
      (lazy-seq
       (let [n' (inc n)
             sum' (+ sum n')]
         (cons sum' (tfun sum' n'))))) 0 0))

(with-test
  (defn ^{:answer 76576500} main
    []
    (logging-execution-time
     (some (fn [t] (when (< 500 (count (factors t))) t)) triangles)))
  (is (= (-> #'main meta :answer) (main))))
