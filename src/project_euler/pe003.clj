(ns project-euler.pe003
  "What is the largest prime factor of the number 600851475143 ?"
  {:problem-page "https://projecteuler.net/problem=3"}
  (:require [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(defn factor?
  "Is p a factor of q?"
  [q p]
  (zero? (rem q p)))

; https://blog.ochronus.com/learn-clojure-with-project-euler-127dedcbff54#.lhbwkusb6
(defn get-max-prime-factor
  "Find the maximum prime factor of q starting with p-max, the largest currently known prime factor"
  [q p-max]
  (let [limit (Math/sqrt q)] ; Factor pairs of q will have one of the pair below sqrt(q)
    (loop [n q candidate p-max]
      (if (> candidate limit)
        n ; there are no unfound factors past the limit, thus n itself is prime and a factor of itself
        (if (factor? n candidate) ; is candidate a factor of n?
          (get-max-prime-factor (/ n candidate) candidate) ; yes - recurse to find its max prime factor
          (recur n (inc candidate)) ; no - try the next largest candidate
          )))))

(with-test
  (defn ^{:answer 6857} main
    []
    (let [n 600851475143N]
      (get-max-prime-factor n 2)))
  (is (= (-> #'main meta :answer) (main))))
