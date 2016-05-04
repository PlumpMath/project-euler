(ns project-euler.pe007
  "What is the 10 001st prime number?"
  {:problem-page "https://projecteuler.net/problem=6"}
  (:require [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

;; Sadly, this implementation runs out of stack due to the nested nature of calls to `remove` on
;; the lazy seq parameter.  A transducer-based solution would be nice, but the nesting nature of
;; composed transforms would probably exhaust the stack just the same.
(def primes
  "An infinite, lazy sequence of prime numbers"
  ((fn sieve [[p & candidates]]
          (lazy-seq (cons p (sieve (remove #(zero? (rem % p)) candidates)))))
        (iterate inc 2)))

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
  (defn ^{:answer 104743} main
    []
    (nth primes (dec 10001)))
  (is (= (-> #'main meta :answer) (main))))
