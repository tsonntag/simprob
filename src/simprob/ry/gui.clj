(ns simprob.ry.gui
  (require
    [seesaw.core :refer :all]
    [simprob.simulation :as simulation]
    [simprob.gui :as gui]
    [simprob.ry.core :as ry]))

(defn run []
  (let [label      (label "")
        listener   (fn [{:keys [choice] :as state}]
                     (config! label :text
                              (if (not (nil? choice))
                                (str (ry/top choice) " " (ry/bottom choice))
                                "")))
        simulation (simulation/create
                     (ry/chooser :yellow)
                     listener)
        title      "Rot Gelb"
        app        (gui/create 
                     simulation title [label])]
    (gui/show app)))
