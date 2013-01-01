(ns personal.views.styles.main
  (:require [clj-style.core :as cs]))

(cs/defrule header
  [:header
   :height :48px
   :width "100%"
   :font-family "NeoRetroDrawRegular"
   :color "#333"
   :font-size :2em
   :margin :0
   :border-bottom "2px #333 solid"
   [:h1
    :text-align :center]])

(cs/defrule footer
  [:footer
   :height :48px
   :width "100%"
   :font-family "NeoRetroDrawRegular"
   :border-top "2px #333 solid"
   [:ul
    :margin "0 auto"
    :float :left
    :list-style-type :none
    [:li
     :width :160px
     :display :block
     :float :left
     :text-align :center
     [:a
      :color "#333"
      :text-decoration :none]
     [:a:hover
      :font-family "NeoRetroFillRegular"]]]])


(cs/defrule wrapper
  [:#wrapper
   :width :480px
   :height "100%"
   :min-height :960px
   :font-family "CardoRegular"
   :font-size :1em
   :margin "0 auto"
   [:#pagination
    :width :480px
    :list-style-type :none
    :float :left

;;  [:li#cur
;;     :margin "0 auto"]
    [:li#next
     :float :right]]])

(cs/defrule posts
  [:#posts
   :width "95%"
   :margin "0 auto"
   [:li
    :list-style-type :none 
    :height :96px
    :width "100%"
    :padding :2px
    :margin-bottom :4em
    [:.url
     :float :left
     [:a 
      :color :black
      :text-decoration :none
      :border-bottom "1px dashed black"
      ]
     [:a:hover 
      :padding :0
      :border-bottom :none]]
    [:.created_at
     :color :black
     :float :right]
    [:.description
     :clear :both
     :text-align :justify
     :color :black]]
   [:.break
    :clear :both
    :margin "0px auto"
    :margin-top :1em
    :height :30px
    :width  :30px
    :background-image "url('/img/break.svg')"
    :background-repeat :no-repeat
    :background-size "100%"]])

(defn generate-url-line [prefix extension fmt]
  (cond
   (= fmt "") (format "url('/fonts/%s-webfont.%s')" prefix extension)
   :else (format "url('/fonts/%s-webfont.%s') format('%s')" prefix extension fmt)))

(cs/defmixin font-face [name prefix weight style]
  :font-family name
  :src (generate-url-line prefix "eot" "")
  :src (apply str
              (interpose ",\n"
                         (list (generate-url-line prefix "eot?#iefix" "embedded-opentype")
                               (generate-url-line prefix "woff" "woff")
                               (generate-url-line prefix "ttf" "truetype")
                               (generate-url-line prefix (format "svg#%s" name) "svg")))) 
  :font-weight weight
  :font-style style)

(cs/defrule cardo-regular
  ["@font-face" (font-face "CardoRegular" "Cardo104s" "normal" "normal")])
(cs/defrule cardo-italic
  ["@font-face" (font-face "CardoItalic"  "Cardoi99"  "normal" "normal")])
(cs/defrule cardo-bold
  ["@font-face" (font-face "CardoBold"    "Cardo101"  "normal" "normal")])

(cs/defrule neord-regular
  ["@font-face" (font-face "NeoRetroDrawRegular" "NEORD___" "normal" "normal")])
(cs/defrule neorf-regular
  ["@font-face" (font-face "NeoRetroFillRegular"  "NEORF___"  "normal" "normal")])
(cs/defrule neors-regular
  ["@font-face" (font-face "NeoRetroShadowRegular" "NEORS___" "normal" "normal")])

(clojure.java.io/delete-file (clojure.java.io/as-file "resources/public/css/main.css"))
(cs/save "resources/public/css/main.css")
