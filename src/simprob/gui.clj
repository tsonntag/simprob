(ns simprob.gui (require
    [simprob.simulation :as simulation]
    [seesaw.core :refer :all]
    [seesaw.font :refer :all]
  ))
 
(defn- make-button [text action] 
   (let [b (button :text text)]
       (listen b :action action)
       b))

(defn create
  "creates app for simulation with items (widgets)"
  [simulation title items]
  (let [state        (:state simulation)
        f            (frame :title title :on-close :exit)
        init-btn     (make-button "Neu" (fn [_] (simulation/init!   simulation)))
        step-btn     (make-button "Zug"   (fn [_] (simulation/step!   simulation)))
        run-btn      (make-button "Start" (fn [_] (simulation/toggle! simulation)))
        lbl          (fn [text] (label :text text :font (font :size 18)))
        labels       {:n (lbl "") :n-all (lbl "") :n-occured (lbl "") :p (lbl "")}
        result-panel (grid-panel :columns 2 :vgap 5 :hgap 2
                                 :items [(lbl "Zug:  ")             (:n         labels)
                                         (lbl "Gelb oben:  ")       (:n-all     labels)
                                         (lbl "Gelb auch unten:  ") (:n-occured labels)
                                         (lbl "rel. Häufigkeit:  ") (:p         labels)])
        main-panel (border-panel :vgap 5 :hgap 5 :border 5
                                 :west (flow-panel :vgap 5 :hgap 5 :border 5 :items items)
                                 :east (vertical-panel
                                         :items [(flow-panel :items [run-btn step-btn init-btn])
                                                 result-panel]))
        ]
        (add-watch
          state :widget
          (fn [_ _ _ state]
            (doseq [[key label] labels]
              (config! label  :text (key state)))
            (config! init-btn :enabled? (not (:running state)))
            (config! step-btn :enabled? (not (:running state)))
            (config! run-btn  :text     (if (:running state) "Stop" "Start"))))
        (config! f :content main-panel)
        {:frame f :simulation simulation}))

(defn show [app] (-> (:frame app) pack! show!))
