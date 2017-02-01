(ns boot.new.hellhound
  (:require [boot.new.templates :refer [renderer name-to-path ->files]]))

(def render (renderer "hellhound-template"))

(defn- readln
  [msg]
  (print (str msg " [y/n] "))
  (clojure.string/lower-case (read-line)))

(defn- y-n
  [msg default]
  (loop [answer (readln msg)]
    (cond
      (= answer "y") true
      (= answer "n") false
      (nil? answer)  default
      :else (recur (readln msg)))))

(defn hellhound
  "Create a basic HellHound web application template."
  [name & args]
  (let [data {:name name
              :sanitized (name-to-path name)}
        docker (y-n "Do you want docker support?" false)]
    (println "Generating fresh 'hellhound' project.")
    (println name " has the following arguments: " args)

    (->files data
             ["build.boot" (render "build.boot" data)]
             ["LICENSE"    (render "LICENSE"    data)]
             ["README.md"  (render "README.md"  data)]
             ["boot.properties"  (render "boot.properties"  data)]
             ["src/clj/{{sanitized}}/handler.clj" (render "handler.clj" data)]
             ["src/clj/{{sanitized}}/system.clj"  (render "system.clj" data)])))
