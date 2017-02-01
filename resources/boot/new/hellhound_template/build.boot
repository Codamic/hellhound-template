(set-env!
 :source-paths    #{"src/cljs"
                    "src/clj"
                    "resources/assets"}

 :resource-paths  #{"resources/statics"}
 :dependencies '[[org.clojure/clojure           "1.9.0-alpha14"]
                 [org.clojure/clojurescript     "RELEASE"]
                 [codamic/hellhound             "0.12.0"]
                 [yogthos/config                "0.8"]
                 [ring/ring-defaults            "0.3.0-beta1"]
                 [selmer                        "1.0.9"]

                 ;; Cljs repl dependencies ----------------------------
                 [adzerk/boot-cljs-repl      "0.3.3"     :scope "test"]
                 [com.cemerick/piggieback    "0.2.1"     :scope "test"]
                 [weasel                     "0.7.0"     :scope "test"]
                 [org.clojure/tools.nrepl    "0.2.12"    :scope "test"]
                 ;; ---------------------------------------------------

                 [org.danielsz/system        "0.3.2-SNAPSHOT"]])

(require
 '[adzerk.boot-cljs       :refer [cljs]]
 '[adzerk.boot-reload     :refer [reload]]
 '[adzerk.boot-cljs-repl  :refer [cljs-repl-env start-repl]]
 '[deraen.boot-less       :refer [less]]
 '[deraen.boot-sass       :refer [sass]]
 '[system.boot            :refer [system] :as s]
 '[hellhound.boot.helpers :refer :all]
 '[hellhound.boot.repl    :refer [cider]]
 '[environ.boot           :refer [environ]]
 '[{{name}}.system        :refer [dev-system]])

(task-options!
  pom {:project      '{{name}}
       :version      "0.1.0-SNAPSHOT"
       :description  "FIXME: Description"
       :license      {:name "GPLv3" :url "https://www.gnu.org/licenses/gpl.html"}}

  jar {:main     '{{name}}.system
       :manifest {"Description"  "FIXME: Description"
                  "Url"          "http://github.com/yourname/{{name}}"}}

  cljs {:ids #{"application"}
        :optimizations :none :source-map true}

  less {:source-map true}
  sass {:source-map true})


(deftask dev
  [l with-less          bool "Use LESS instead of SASS."
   p port        PORT   int  "Port to run the web server on."]
  (let [port_ (or port 4000)]
    (set-env! :source-paths #(conj % "src/js/dev"))
    (environ :env {:http-port (str port_)})

    (comp
     (cider)
     (watch)
     (if with-less
       (less)
       (sass))
     (system :sys #'dev-system :auto true :files ["handler.clj" "system.clj"])
     (cljs-repl-env :ws-host "localhost" :ids #{"application"})
     (cljs)
     (repl :server true)
     (target))))


(deftask prod
  "Setup the prod environment."
  []
  (environ :env {:http-port "4000"})
  identity)


(deftask testing []
  (set-env! :source-paths #(conj % "test/cljs"))
  identity)
