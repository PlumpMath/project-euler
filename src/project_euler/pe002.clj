(ns project-euler.pe002
  "By considering the terms in the Fibonacci sequence whose values do not exceed four million, find the sum of the even-valued terms."
  {:problem-page "https://projecteuler.net/problem=2"})

(def fib (lazy-cat '(1N 1N) (map + fib (rest fib))))
(def fib ((fn fib* [n-2 n-1]
            (cons n-2 (lazy-seq (fib* n-1 (+ n-1 n-2))))) 1N 1N))

(defn main
  []
  ^{:answer 4613732}
  (transduce
   (comp (take-while (fn [x] (< x 4000000)))
      (filter even?))
   +
   fib))

(defn alternate
  []
  ^{:answer 4613732}
  (loop [s 0 n2 1 n1 1]
    (let [fib (+ n2 n1)]
      (if (< fib 4000000)
        (recur (if (even? fib) (+ s fib) s) n1 fib)
        s))))
