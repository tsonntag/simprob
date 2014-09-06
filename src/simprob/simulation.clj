(ns simprob.simulation)

(defn create
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
   
(defn running?
  "returns true if running"
  [{:keys [state]} &args]
  (:running state))

(defn start!
  "starts a simulation"
  [{:keys [state]} &args]
  (swap! state :runnign true))

(defn stop!
  "stops a simulation"
  [{:keys [state]} &args]
  (swap! state :runnign false))

(defn step!
  "Performs a simulation step:
   Calls chooser. 
   Computes the propability"
  [{:keys [chooser state]}]
  (let [{:keys [n n-all n-occured n-all]} @state
        [choice accepted]                 (chooser) 
        accepted? (not (nil? accepted))
        n         (inc n)
        n-all     (if accepted? (inc n-all) n-all)
        n-occured (if accepted? (+ n-occured accepted) n-occured)
        p         (float (if (zero? n-all) 0 (/ n-occured n-all)))]
     (swap! state assoc 
        :choice    choice
        :accepted  accepted
        :n         n
        :n-all     n-all
        :n-occured n-occured
        :p         p)))

(defn run!
  [simulation]
  (while (running? simulation)
     (step! simulation)))
