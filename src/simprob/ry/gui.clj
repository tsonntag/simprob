(ns simprob.ry.gui
  (require
    [seesaw.core :refer :all]
    [simprob.core :as simprob]
    [simprob.gui :as gui]
    [simprob.ry.core :as ry]))

(def _choice (atom []))

(def board-label (label ""))

(gui/board! board-label)

(gui/watch-text _choice board-label
               (fn [choice]
                (str (ry/top choice) " " (ry/bottom choice))))

(core/init!
  (ry/chooser :yellow)
  (fn [{:keys [n choice n-occured n-all p] :as state}]
   ;(println "listener:" state)
   (reset! _choice       choice)
   (reset! gui/n         n)
   (reset! gui/n-occured n-occured)
   (reset! gui/n-all     n-all)
   (reset! gui/p         p)))

(gui/show)
