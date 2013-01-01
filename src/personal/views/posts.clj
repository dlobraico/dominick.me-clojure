(ns personal.views.posts
  (:require [personal.views.common :as common])
  (:require [personal.views.db :as db])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [link-to]]
        [clj-time.core :exclude(extend)]
        [clj-time.format] 
        [clojure.core.match :only (match)]))

(defn relative-date [sql-date] 
  (def cutoff-date (minus (now) (weeks 1)))
  (def sql-fmt (formatters :date-hour-minute-second))
  (def str-fmt (formatter "H:MM a, MMMM d, YYYY"))
  (def d   (parse sql-fmt sql-date))
  (def intv (interval d (now)))
  (if (within? (interval cutoff-date (now)) d)
    (let [xdays (double (/ (in-minutes intv) 60 24)) 
          xhours (double (/ (-
                             (in-minutes intv)
                             (* (int xdays) 60 24))
                            60))]
      (match [xdays xhours]
        [0 0] "now"
        [0 _] (format "%.0f days ago" xdays)
        [_ 0] (format "%.0f hours ago" xhours)
        :else (format "%.0f days and %.0f hours ago" xdays xhours)))
    (unparse str-fmt d)))
    
(defpartial post-item [{:keys [title url description created_at published]}]
  [:li 
   [:div.url [:h2 (link-to url title)]]
   [:div.created_at (relative-date created_at)]
   [:div.description description]
   [:div.break]])

(defpartial pagination [cur-page]
  (let [prev (link-to (str "/page/" (- cur-page 1)) "<< Previous")
        next (link-to (str "/page/" (+ cur-page 1)) "Next >>")
        total (db/total-posts)]
    [:ul#pagination
     [:li#prev (if (> cur-page 1) prev)]
     ;; [:li#cur  cur-page]
     [:li#next (if (> (- total (* 10 cur-page)) 0) next)]
     ]))

(defpartial posts-list [page]
  [:ul#posts
   (map post-item (db/select-published page))]
  (pagination page))


(defpage "/" []
    (common/layout
     (posts-list 1)))

(defpage "/page/:num" {:keys [num]}
  (let [page (Integer/parseInt num)]
    (common/layout
     (posts-list page))))
