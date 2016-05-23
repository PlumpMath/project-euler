(ns project-euler.pe019
  "Counting Sundays"
  {:problem-page "https://projecteuler.net/problem=19"}
  (:require [project-euler.util :refer [logging-execution-time]]
            [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

;; 1 Jan 1900 was a Monday.
;; Thirty days has September,
;;  April, June and November.
;;  All the rest have thirty-one,
;;  Saving February alone,
;;  Which has twenty-eight, rain or shine.
;;  And on leap years, twenty-nine.
;; A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.
;; How many Sundays fell on the first of the month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?

(def days-of-the-week '(:mon :tue :wed :thu :fri :sat :sun))

(def normal-year-months
  [31 28 31 30 31 30 31 31 30 31 30 31])

(def leap-year-months
  (assoc normal-year-months 1 29))

(defn leap-year?
  [year]
  (and (zero? (mod year 4))
       (or (not (zero? (mod year 100)))
           (zero? (mod year 400)))))

(def calendar
  "Return a lazy sequence of date triples (y m d) starting at (1900 1 1)"
  ((fn date++ [[y m d :as date]]
     (lazy-seq (cons date (let [[m d] [(dec m) (dec d)] ; switch to zero-based counting
                                days-in-month ((if (leap-year? y) leap-year-months normal-year-months) m)
                                [d′ overflow] (let [d′ (inc d)] [(mod d′ days-in-month) (quot d′ days-in-month)])
                                [m′ overflow] (let [m′ (+ overflow m)] [(mod m′ 12) (quot m′ 12)])
                                y′ (+ y overflow)]
                            (date++ (list y′ (inc m′) (inc d′)))))))
   '(1900 1 1)))

(with-test
  (defn ^{:answer 171} main
    []
    (logging-execution-time
     (count (sequence (comp (map list)
                         (drop-while (fn [[date _]] (not= date [1901 1 1])))
                         (take-while (fn [[date _]] (not= date [2001 1 1])))
                         (filter (fn [[_ dow]] (= :sun dow)))
                         (filter (fn [[[_ _ d] _]] (= 1 d))))
                      calendar (cycle days-of-the-week)))))
  (is (= (-> #'main meta :answer) (main))))
