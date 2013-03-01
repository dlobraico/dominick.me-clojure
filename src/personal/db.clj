(ns personal.db
  "database queries"
  (:use [korma.db]
        [korma.core]
        [clojure.core.match :only (match)]))

(defdb mydb {:classname "org.sqlite.JDBC"
             :subprotocol "sqlite"
             :subname "db/personal.db"})

(defentity posts
  (pk :id)
  (table :posts)
  (entity-fields :title :url :description :created_at :published)
  (database mydb))

(defentity users
  (pk :id)
  (table :users)
  (entity-fields :username :password :admin)
  (database mydb))

(defn get [entity]
  (match entity
         :posts (select posts
                        (order :created_at :desc))
         :users (select users
                        (order :id :asc))))