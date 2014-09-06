(ns simprob.ry.core
  (require [simprob.simulation :as simulation]))

(def pads [[:red :red] [:red :yellow] [:yellow :yellow]])

(defn top
  "returns top of choice"
  [[pad i-top]]
   (pad i-top))

(defn bottom
  "returns bottom of choice"
  [[pad i-top]]
  (let [invert #(if (= % 1) 0 1)]
    (pad (invert i-top))))

(defn show
  "returns [top of choice, bottom of choice]"
  [choice]
  [(top choice) (bottom choice)])

(defn choice-s
  "returns choice as string"
  [choice]
  (let [s (show choice)]
    (format "[%-7s %-7s]" (first s) (last s))))

(defn choose 
  []
  "select a pad and the index of the surface randomly. returns [pad i-top]"
  (let [pad   (pads (rand-int 3))
        i-top (rand-int 2)]
    [pad i-top]))

(defn chooser 
  "Returns a chooser for color.
   If top of choice is not color accepted is nil,
   else if bottom of choice is color accepted is 1 else 0
   returns [choice accepted]"
  [color]
  (fn []
   (let [choice   (choose)
         accepted (when (= (top choice) color)
                    (if (= (bottom choice) color) 1 0))]
      [choice accepted])))

(defn line [{:keys [n n-all n-occured p choice accepted] :as state}]
  (printf "%7d %-20s %-8s %7d %7d  %10.9f\n" 
     n 
     (choice-s choice)
     (if (not (nil? accepted)) 
        (= accepted 1)
        "")
     n-occured
     n-all
     p ))

(defn header [& args]
  (apply printf "%7s %-20s %-8s %7s %7s  %4s\n" args))

(defn run
  "runs red-yellow n times on console"
  [n]
  (let [listener
         (fn [state]
    ;      (println "console-listener" state)
           (line state))
         simulation (simulation/create (chooser :yellow) listener)]
    ;(println simulation)
    (dotimes [i n]  
      (simulation/step! simulation))))


