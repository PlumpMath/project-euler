(ns project-euler.pe023
  "Non-abundant sums"
  {:problem-page "https://projecteuler.net/problem=23"}
  (:require [project-euler.util :refer [factors logging-execution-time]]
            [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

;; A perfect number is a number for which the sum of its proper divisors is exactly equal to the number. For example, the sum of the proper divisors of 28 would be 1 + 2 + 4 + 7 + 14 = 28, which means that 28 is a perfect number.

;; A number n is called deficient if the sum of its proper divisors is less than n and it is called abundant if this sum exceeds n.

;; As 12 is the smallest abundant number, 1 + 2 + 3 + 4 + 6 = 16, the smallest number that can be written as the sum of two abundant numbers is 24. By mathematical analysis, it can be shown that all integers greater than 28123 can be written as the sum of two abundant numbers. However, this upper limit cannot be reduced any further by analysis even though it is known that the greatest number that cannot be expressed as the sum of two abundant numbers is less than this limit.

;; Find the sum of all the positive integers which cannot be written as the sum of two abundant numbers.

(defn- abundant? [n]
  (< (* 2 n) (reduce + (factors n))))

(def abundants
  (sequence (filter abundant?) (range)))

(defn- all-sums
  "Find all sums less than limit of pairs of numbers from the addands sorted sequential"
  [addands limit]
  (reduce (fn [sums a] (reduce (fn [sums b] (conj sums (+ a b)))
                              sums
                              (take-while (fn [n] (<= n (- limit a))) addands)))
          #{}
          (take-while (fn [n] (<= n limit)) addands)))

(with-test
  (defn ^{:answer 4179871} main
    []
    (logging-execution-time
     (let [limit 28123 ; largest integer that cannot be expressed as SumOfTwoAbundants (sota)
           sotas (all-sums abundants limit)]
       (transduce (remove sotas) + (range 1 (inc limit))))))
  (is (= (-> #'main meta :answer) (main))))
