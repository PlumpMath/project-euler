(ns project-euler.util
  "Utility functions and functions that have yet to be used in anger"
  (:require [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(defmacro logging-execution-time
  [& forms]
  `(let [start# (System/nanoTime)
         result# (do ~@forms)]
     (log/infof "Elapsed time: %f ms" (/ (double (- (. System (nanoTime)) start#)) 1000000.0))
     result#))

;; https://en.wikipedia.org/wiki/Methods_of_computing_square_roots#Digit-by-digit_calculation
(defn sqrt
  "Return the square root of the given natural number n as the pair [p r], where p is the greatest integer <= sqrt(n) and r is the integer \"remainder\", such that p^2 + r = n"
  [n]
  {:pre [(not (neg? n)) (integer? n)]}
  (let [in-pairs (loop [pairs () balance n]
                   (if (zero? balance)
                     pairs
                     (recur (cons (rem balance 100) pairs) (quot balance 100))))]
    (loop [pairs in-pairs p 0N remainder 0N]
      (if (seq pairs)
        (let [c (+ (* 100N remainder) (first pairs))
              [x y] (last (sequence (comp (map (fn [x] [x (* x (+ (* 20N p) x))]))
                                       (filter (fn [[x y]] (<= y c))))
                                    (range 10N)))]
          (recur (rest pairs) (+ (* 10N p) x) (- c y)))
        [p remainder]))))

(def perfect-square? (comp zero? second sqrt))

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

(defn rdigitize
  "Return a lazy sequence of the digits, in reverse order, that comprise the natural number n"
  [n]
  (when-not (zero? n) (lazy-seq (cons (rem n 10) (rdigitize (quot n 10))))))

(defn digitize
  "Return a sequence of the digits that comprise the natural number n"
  [n]
  (reverse (rdigitize n)))

(defn factors
  "Return all factor pairs of n"
  [n]
  (let [limit (Math/sqrt n)]
    (loop [acc #{} candidate 1]
      (if (> candidate limit)
        acc
        (recur (if (zero? (mod n candidate))
                 (conj acc candidate (quot n candidate))
                 acc)
               (inc candidate))))))

;; https://en.wikipedia.org/wiki/Trial_division
(defn prime-factorization
  "Return the prime factorization of n using trial division"
  [n]
  (let [limit (long (Math/ceil (Math/sqrt n)))
        candidates (take-while (fn [p] (<= p limit)) primes)]
    (if (= 1 n)
      () ; this matches the accepted definition for the prime factorization of 1
      (loop [[candidate & candidates] candidates]
        (if-not candidate
          (list n) ; n is itself prime since we have run out of candidates
          (if (zero? (mod n candidate))
            (cons candidate (prime-factorization (quot n candidate)))
            (recur candidates)))))))

(defn prime?
  "Is n a prime number?"
  [n]
  (let [limit (-> n sqrt first long)
        candidates (take-while (fn [p] (<= p limit)) primes)]
    (when-not (= 1 n)
      (loop [[candidate & candidates] candidates]
        (if-not candidate
          true
          (if (zero? (mod n candidate))
            false
            (recur candidates)))))))
