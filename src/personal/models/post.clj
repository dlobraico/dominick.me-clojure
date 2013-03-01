(ns personal.models.post
  (:use [personal.db]
        [korma.db]
        [korma.core]))

(defn generate_offset_limit [page_number num_per_page]
  (let [start (* page_number num_per_page)]
    [start (+ start 10)]))

(defn total-posts []
  (:cnt (first
         (select posts
                 (fields (raw "COUNT(*) cnt"))
                 (where (= :published 1))))))

(defn select-published [page] 
  (let [[off lim] (generate_offset_limit (- page 1) 10)]
    (select posts
            (fields :title :url :description :created_at)
            (order :created_at :desc)
            (where (= :published 1))
            (offset off)
            (limit lim))))

(defn insert-post [title url description published]
  (insert posts
          (values {:title title
                   :url url
                   :description description
                   :published published})))

(defn publish-post [id]
  (update posts
          (set-fields {:published true})
          (where {:id (= id)})))
