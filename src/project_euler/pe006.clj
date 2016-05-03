(ns project-euler.pe006
  "Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum."
  {:problem-page "https://projecteuler.net/problem=6"}
  (:require [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(defn- square
  [x]
  (* x x))

(with-test
  (defn ^{:answer 25164150} main
    []
    (let [source (range 1 (inc 100))
          sum-of-squares (transduce (map square) + source)
          square-of-sums (square (reduce + source))]
      (- square-of-sums sum-of-squares)))
  (is (= (-> #'main meta :answer) (main))))
