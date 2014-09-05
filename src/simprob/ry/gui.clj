(ns simprob.ry.gui
  (require
    [seesaw.core :refer :all]
    [simprob.ry.core :as ry]))

(defn watch-text [atom widget f]
  (add-watch atom :widget (fn [_ _ _ state] (config! widget :text (f state)))))

(def f (frame
         :title "Title"
         :on-close :exit))

(defn show [] (-> f pack! show!))

(def running?  (atom false))
(def n         (atom 0))
(def n-all     (atom 0))
(def n-occured (atom 0))

(def run-button
  (let [b (button)]
    (listen b :action (fn [e] (swap! running? #(not %))))
    b))
(def n-label         (label))
(def n-all-label     (label))
(def n-occured-label (label))

(watch-text running?  run-button      #(if % "Stop" "Start"))
(watch-text n         n-label         identity)
(watch-text n-all     n-all-label     identity)
(watch-text n-occured n-occured-label identity)

(reset! running?  false)
(reset! n         0)
(reset! n-all     0)
(reset! n-occured 0)

(def result-panel
  (grid-panel
    :rows 3
    :columns 2
    :items [(label "Zuge:")       n-label
            (label "Ereignisse:") n-occured-label
            (label "Von:")        n-all-label]))


(def left-panel "left")

(defn display [content]
  (config! f :content content)
  content)

(display 
  (border-panel 
    :vgap 5
    :hgap 5
    :border 5
    :west left-panel
    :east (border-panel
            :vgap 5
            :hgap 5
            :border 5
            :north run-button
            :center result-panel)))

#_(defn -main [& args])

