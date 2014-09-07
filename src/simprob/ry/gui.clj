(ns simprob.ry.gui
  (require
    [seesaw.core :refer :all]
    [seesaw.font :refer :all]
    [simprob.simulation :as simulation]
    [simprob.gui :as gui]
    [simprob.ry.core :as ry])
  (gen-class))

(defn run []
  (let [bg        (config (label) :background)
        font      (font :size 18)
        left      (label :font font :text "        " :background bg :bounds [:* :* 100 200])
        right     (label :font font :text "        " :background bg :bounds [:* :* 100 200])
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
        title      "Das Sockenexperiment"
        app        (gui/create 
                     simulation title [pad])]
    (gui/show app)))

(defn -main [& args]
  (run))
