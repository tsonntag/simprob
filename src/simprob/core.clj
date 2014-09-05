(ns simprob.core
  (:gen-class))

(defn next-state 
  "Calls chooser. 
   Computes the propability and returns next state"
  [chooser {:keys [n n-all n-occured n-all]}]
  (let [[choice accepted] (chooser)
        accepted?         (not (nil? accepted))]
    {:choice    choice
     :accepted  accepted
     :n         (inc n)
     :n-all     (if accepted? (inc n-all) n-all) 
     :n-occured (if accepted? (+ n-occured accepted) n-occured)
     :p         (float (if (zero? n-all) 0 (/ n-occured n-all)))
     }))

(def initial-state {:n 0, :n-all 0, :n-occured 0, :p 0.0})

(defn run [stopped? chooser listener]
  "Calls repeatly chooser. 
   Computes the propability
   and calls (listener state) for each run.
   Stops if (stopped? state) returns true"
  (loop [state initial-state]
     (when (not (stopped? state))
        (let [state (next-state chooser state)]
          (listener state)
          (recur state)))))
