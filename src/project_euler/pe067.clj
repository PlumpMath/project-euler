(ns project-euler.pe067
  "Maximum path sum II"
  ;; Find the maximum total from top to bottom in triangle.txt
  {:problem-page "https://projecteuler.net/problem=67"}
  (:require [project-euler.util :refer [logging-execution-time]]
            [clojure.java.io :as io]
            [clojure.test :as test :refer [with-test is]]
            [clojure.tools.logging :as log]))

(def rows (map (fn [row] (map #(Integer/parseInt %) (re-seq #"\d+" row)))
       (line-seq (io/reader (io/resource "p067_triangle.txt")))))

(with-test
  (defn ^{:answer 7273} main
    []
    (logging-execution-time
     (first (reduce (fn [best-paths row]
                      (map (fn [v [l r]] (+ v (if (< l r) r l)))
                           row (partition 2 1 best-paths)))
                    (reverse rows)))))
  (is (= (-> #'main meta :answer) (main))))
