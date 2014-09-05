(ns simprob
  (:gen-class))

(defn run [runs chooser listener]
  "calls chooser n-times. computes the propability
   and calls (listener i choice p) for each run"
  (loop [i 0
         n 0
         p 0]
     (when (< i runs)
       (let [chosen       (chooser)
             [_ accepted] chosen
             accepted?    (not (nil? accepted))
             n            (if accepted? (inc n) n) 
             p            (if accepted?
                            (/ (+ (* (dec n) p) accepted) n)
                            p)]
          (listener i n chosen p)
          (recur (inc i) n p)))))
