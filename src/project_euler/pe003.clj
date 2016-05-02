(ns project-euler.pe003
  "What is the largest prime factor of the number 600851475143 ?")

(defn factor?
  "Is p a factor of q?"
  [q p]
  (zero? (rem q p)))

; https://blog.ochronus.com/learn-clojure-with-project-euler-127dedcbff54#.lhbwkusb6
(defn get-max-prime-factor
  "Find the maximum prime factor of q starting with p-max, the largest currently known prime factor"
  [q p-max]
  (let [limit (Math/sqrt q)] ; The greatest factor of q cannot be greater than sqrt(q)
    (loop [n q candidate p-max]
      (if (> candidate limit)
        n ; there are no factors past the limit, thus n itself is prime and a factor of itself
        (if (factor? n candidate) ; is candidate a factor of n?
          (get-max-prime-factor (/ n candidate) candidate) ; yes - recurse to find its max prime factor
          (recur n (inc candidate)) ; no - try the next largest candidate
          )))))

(defn main
  []
  ^{:answer 6857}
  (let [n 600851475143N]
    (get-max-prime-factor n 2)))
