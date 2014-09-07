(ns simprob.gui (require
    [simprob.simulation :as simulation]
    [seesaw.core :refer :all]))
 
(defn- make-button [text action] 
   (let [b (button :text text)]
       (listen b :action action)
       b))

(defn create
  "creates app for simulation with items (widgets)"
  [simulation title items]
  (let [state        (:state simulation)
        f            (frame :title title :on-close :exit)
        init-btn     (make-button "Reset" (fn [_] (simulation/init!   simulation)))
        step-btn     (make-button "Step"  (fn [_] (simulation/step!   simulation)))
        run-btn      (make-button "Start" (fn [_] (simulation/toggle! simulation)))
        labels       {:n (label) :n-all (label) :n-occured (label) :p (label)}
        result-panel (grid-panel :rows 4 :columns 2
                                 :items [(label "Step:")  (:n         labels)
                                         (label "Event")  (:n-occured labels)
                                         (label "Valid:") (:n-all     labels)
                                         (label "p:")     (:p         labels)])
        main-panel (border-panel :vgap 5 :hgap 5 :border 5
                                 :west (flow-panel   :vgap 5 :hgap 5 :border 5 :items items)
                                 :east (border-panel :vgap 5 :hgap 5 :border 5
                                                     :north (flow-panel
                                                              :items [step-btn run-btn init-btn])
                                                     :center result-panel))
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

(defn run [{:keys [simulation]}]
  (future (simulation/run! simulation)))
  ;(simulation/run! simulation))
