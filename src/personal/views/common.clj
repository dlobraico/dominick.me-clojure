(ns personal.views.common
  (:require [personal.views.styles.main :as main])
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5 link-to]]))

(defpartial header []
  [:header
   [:h1 "Dominick LoBraico"]])

(defpartial footer []
  [:footer
   [:ul
    [:li (link-to "/about" "About")]
    [:li (link-to "http://twitter.com/pygatea" "Twitter")]
    [:li (link-to "/contact" "Contact")]]])

(defpartial layout [& content]
  (html5
   [:head
    [:title "personal"]
    (include-css "/css/reset.css")
    (include-css "/css/main.css")]
   [:body
    [:div#wrapper
     (header)
     content
     (footer)]]))
