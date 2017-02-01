(def project 'hellhound/boot-template)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          ;; uncomment this if you write tests for your template:
          ;; :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]
                            [boot/new "RELEASE"]
                            [adzerk/boot-test "RELEASE" :scope "test"]])

(task-options!
 pom {:project     project
      :version     version
      :description "HellHound 'boot new' template."
      :url         "http://github.com/Cidamic/hellhound-template"
      :scm         {:url "https://github.com/Codamic/hellhound-template"}
      :license     {"GPLv2"
                    "https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html"}})

(deftask build
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))


(require '[adzerk.boot-test :refer [test]]
         '[boot.new :refer [new]])
