(ns personal.views.admin
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers)
  (:require [noir.session :as session]
            [noir.validation :as vali]
            [noir.response :as resp]
            [clojure.string :as string]
            [personal.models.post :as posts]
            [personal.models.user :as users]
            [personal.views.common :as common]))

(def post-actions [{:url "/admin/post/add" :text "Add a post"}])

(defpartial error-text [errors]
  [:span (string/join "" errors)])

(defpartial post-fields [{:keys [title body]}]
  (vali/on-error :title error-text)
  (text-field {:placeholder "Title"} :title title)
  (vali/on-error :body error-text)
  (text-area {:placeholder "Body"} :body body))

(defpartial user-fields [{:keys [username] :as usr}]
  (vali/on-error :username error-text)
  (text-field {:placeholder "Username"} :username username)
  (vali/on-error :password error-text)
  (password-field {:placeholder "Password"} :password))

(defpartial post-item [{:keys [title] :as post}]
  [:li
   (link-to (posts/edit-url post) title)])

(defpartial action-item [{:keys [url text]}]
  [:li
   (link-to url text)])

(pre-route "/admin*" []
           (when-not (users/admin?)
             (resp/redirect "/admin/login")))

(defpage "/admin/login" {:as user}
  (if (users/admin?)
    (resp/redirect "/admin")
    (common/main-layout
     (form-to [:post "/login"]
              [:ul.actions
               [:li (link-to {:class "submit"} "/" "Login")]]
              (user-fields user)
              (submit-button {:class "submit"} "submit")))))

(defpage [:post "/login"] {:as user}
  (if (users/login! user)
    (resp/redirect "/admin")
    (render "/login" user)))

(defpage "/logout" {}
  (session/clear!)
  (resp/redirect "/"))

(defpage "/admin" {}
  (common/admin-layout
   [:ul.actions
    (map action-item post-actions)]
   [:ul.items
    (map post-item (posts/get-latest))]))

(defpage "/admin/post/add" {:as post}
  (common/admin-layout
   (form-to [:post "/admin/post/add"]
            [:ul.actions
             [:li (link-to {:class "submit"} "/" "Add")]]
            (post-fields post)
            (submit-button {:class "submit"} "add post"))))

(defpage [:post "/admin/post/add"] {:as post}
  (if (posts/add! post)
    (resp/redirect "/admin")
    (render "/admin/post/add" post)))

(defpage "/admin/post/edit/:id" {:keys [id]}
  (if-let [post (posts/id->post id)]
    (common/admin-layout
     (form-to [:post (str "/admin/post/edit" id)]
              [:ul.actions
               [:li (link-to {:class "submit"} "/" "Submit")]
               [:li (link-to {:class "delete"} (str "/admin/post/remove/" id) "Remove")]]
              (post-fields post)
              (submit-button {:class "submit"} "submit")))))

(defpage [:post "/admin/post/edit/:id"] {:keys [id] :as post}
  (if (posts/edit! post)
    (resp/redirect "/admin")
    (render "/admin/post/edit/:id" post)))

(defpage "/admin/post/remove/:id" {:keys [id]}
  (posts/remove! id)
  (resp/redirect "/admin"))