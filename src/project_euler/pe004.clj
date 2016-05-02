(ns project-euler.pe004
  "Find the largest palindrome made from the product of two 3-digit numbers."
  {:problem-page "https://projecteuler.net/problem=4"}
  (:require [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(defn palindrome?
  [n]
  (= n (loop [f n r 0]
         (if (zero? f)
           r
           (recur (quot f 10) (+ (* 10 r) (rem f 10)))))))

(with-test
  (defn ^{:answer 906609} main
    []
    (apply max (filter palindrome? (for [x (range 100 1000) y (range x 1000)] (* x y)))))
  (is (= (-> #'main meta :answer) (main))))
