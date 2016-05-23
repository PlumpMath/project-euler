(ns project-euler.pe021
  "Amicable numbers"
  {:problem-page "https://projecteuler.net/problem=21"}
  (:require [project-euler.util :refer [logging-execution-time factors]]
            [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

;; Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
;; If d(a) = b and d(b) = a, where a ≠ b, then a and b are an amicable pair and each of a and b are called amicable numbers.
;; For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.

;; Evaluate the sum of all the amicable numbers under 10000.

(defn- proper-divisors
  "Return the set of proper divisors of n (numbers less than n which divide evenly into n)"
  [n]
  (disj (factors n) n))

(def d
  (memoize (fn [n] (reduce + (proper-divisors n)))))

(defn- amicable?
  [a]
  (let [b (d a)]
    (and (not= a b) (= a (d b)))))

(with-test
  (defn ^{:answer 31626} main
    []
    (logging-execution-time
     (reduce + (filter amicable? (range 10000)))))
  (is (= (-> #'main meta :answer) (main))))
