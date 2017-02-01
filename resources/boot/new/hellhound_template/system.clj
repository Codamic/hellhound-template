(ns {{name}}.system
  (:require [com.stuartsierra.component :as component]
            [hellhound.components.websocket :refer [websocket-server]]
            [hellhound.components.webserver :refer [webserver]]
            [hellhound.system  :refer [defsystem stop-system]]
            [system.repl  :refer [set-init! start]]
            [{{name}}.handler :refer [dev-handler]]
            [hellhound.logger.core   :as logger]))


(defsystem dev-system
  (websocket-server)
  (webserver dev-handler))

(.addShutdownHook
 (Runtime/getRuntime)
 (Thread. (fn []
            (logger/info "Shutting down...")
            (stop-system))))


(defn -main [& args]
  (set-init! #'dev-system)
  (start))
