;;; https://github.com/nayuki/Project-Euler-solutions/blob/master/Answers.txt
(defproject project-euler "0.1.0-SNAPSHOT"
  :description "Solutions to Project Euler problems"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.slf4j/slf4j-api "1.7.21"]
                 [org.slf4j/slf4j-log4j12 "1.7.21"]
                 [log4j/log4j "1.2.17"]]
  :jvm-opts ["-Xss2m"])
