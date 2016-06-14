(ns project-euler.pe027
  "Quadratic primes"
  ;; Find the product of the coefficients, a and b, for the quadratic expression that produces the maximum number of primes for consecutive values of n, starting with n = 0.
  {:problem-page "https://projecteuler.net/problem=27"}
  (:require [project-euler.util :refer [logging-execution-time prime? primes]]
            [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(defn gen-f [a b] (fn [n] (+ (* n n) (* a n) b)))

(defn generator-length
  [f]
  (loop [i 0]
    (let [n (f i)]
      (if (and (pos? n) (prime? n))
        (recur (inc i))
        i))))

(with-test
  (defn ^{:answer -59231} main
    []
    (logging-execution-time
     (let [[a b l] (->> (for [b (range -1001 1000) a (range -1001 1000)] [a b])
                        ;; OPTIMIZE: b can be constrained since max length is b-1
                        (map (fn [[a b]] [a b (generator-length (gen-f a b))]))
                        (reduce (fn [[_ _ l :as champ] [_ _ l' :as challenger]]
                                  (if (< l l') challenger champ))))]
       (* a b))))
  (is (= (-> #'main meta :answer) (main))))
