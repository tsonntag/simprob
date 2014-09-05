(ns simprob.core
  (:gen-class))

(defn run [runs chooser listener]
  "calls chooser i times. computes the propability
   and calls (listener i n choice p) for each run"
  (loop [i         0
         n-occured 0
         n-all     0]
     (when (< i runs)
       (let [chosen       (chooser)
             [_ accepted] chosen
             accepted?    (not (nil? accepted))
             n-all        (if accepted? (inc n-all) n-all) 
             n-occured    (if accepted? (+ n-occured accepted) n-occured)
             p            (float (if (zero? n-all) 0 (/ n-occured n-all)))]
          (listener i chosen n-occured n-all p)
          (recur (inc i) n-occured n-all)))))
