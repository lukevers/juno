(ns juno.git
  (:gen-class)
  (:require [clojure.java.shell :as shell]))

(defn isgit [dir]
  "Checks if the given directory is a git repository or not."
  (if (= (get (shell/sh "git" "rev-parse" :dir dir) :exit) 0) true false))