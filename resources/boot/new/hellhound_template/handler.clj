(ns {{name}}.handler
    (:require [hellhound.routes.core          :refer [make-handler
                                                      hellhound-routes GET
                                                      redirect-to-not-found]]
              [garm.controllers.home          :refer [home]]
              [ring.middleware.params         :refer [wrap-params]]
              [ring.middleware.keyword-params :refer [wrap-keyword-params]]
              [ring.middleware.reload         :refer [wrap-reload]]
              [ring.middleware.anti-forgery   :refer [wrap-anti-forgery]]
              [hellhound.middlewares.logger   :refer [wrap-logger]]
              [ring.middleware.session        :refer [wrap-session]]))


(def routes (make-handler
             ["/" [(GET "" home)
                   (hellhound-routes)
                   (redirect-to-not-found)]]))


(def dev-handler (-> #'routes
                     wrap-keyword-params
                     wrap-params
                     wrap-logger
                     wrap-anti-forgery
                     wrap-session
                     wrap-reload))

(def handler (-> #'routes
                 wrap-keyword-params
                 wrap-params
                 wrap-anti-forgery
                 wrap-session))
