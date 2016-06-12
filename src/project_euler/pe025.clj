(ns project-euler.pe025
  "1000-digit Fibonacci number"
  ;; What is the index of the first term in the Fibonacci sequence to contain 1000 digits?
  {:problem-page "https://projecteuler.net/problem=25"}
  (:require [project-euler.util :refer [logging-execution-time]]
            [clojure.tools.logging :as log]
            [clojure.test :as test :refer [with-test is]]))

(def fib ((fn fib* [n-2 n-1]
            (cons n-2 (lazy-seq (fib* n-1 (+ n-1 n-2))))) 1N 1N))

(defn digits? [digits n]
  (not (pos? (quot n (reduce * (repeat (dec digits) 10N))))))

(with-test
  (defn ^{:answer 4782} main
    []
    (logging-execution-time
     (inc (count (take-while (partial digits? 1000) fib)))))
  (is (= (-> #'main meta :answer) (main))))
