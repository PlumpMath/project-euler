(ns project-euler.pe015
  "Lattice paths"
  ; How many such routes are there through a 20×20 grid?
  {:problem-page "https://projecteuler.net/problem="}
  (:require [project-euler.util :refer [logging-execution-time]]
            [clojure.tools.logging :as log]
            [clojure.test :as test :refer [with-test is]]))

(def grid [20 20]) ; dimensions of grid

(defn constrain-position [position]
  (mapv (fn [[minimum ordinate maximum]]
         (min (max minimum ordinate) maximum))
      (map list [0 0] position grid)))

(def move-vector {:u [ 0 -1] :d [ 0  1] :l [-1  0] :r [ 1  0]})

(defn move [position direction]
  (let [position' (constrain-position (map + position (move-vector direction)))]
    (when-not (= position' position) position')))

(def vertices (for [j (range (inc (first grid))) i (range (inc (last grid)))]
                [i j]))

(def neighbors (reduce (fn [mem position]
                         (assoc mem position (reduce (fn [ns direction]
                                                       (if-let [position' (move position direction)]
                                                         (conj ns position')
                                                         ns))
                                                     #{} '(:d :r))))
                       {} vertices))

(def walk
  (memoize (fn [start]
             (if (= start grid)
               1
               (transduce (map walk) + (neighbors start))))))

(with-test
  (defn ^{:answer 137846528820} main
    []
    (logging-execution-time (walk [0 0])))
  (is (= (-> #'main meta :answer) (main))))
