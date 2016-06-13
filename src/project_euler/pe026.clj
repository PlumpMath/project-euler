(ns project-euler.pe026
  "Reciprocal Cycles"
  ;; Find the value of d < 1000 for which 1/d contains the longest recurring cycle in its decimal fraction part.
  {:problem-page "https://projecteuler.net/problem=26"}
  (:require [project-euler.util :refer [logging-execution-time prime-factorization]]
            [clojure.tools.logging :as log]
            [clojure.test :as test :refer [with-test is]]))

;; http://www.maths.surrey.ac.uk/hosted-sites/R.Knott/Fractions/fractions.html#section1.2
(defn lambda
  [n]
  {:pre [(> n 1) (not (zero? (* (mod n 2) (mod n 5))))]}
  (loop [a 10N l 1]
    (if (= 1N (mod a n)) l (recur (* 10N a) (inc l)))))

(let [f10 (set (prime-factorization 10))]
  (defn- period
    [n]
    ;; OPTIMIZE - don't bother with complete factorization, just prime-10/coprime-10
    (let [factors (prime-factorization n)
          prime-10 (reduce * (filter f10 factors)) ; OPTIMIZE - divide to get coprime-10
          coprime-10 (reduce * (remove f10 factors))]
      (if (and (< 1 prime-10) (= 1 coprime-10))
        0 ; terminating
        (lambda coprime-10)))))

(with-test
  (defn ^{:answer 983} main
    []
    (logging-execution-time
     (first (reduce (fn [[n p :as a] [n' p' :as b]] (if (> p p') a b))
                    (map (fn [n] [n (period n)]) (range 2 1000))))))
  (is (= (-> #'main meta :answer) (main))))
