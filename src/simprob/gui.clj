(ns simprob.gui (require
    [simprob.core :as core]
    [seesaw.core :refer :all]))
 
(def f (frame :title "Title" :on-close :exit))
 
(defn show [] (-> f pack! show!))

(defn make-button [text f] 
   (let [b (button :text text)]
       (listen b :action f)
       b))

(def run-button   (make-button "Start"(fn [e] (swap! core/running? #(not %)))))
(def step-button  (make-button "Step" (fn [e] #(core/next-state!))))

(def labels {:n         (label)
             :n-all     (label)
             :n-occured (label)
             :p         (label)})

(add-watch running? :widget (fn [_ _ _ value]
                              (config! step-button :enabled? (not value))
                              (config! run-button  :text     (if value "Stop" "Start"))))

(add-watch state :widget
           (fn [_ _ _ state]
             (doseq [[key label] labels]
               (config! label :text (key state)))))

(def result-panel
  (grid-panel
    :rows 4
    :columns 2
    :items [(label "Step:")  (:n         labels)
            (label "Event")  (:n-occured labels)
            (label "Valid:") (:n-all     labels)
            (label "p:")     (:p         labels)]))


(defn display [content]
   (config! f :content content)
   content)

(def board (flow-panel :hgap 5 :vgap 5))

(defn board! [& items] (config! board :items items))

(display
  (border-panel
    :vgap 5
    :hgap 5
    :border 5
    :west board 
    :east (border-panel
            :vgap 5
            :hgap 5
            :border 5
            :north (flow-panel :items [step-button run-button])
            :center result-panel)))
