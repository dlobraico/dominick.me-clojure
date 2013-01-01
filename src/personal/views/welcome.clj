(ns personal.views.welcome
  (:require [personal.views.common :as common])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to personal"]))

(defpage "/my-page" []
  (common/layout
   [:h1 "This is my first page!"]
   [:p "Hope you like it."]))
