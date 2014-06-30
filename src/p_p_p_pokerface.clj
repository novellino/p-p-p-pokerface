(ns p-p-p-pokerface)


(def replacements {\T 10, \J 11, \Q 12, \K 13, \A 14})

(defn rank [card]
  (let [[fst snd] card]
    (cond
     (Character/isDigit fst) (Integer/valueOf (str fst))
     :else (Integer/valueOf (str (replacements fst))))))

(defn suit [card]
  (let [[_ snd] card]
  (str snd)))

(defn pair? [hand]
  (cond
  (= (apply max
            (vals (frequencies
                   (map rank hand))))2) true
   :else false))

(defn three-of-a-kind? [hand]
  (cond
  (= (apply max
            (vals (frequencies
                   (map rank hand))))3) true
   :else false))

(defn four-of-a-kind? [hand]
  (cond
  (= (apply max
            (vals (frequencies
                   (map rank hand))))4) true
   :else false))

(defn flush? [hand]
  (cond
  (= (apply max
            (vals (frequencies
                   (map suit hand))))5) true
  :else false))

(defn full-house? [hand]
  (cond
  (= (sort
            (vals (frequencies
                   (map rank hand))))
           (range 2 (+ 2 2))) true
   :else false))

(defn two-pairs? [hand]
  (cond
  (= (sort
            (vals (frequencies
                   (map rank hand))))
            [1 2 2] ) true
   (= (sort
            (vals (frequencies
                   (map rank hand))))
            [1 4] ) true
   :else false))

(defn straight? [hand]
   (cond
   (= (sort
            (map rank hand))
      (range 2 (+ 2 5))) true
    (= (sort
        (vals (frequencies
            (map rank hand))))
      (take 5(cycle [1]))) true
   :else false))

(defn straight-flush? [hand]
  (cond
   (and (straight? hand) (flush? hand)) true
   :else false))

(defn high-card? [hand]
  true)


(defn value [hand]
  (let [checkers #{[high-card? 0]  [pair? 1]
                 [two-pairs? 2]  [three-of-a-kind? 3]
                 [straight? 4]   [flush? 5]
                 [full-house? 6] [four-of-a-kind? 7]
                 [straight-flush? 8]}
        filtered (filter (fn [check] ((first check ) hand ))checkers)]
   ;(filter (fn[x] map checkers x)hand)
    (apply max (map second filtered))
  ))

;http://java.dzone.com/articles/clojure-handling-state
