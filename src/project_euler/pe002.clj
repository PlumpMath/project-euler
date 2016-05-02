(ns project-euler.pe002
  "By considering the terms in the Fibonacci sequence whose values do not exceed four million, find the sum of the even-valued terms."
  {:problem-page "https://projecteuler.net/problem=2"})

(def fib (lazy-cat [0 1] (map + fib (rest fib))))

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
