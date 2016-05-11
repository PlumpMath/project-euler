(ns project-euler.pe009
  "There exists exactly one Pythagorean triplet for which a + b + c = 1000.
  Find the product abc."
  {:problem-page "https://projecteuler.net/problem=9"}
  (:require [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(with-test
  (defn ^{:answer 31875000} main
    []
    (let [triple (sequence
                  (comp
                   (filter (fn [[a b c]] (= (+ (* a a) (* b b)) (* c c))))
                   (map (partial apply *)))
                  (for [a (range 1 1000)
                        b (range (inc a) 1000)]
                    [a b (- 1000 (+ a b))]))]
      (assert (= 1 (count triple)))
      (first triple))) ; 200 375 425
  (is (= (-> #'main meta :answer) (main))))
