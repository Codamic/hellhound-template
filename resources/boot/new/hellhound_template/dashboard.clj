(ns {{name}}.controllers.home
  (:require [hellhound.system  :refer [get-system]]
            [selmer.parser     :as parser]))


(defn dashboard
  "home page controller."
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (parser/render-file "views/home/index.html" {:msg "Hello Client"})})
