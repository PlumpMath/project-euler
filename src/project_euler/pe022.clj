(ns project-euler.pe022
  "Names scores"
  {:problem-page "https://projecteuler.net/problem=22"}
  (:require [project-euler.util :refer [logging-execution-time]]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

;; Using names.txt (right click and 'Save Link/Target As...'), a 46K text file containing over five-thousand first names, begin by sorting it into alphabetical order. Then working out the alphabetical value for each name, multiply this value by its alphabetical position in the list to obtain a name score.

;; For example, when the list is sorted into alphabetical order, COLIN, which is worth 3 + 15 + 12 + 9 + 14 = 53, is the 938th name in the list. So, COLIN would obtain a score of 938 × 53 = 49714.

;; What is the total of all the name scores in the file?

(def names
  (map (comp last (partial re-find #"\"(.+)\""))
       (string/split (slurp (io/resource "p022_names.txt")) #",")))

(let [origin (dec (int \A))]
  (defn score
    [s]
    (reduce + (map (fn [c] (- (int c) origin)) (seq s)))))

(with-test
  (defn ^{:answer 871198282} main
    []
    (logging-execution-time
     (reduce + (map-indexed (fn [i n] (* (inc i) (score n)))
                            (sort names)))))
  (is (= (-> #'main meta :answer) (main))))
