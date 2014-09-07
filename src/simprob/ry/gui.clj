(ns simprob.ry.gui
  (require
    [seesaw.core :refer :all]
    [simprob.simulation :as simulation]
    [simprob.gui :as gui]
    [simprob.ry.core :as ry]))

(defn run []
  (let [bg        (config (label) :background)
        left      (label :text "        " :background bg :bounds [:* :* 10 20])
        right     (label :text "        " :background bg :bounds [:* :* 10 20])
        pad       (vertical-panel :items [left right])
        listener   (fn [{:keys [choice] :as state}]
                     #_(config! label :text
                              (if (not (nil? choice))
                                (str (ry/top choice) " " (ry/bottom choice))
                                ""))
                     (if (not (nil? choice))
                       (do
                         (config! left  :background (ry/top    choice))
                         (config! right :background (ry/bottom choice)))
                       (do 
                         (config! left  :background bg)
                         (config! right :background bg)))
                     )
        simulation (simulation/create
                     (ry/chooser :yellow)
                     listener)
        title      "Rot Gelb"
        app        (gui/create 
                     simulation title [pad])]
    (gui/show app)))
