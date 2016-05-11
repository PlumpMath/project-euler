(ns project-euler.pe206
  "Find the unique positive integer whose square has the form 1_2_3_4_5_6_7_8_9_0,
where each “_” is a single digit."
  {:problem-page "https://projecteuler.net/problem=206"}
  (:require [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(defn- rdigitize
  "Return a lazy sequence of the digits, in reverse order, that comprise the natural number n"
  [n]
  (when-not (zero? n) (lazy-seq (cons (rem n 10) (rdigitize (quot n 10))))))

(defn- digitize
  "Return a sequence of the digits that comprise the natural number n"
  [n]
  (reverse (rdigitize n)))

(defn- inject
  "Inject the digits into the magic template and return the value"
  [template digits]
  (reduce (fn [acc d] (+ (* 10 acc) d))
          0 (concat (interleave (butlast template) digits) (list (last template)))))

(defn- match?
  [template n]
  (loop [[a & as] (take-nth 2 (rdigitize n)) [b & bs] (reverse template)]
    (if as
      (and (= a b) (recur as bs))
      n)))

;; https://en.wikipedia.org/wiki/Methods_of_computing_square_roots#Digit-by-digit_calculation
(defn- sqrt
  "Return the square root of the given natural number n as the pair [p r], where p is the greatest integer <= sqrt(n) and r is the integer \"remainder\", such that p^2 + r = n"
  [n]
  {:pre [(not (neg? n)) (integer? n)]}
  (let [in-pairs (loop [pairs () balance n]
                   (if (zero? balance)
                     pairs
                     (recur (cons (rem balance 100) pairs) (quot balance 100))))]
    (loop [pairs in-pairs p 0 remainder 0]
      (if (seq pairs)
        (let [c (+ (* 100 remainder) (first pairs))
              [x y] (last (sequence (comp (map (fn [x] [x (* x (+ (* 20 p) x))]))
                                       (filter (fn [[x y]] (<= y c))))
                                    (range 10)))]
          (recur (rest pairs) (+ (* 10 p) x) (- c y)))
        [p remainder]))))

(defn- square [x] (* x x))

(with-test
  (defn ^{:answer 1389019170 :square-of-answer 1929374254627488900} main
    []
    (let [template '(1 2 3 4 5 6 7 8 9 0)
          template' (butlast template)
          [lower _] (sqrt (inject template' (repeat 8 0)))
          [upper _] (sqrt (inject template' (repeat 8 9)))
          xform (fn [rf] (fn
                          ([] (rf))
                          ([acc] (rf acc))
                          ([acc n]
                           (rf acc (+ n 3))
                           (rf acc (+ n 7)))))
          xform (comp (map (partial * 10)) xform (map square))
          s' (some (partial match? template')
                   (sequence xform (range (quot lower 10) (quot upper 10))))
          r (* 10 (first (sqrt s')))]
      (assert (match? template (* r r)))
      r))
  (is (= (-> #'main meta :answer) (main))))
