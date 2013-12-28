(ns juno.git
  (:gen-class)
  (:require [clojure.java.shell :as shell]))

(defn isgit [dir]
  "Checks if the given directory is a git repository or not."
  (if (= (get (shell/sh "git" "rev-parse" :dir dir) :exit) 0) true false))

(defn user []
  "Gets the current git user."
  (get (shell/sh "git" "config" "--global" "user.name") :out))

(defn sha [dir]
  "Gets the full sha of HEAD of the given directory."
  (get (shell/sh "git" "rev-parse" "HEAD" :dir dir) :out))

(defn sha-short [dir]
  "Gets the short sha of HEAD of the given directory."
  (get (shell/sh "git" "rev-parse" "--short" "HEAD" :dir dir) :out))

(defn commits [dir]
  "Gets the number of commits from HEAD of the given directory."
  (get (shell/sh "git" "rev-list" "HEAD" "--count" :dir dir) :out))