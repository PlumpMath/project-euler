(ns project-euler.pe005
  "What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?"
  {:problem-page "https://projecteuler.net/problem=5"}
  (:require [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

;; Restatement: find the least common multiple of all of the numbers from 1 to 20
;; Reference: https://en.wikipedia.org/wiki/Least_common_multiple

(defn factor?
  "Is p a factor of q?"
  [q p]
  (zero? (rem q p)))

(defn- sieve
  "A simplistic implementation of the Sieve of Erasthones for finding all prime numbers less than limit"
  [limit]
  (let [max (Math/sqrt limit)]
    (loop [primes [] [prime & rest :as candidates] (range 2 (inc limit))]
      (if (> prime max)
        (concat primes candidates)
        (recur (conj primes prime) (remove #(factor? % prime) rest))))))

;; https://en.wikipedia.org/wiki/Least_common_multiple#A_method_using_a_table
(defn lcm
  "Similar to parallel prime factorization"
  [& ns]
  (loop [factors [] ns ns [prime & rest :as primes] (sieve (apply max ns))]
    (if (every? (partial = 1) ns)
      (reduce * factors)
      (let [reduced (map #(if (factor? % prime) (/ % prime) %) ns)]
        (if (= reduced ns) ; did we make progress by reduction?
          (recur factors ns rest) ; no, try the next prime
          (recur (conj factors prime) reduced primes))))))

(with-test
  (defn ^{:answer 232792560} main
    "Find the least common multiple of a range"
    []
    (apply lcm (range 1 (inc 20))))
  (is (= (-> #'main meta :answer) (main))))
