(ns simprob.redyellow)

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

(defn listener [i chosen n-occured n-all p]
  (let [[choice accepted] chosen]
    (printf "%5d %s %1s %5d %5d   %4.3f\n" 
            (inc i)
            (choice-s choice)
            (if (not (nil? accepted)) accepted "")
            n-occured
            n-all
            p)))
