(ns project-euler.pe004
  "Find the largest palindrome made from the product of two 3-digit numbers.")

(defn palindrome?
  [n]
  (let [s (seq (str n))]
    (= s (reverse s))))

(defn main
  []
  ^{:answer 906609}
  (apply max (filter palindrome? (for [x (range 100 1000) y (range x 1000)] (* x y)))))
