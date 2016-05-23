(ns project-euler.pe020
  "Factorial digit sum"
  {:problem-page "https://projecteuler.net/problem=20"}
  (:require [project-euler.util :refer [logging-execution-time rdigitize]]
            [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

;Find the sum of the digits in the number 100!

(defn- factorial
  [n]
  (if (zero? n)
    1
    (* n (factorial (dec n)))))

(with-test
  (defn ^{:answer 648} main
    []
    (logging-execution-time
     (reduce + (rdigitize (factorial 100N)))))
  (is (= (-> #'main meta :answer) (main))))
