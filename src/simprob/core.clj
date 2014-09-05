(ns simprob.core
  (:gen-class))

(def initial-state {:n 0, :n-all 0, :n-occured 0, :p 0.0})
(def running?      (atom false))
(def state         (atom initial-state))
(defn init! [_chooser listener]
  (reset! running? false)
  (reset! state    core/initial-state)
  (reset! chooser _chooser)
  (add-watch state :listener (fn [_ _ _ state] (listener state))))

(def chooser (atom nil))

(defn next-state!
  "Calls chooser. 
   Computes the propability and returns next state"
  []
  (let [{:keys [n n-all n-occured n-all]} @state
        [choice accepted] (@chooser)
        accepted?         (not (nil? accepted))
        next-state        {:choice    choice
                           :accepted  accepted
                           :n         (inc n)
                           :n-all     (if accepted? (inc n-all) n-all)
                           :n-occured (if accepted? (+ n-occured accepted) n-occured)
                           :p         (float (if (zero? n-all) 0 (/ n-occured n-all)))}]
     (reset! state new-state)
     ))

(defn run! []
  (while @running?
     (next-state!)))
