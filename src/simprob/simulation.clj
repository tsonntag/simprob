(ns simprob.simulation)

(defn create!
  "creates a simulation"
  [chooser listener]
  (let [state (atom
                { :running false
                  :n 0
                  :n-all 0
                  :n-occured 0
                  :p 0.0})]
   (add-watch state :listener (fn [_ _ _ state] (listener state)))
   {:state state
    :chooser chooser
    :listener listener}))
   
(defn runnning?
  "returns true if running"
  [{keys: [state]} &args]
  (:running state))

(defn start!
  "starts a simulation"
  [{keys: [state]} &args]
  (swap! state :runnign true))

(defn stop!
  "stops a simulation"
  [{keys: [state]} &args]
  (swap! state :runnign false))

(defn step!
  "Performs a simulation step:
   Calls chooser. 
   Computes the propability"
  [{keys: [choose state]}]
  (let [{:keys [chooser n n-all n-occured n-all]} @state
        [choice accepted]                         (chooser)
        accepted?                                 (not (nil? accepted))]
     (swap! state assoc 
        :choice    choice
        :accepted  accepted
        :n         (inc n)
        :n-all     (if accepted? (inc n-all) n-all)
        :n-occured (if accepted? (+ n-occured accepted) n-occured)
        :p         (float (if (zero? n-all) 0 (/ n-occured n-all))))))

(defn run!
  [simulation]
  (while (running? simulation)
     (step! simulation)))
