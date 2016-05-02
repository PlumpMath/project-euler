(ns project-euler.pe001
  "Find the sum of all the multiples of 3 or 5 below 1000.")

(defn main
  []
  ^{:answer 233168}
  (transduce (filter (fn [x] (or (zero? (rem x 5)) (zero? (rem x 3)))))
             +
             (range 1 1000)))

(defn alternate
  "Find the sum of all the multiples of 3 or 5 below 1000."
  []
  ^{:answer 233168}
  (reduce +
          (set
           (let [limiter (fn [x] (< x 1000))]
             (concat
              (take-while limiter (iterate (partial + 5) 0))
              (take-while limiter (iterate (partial + 3) 0)))))))
