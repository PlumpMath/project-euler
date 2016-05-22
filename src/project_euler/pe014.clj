(ns project-euler.pe014
  "Longest Collatz sequence"
  ;Which starting number, under one million, produces the longest chain?
  {:problem-page "https://projecteuler.net/problem=14"}
  (:require [project-euler.util :refer [logging-execution-time]]
            [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

;; OPTIMIZE: this does not benefit from being lazy.  Consider loop/recur
;; OPTIMIZE: cache the resulting chain at every entry point
(letfn [(collatz' [n] (if (even? n) (quot n 2) (+ (* 3 n) 1)))]
  (defn collatz
    "Return the (lazy) Collatz sequence starting at n"
    [n]
    (concat ((fn collatz* [n] (lazy-seq
                              (when (not= 1 n) (cons n (collatz* (collatz' n)))))) n) '(1))))

(with-test
  (defn ^{:answer 837799} main
    []
    ;; OPTIMIZE: cache map of entry points to length of chain
    (logging-execution-time
     (last (reduce (fn [[l n :as acc] candidate]
                     (let [l' (count (collatz candidate))]
                       (if (> l' l)
                         [l' candidate]
                         acc)))
                   [0 nil]
                   (range 1 1000000)))))
  (is (= (-> #'main meta :answer) (main))))
