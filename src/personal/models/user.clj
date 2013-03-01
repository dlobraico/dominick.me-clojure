(ns personal.models.user
  (:require [noir.util.crypt :as crypt]
            [noir.validation :as vali]
            [noir.session :as session]
            [personal.db :as db])
  (:use 
        [korma.db]
        [korma.core]))

(defn all []
  (vals (db/get :users)))

(defn get-username [username]
  (db/get-in :users [username]))

(defn admin? []
  (session/get :admin))

(defn me []
  (session/get :username))