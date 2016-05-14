(ns project-euler.pe012
  "What is the value of the first triangle number to have over five hundred divisors?"
  {:problem-page "https://projecteuler.net/problem=12"}
  (:require [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(defn factors
  [n]
  (let [limit (Math/sqrt n)]
    (loop [acc #{} candidate 1]
      (if (> candidate limit)
        acc
        (recur (if (zero? (mod n candidate))
                 (conj acc candidate (quot n candidate))
                 acc)
               (inc candidate))))))

(def triangles
  "A lazy seq of triangle numbers"
  ((fn tfun [sum n]
      (lazy-seq
       (let [n' (inc n)
             sum' (+ sum n')]
         (cons sum' (tfun sum' n'))))) 0 0))

(with-test
  (defn ^{:answer 76576500} main
    []
    (some (fn [t] (when (< 500 (count (factors t))) t)) triangles))
  (is (= (-> #'main meta :answer) (main))))
