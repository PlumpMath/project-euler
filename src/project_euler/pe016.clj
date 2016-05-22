(ns project-euler.pe016
  "Power digit sum"
  ;; What is the sum of the digits of the number 2^1000?
  {:problem-page "https://projecteuler.net/problem=16"}
  (:require [project-euler.util :refer [logging-execution-time
                                        rdigitize]]
            [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(defn pow
  [base exponent]
  (loop [result 1 exponent exponent]
    (if (zero? exponent)
      result
      (recur (* base result) (dec exponent)))))

(with-test
  (defn ^{:answer 1366} main
    []
    (logging-execution-time
     (let [n (pow 2N 1000)
           digits (rdigitize n)]
       (reduce + digits))))
  (is (= (-> #'main meta :answer) (main))))
