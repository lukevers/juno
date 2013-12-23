(ns juno.core
  (:gen-class)
  (:require [juno.git :as git]
            [me.raynes.fs :as fs]
            [compojure.route :as route])
  (:use org.httpkit.server
        ring.middleware.resource
        compojure.core))

; Define our base. The base will be overwritten by the first argument
; by default. There has to be a base set so we know where to start
; hosting from.
(def base "")
; Define our current location.
(def loc "")

(defn header []
  "HTML header for pages."
  (def htm "<!DOCTYPE html><html><head><title>Juno</title>")
  (def htm (str htm "<link rel=\"stylesheet\" href=\"/static/css/style.css\"/>"))
  (str htm "</head>"))

(defn clist [ls]
  "Turn seq of files/folders in a directory to a list."
  (def htm "<ul>")
  (if (not (empty? loc))
    (if (.endsWith loc "/")
      (def htm (str htm "<a href=\"/\"><li>/</li></a><a href=\"../\"><li>..</li></a>"))
      (def htm (str htm "<a href=\"/\"><li>/</li></a><a href=\"" loc "/../\"><li>..</li></a>"))) nil)
  (if (.endsWith loc "/")
    (doseq [i ls] (def htm (str htm "<a href=\"" loc i "\"><li>" i "</li></a>")))
    (doseq [i ls] (def htm (str htm "<a href=\"" loc "/" i "\"><li>" i "</li></a>"))))
  (str htm "</ul>"))

(defn listdir [dir]
  "Since the given directory is not a git repository, we just continue
   to list the folders and files in this directory."
  (str (header) "<body>" (clist (fs/list-dir dir)) "</body></html>"))

(defn stats [dir]
  
  "")

(defn logs [dir]
  
  "")

(defn repo [dir]
  "Since the given directory is a git repository, we list stats about
   the git repo, the contents of the repo, and then show the log."
  (str (header) "<body>" (stats dir) (clist (fs/list-dir dir)) (logs dir) "</body></html>"))

(defn roots [req]
  ""
  (def dir (str base (get req :uri)))
  (def loc (get req :uri))
  ; If the location is at "/" we want to set it to be an empty string.
  (if (= loc "/") (def loc ""))
  ; Check if the current location is a git repo. If it is not a git
  ; repo, just list the dir, but if it is a git repo then we show
  ; stats about the repo, list the contents of the repo, and show the
  ; git log.
  (if (= (git/isgit dir) true)
    (repo dir)
    (listdir dir)))

(defroutes router
  ""
  (route/resources "/static/")
  (GET "/*" [] roots))

(defn logger [app]
  ""
  (fn [req]
    (println req)
    (app req)))

(def app 
  ""
  (-> router
      logger))

(defn -main [& args]
  ""
  (def base (first args))
  (run-server app {:port 8080}))