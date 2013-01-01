(defproject personal "0.1.0-SNAPSHOT"
            :description "My linkblog and personal site"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [org.clojure/core.match "0.2.0-alpha11"] 
                           [noir "1.2.1"]
                           [org.clojure/java.jdbc "0.1.1"] 
                           [org.xerial/sqlite-jdbc "3.7.2"]
			   [korma "0.3.0-beta7"]
                           [clj-style "1.0.1"]
			   [clj-time "0.4.4"]
                           ;;[twitter-api "0.6.13"]
                           ]
            :main personal.server)
