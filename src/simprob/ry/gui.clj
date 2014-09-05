(ns simprob.ry.gui
  (require
    [seesaw.core :refer :all]
    [simprob.core :as simprob]
    [simprob.ry.core :as ry]
    [simprob.ry.gui :as gui])

:wq

(simprob/run #(>= % n) chooser console-listener))
