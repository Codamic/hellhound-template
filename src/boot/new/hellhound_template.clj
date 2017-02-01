(ns boot.new.hellhound-template
  (:require [boot.new.templates :refer [renderer name-to-path ->files]]))

(def render (renderer "hellhound-template"))

(defn hellhound-template
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (println "Generating fresh 'boot new' hellhound-template project.")
    (->files data
             ["src/{{sanitized}}/foo.clj" (render "foo.clj" data)])))
