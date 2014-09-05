(ns simprob.core
  (:gen-class))

(defn run [stopped? chooser listener]
  "Calls repeatly chooser. 
   Computes the propability
   and calls (listener i n choice p) for each run.
   Stops if (stopped? n) returns true"
  (loop [n         0
         n-occured 0
         n-all     0]
     (when (not (stopped? n))
       (let [chosen       (chooser)
             [_ accepted] chosen
             accepted?    (not (nil? accepted))
             n-all        (if accepted? (inc n-all) n-all) 
             n-occured    (if accepted? (+ n-occured accepted) n-occured)
             p            (float (if (zero? n-all) 0 (/ n-occured n-all)))]
          (listener (inc n) chosen n-occured n-all p)
          (recur (inc n) n-occured n-all)))))
