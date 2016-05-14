(ns project-euler.pe010
  "Find the sum of all the primes below two million."
  {:problem-page "https://projecteuler.net/problem=10"}
  (:require [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

;; This solution is tail-recursive and passes a fully realized data structure that
;; grows s l o w l y.
;; https://www.cs.hmc.edu/~oneill/papers/Sieve-JFP.pdf
;; http://diditwith.net/2009/01/20/YAPESProblemSevenPart2.aspx
;; http://yonatankoren.com/post/3-lazy-prime-sequence
(def primes
  "An infinite, lazy sequence of prime numbers"
  (letfn [(reinsert [table x prime] (update-in table [(+ prime x)] conj prime))]
    ((fn sieve [table d]
       (if-let [factors (get table d)]
         (recur (reduce #(reinsert %1 d %2) (dissoc table d) factors)
                (inc d)) ; tail recursion w/ fully realized data structure
         (lazy-seq (cons d (sieve (assoc table (* d d) (list d))
                                  (inc d)))))) ; lazy seq with fully realized data structure
     {} 2)))

(with-test
  (defn ^{:answer 142913828922} main
    []
    (transduce (take-while (fn [n] (< n 2000000))) + primes))
  (is (= (-> #'main meta :answer) (main))))
