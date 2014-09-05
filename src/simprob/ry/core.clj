(ns simprob.ry.core
  (require [simprob.core :as simprob]))

(def pads [[:red :red] [:red :yellow] [:yellow :yellow]])

(def color :yellow)

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

(defn choice-s [choice]
  (let [s (show choice)]
    (format "[%-7s %-7s]" (first s) (last s))))

(defn choose 
  ([]
  "select a pad and on of its tops randomly. returns [pad top]"
  (let [pad     (pads (rand-int 3))
        top (rand-int 2)]
    [pad top]))
  ([n]
   (repeatedly n choose)))

(defn chooser 
  "if top of choice is not color accepted is nil,
   else if bottom of choice is color accepted is 1 else 0
   returns [choice accepted]"
  []
  (let [choice   (choose)
        accepted (when (= (top choice) color)
                   (if (= (bottom choice) color) 1 0))]
     [choice accepted]))

(defn line [& args]
  (apply printf "%7d %-20s %-8s %7d %7d  %4.3f\n" args))

(defn header [& args]
  (apply printf "%7s %-20s %-8s %7s %7s  %4s\n" args))

(defn listener [n chosen n-occured n-all p]
  (let [[choice accepted] chosen]
    (line
      n 
      (choice-s choice)
      (if (not (nil? accepted)) 
        (= accepted 0)
        "")
      n-occured
      n-all
      p)))

(defn run
  "runs red-yellow n times"
  [n]
  (header "run" "" "accepted" "true" "all" "p")
  (simprob/run #(>= % n) chooser listener))

