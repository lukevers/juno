(ns juno.core
  (:gen-class)
  (:require [me.raynes.fs :as fs])
  (:use org.httpkit.server
        compojure.core))

; Define our base. The base will be overwritten by the first argument
; by default. There has to be a base set so we know where to start
; hosting from.
(def base "")
; Define our current location.
(def loc "")

; Turns seq into an html list
(defn clist [ls]
  (def htm "<ul>")
  (if (not (empty? loc))
    (if (.endsWith loc "/")
      (def htm (str htm "<a href=\"../\"><li>..</a></li>"))
      (def htm (str htm "<a href=\"" loc "/../\"><li>..</a></li>"))) nil)
  (if (.endsWith loc "/")
    (doseq [i ls] (def htm (str htm "<a href=\"" loc i "\"><li>" i "</li></a>")))
    (doseq [i ls] (def htm (str htm "<a href=\"" loc "/" i "\"><li>" i "</li></a>"))))
  (str htm "</ul>"))

; This is where things get fun!
(defn roots [req]
  (def dir (str base (get req :uri)))
  (def loc (get req :uri))
  ; If the location is at "/" we want to set it to be an empty string.
  (if (= loc "/") (def loc ""))
  (def ls (fs/list-dir dir))
  (clist ls))

(defroutes router
  (GET "*" [] roots))

(defn logger [app]
  (fn [req]
    (println req)
    (app req)))

(def app 
  (-> router
      logger))
  
(defn -main [& args]
  (def base (first args))
  (run-server app {:port 8080}))