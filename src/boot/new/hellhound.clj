(ns boot.new.hellhound
  (:require [boot.new.templates :refer [renderer name-to-path ->files]]))

(def render (renderer "hellhound-template"))

(defn hellhound
  "Create a basic HellHound web application template."
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (println "Generating fresh 'hellhound' project.")
    (->files data
             ["build.boot" (render "build.boot" data)])))

    (->files data
             ["src/clj/{{sanitized}}/handler.clj" (render "handler.clj" data)])))
