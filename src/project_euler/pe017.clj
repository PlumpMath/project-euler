(ns project-euler.pe017
  "Number letter counts"
  ;; If all the numbers from 1 to 1000 (one thousand) inclusive were written out in words, how many letters would be used?
  {:problem-page "https://projecteuler.net/problem=17"}
  (:require [project-euler.util :refer [logging-execution-time
                                        rdigitize]]
            [clojure.test :as test :refer [with-test is]]
            [clojure.string :as string]
            [clojure.tools.logging :as log]))

(def units
  {1 "one"
   2 "two"
   3 "three"
   4 "four"
   5 "five"
   6 "six"
   7 "seven"
   8 "eight"
   9 "nine"
   10 "ten"
   11 "eleven"
   12 "twelve"
   13 "thirteen"
   14 "fourteen"
   15 "fifteen"
   16 "sixteen"
   17 "seventeen"
   18 "eighteen"
   19 "nineteen"})

(def tens
  {1 "ten"
   2 "twenty"
   3 "thirty"
   4 "forty"
   5 "fifty"
   6 "sixty"
   7 "seventy"
   8 "eighty"
   9 "ninety"})

(defn- wordify100
  [n]
  (let [[u t] (rdigitize n)]
    (cond
      (zero? n) nil
      (nil? t) (units u)
      (= 1 t) (units n)
      (< 1 t) (string/join "-" (filter identity [(tens t)
                                                    (units u)])))))

(defn- wordify1000
  [n]
  (if (= 1000 n)
    "one thousand"
    (let [hundreds (quot n 100)
          balance (mod n 100)]
      (let [s100 (when (pos? hundreds) (str (units hundreds) " hundred"))]
        (string/join " and " (filter identity [s100
                                               (wordify100 balance)]))))))

(defn- squish
  [s]
  (string/replace s #"[-\s]" ""))

(with-test
  (defn ^{:answer 21124} main
    []
    (logging-execution-time (transduce (comp (map wordify1000)
                                          (map squish)
                                          (map count))
                                       +
                                       (range 1 (inc 1000)))))
  (is (= (-> #'main meta :answer) (main))))
