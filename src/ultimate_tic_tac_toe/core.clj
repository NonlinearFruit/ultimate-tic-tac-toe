(ns ultimate-tic-tac-toe.core)

(def monte-carlo-strength 5)
(def empty-board (vec (take 9 (repeat nil))))
(def empty-multi-board (vec (take 9 (repeat empty-board))))
(def x-board ["X   X"
              " X X "
              "  X  "
              " X X "
              "X   X"])
(def o-board [" OOO "
              "O   O"
              "O   O"
              "O   O"
              " OOO "])
(def c-board [" CCCC"
              "C    "
              "C    "
              "C    "
              " CCCC"])

(def lines (apply concat (vals {:rows [[0 1 2] [3 4 5] [6 7 8]]
                                :columns [[0 3 6] [1 4 7] [2 5 8]]
                                :diagonals [[2 4 6] [0 4 8]]})))

(defn opponent [symbol]
  (if (= :x symbol) :o :x))

(defn has-player-won? [symbol board]
  (some? (some
           (fn [line] (every? #(= symbol %1) (map board line)))
           lines)))

(defn has-cat-won? [board]
  (not (some? (some nil? board))))

(defn is-complete? [board]
  (or
    (has-player-won? :x board)
    (has-player-won? :o board)
    (has-cat-won? board)))

(defn has-player-won-the-multi-board? [symbol multiboard]
  (some? (some
           (fn [line] (every? #(has-player-won? symbol %1) (map multiboard line)))
           lines)))

(defn has-cat-won-the-multi-board? [multiboard]
  (every? is-complete? multiboard))

(defn stringify-board [board]
  (apply format
         "%s|%s|%s\n-+-+-\n%s|%s|%s\n-+-+-\n%s|%s|%s"
         (map #(if (some? %1) (name %1) " ") (flatten board))))

(defn split-into-layers [multiboard]
  (flatten
    (map #(cond
            (has-player-won? :x %1) x-board
            (has-player-won? :o %1) o-board
            (has-cat-won? %1) c-board
            :else (clojure.string/split (stringify-board %1) #"\n"))
         multiboard)))

(defn order-layers-for-inserting [layers]
  (map last
       (sort-by
         #(let [i (first %1)]
            (+
              (mod (* 3 i) 15)
              (mod (quot i 5) 3)
              (* (quot i 15) 15)))
         (map-indexed (fn [i e] [i e]) layers))))

(defn stringify-multi-board [multiboard]
  (apply format
         (clojure.string/replace
           (clojure.string/join "\n" ["         |         |         "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "         |         |         "
                                      "---------+---------+---------"
                                      "         |         |         "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "         |         |         "
                                      "---------+---------+---------"
                                      "         |         |         "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "  layer  |  layer  |  layer  "
                                      "         |         |         "])
           "layer", "%s")
         (order-layers-for-inserting (split-into-layers multiboard))))

(defn clear-screen []
  (print "\033[H\033[2J"))

(defn possible-board-choices [multiboard & [last-move]]
  (let [last-square-chosen (last (or last-move [nil]))]
    (filter
      #(and (not (is-complete? (multiboard %1)))
            (or (= last-square-chosen %1)
                (nil? last-square-chosen)
                (is-complete? (multiboard last-square-chosen))))
      (range 9))))

(defn possible-square-choices [board]
  (filter
    #(nil? (board %1))
    (range 9)))

(defn all-possible-moves [multiboard last-move]
  (apply concat
    (map (fn [board] (map #(do [board %1]) (possible-square-choices (multiboard board))))
      (possible-board-choices multiboard last-move))))

(defn read-choice []
  (try
    (parse-long (read-line))
    (catch Exception e
      nil)))

(defn prompt [msg options]
  (loop []
    (print msg "[" (clojure.string/join "," (map inc options)) "]: ") (flush)
    (let [choice (read-choice)]
      (cond (nil? choice) (recur)
            (some #(= (dec choice) %1) options) (dec choice)
            :else (recur)))))

(defn get-square-choice [board]
  (prompt "Square" (possible-square-choices board)))

(defn get-board-choice [multiboard & [last-move]]
  (prompt "Board" (possible-board-choices multiboard last-move)))

(defn human-player [multiboard last-move]
  (clear-screen)
  (println (stringify-multi-board multiboard))
  (and last-move (println "Opponents move:" (map inc last-move)))
  (let [board-choice (get-board-choice multiboard last-move)
        square-choice (get-square-choice (multiboard board-choice))]
    [board-choice square-choice]))

(defn random-bot [multiboard last-move]
  (rand-nth (all-possible-moves multiboard last-move)))

(defn play-the-game [x o & [multiboard last-move]]
  (loop [multiboard (or multiboard empty-multi-board)
         symbol :x
         last-move (or last-move nil)]
    (cond
      (has-player-won-the-multi-board? :x multiboard) {:winner :x :board multiboard}
      (has-player-won-the-multi-board? :o multiboard) {:winner :o :board multiboard}
      (has-cat-won-the-multi-board? multiboard) {:winner :c :board multiboard}
      :else (let [prompt-player (if (= :x symbol) x o)
                  next-move (prompt-player multiboard last-move)]
              (recur (assoc-in multiboard next-move symbol) (opponent symbol) next-move)))))

(defn simulate-next-move [multiboard next-move]
  ((play-the-game random-bot random-bot
                  (assoc-in multiboard next-move :o)
                  next-move)
   :winner))

; FLAW: Assumes it is playing as :o
; FLAW: Score only counts wins
(defn measure-next-move [multiboard next-move]
  {:move next-move
   :score (count (filter #(= :o %1)
                         (map (fn [_] (simulate-next-move multiboard next-move))
                              (range monte-carlo-strength))))})

(defn monte-carlo-bot [multiboard last-move]
  ((last (sort-by :score
                  (map #(measure-next-move multiboard %1)
                       (all-possible-moves multiboard last-move))))
   :move))

(defn announce-winner [result]
  (clear-screen)
  (println (stringify-multi-board (result :board)))
  (println (cond
             (= :x (result :winner)) "X has won!"
             (= :o (result :winner)) "O has won!"
             :else "Best 2 out of 3?")))

(defn title-screen []
  (clear-screen)
  (println
    (clojure.string/replace
      (clojure.string/join "\n" ["           _   _ _ _   _                 _                "
                                 "          | | | | | |_(_)_ __ ___   __ _| |_ ___          "
                                 "          | | | | | __| | '_ ` _ ? / _` | __/ _ ?         "
                                 "          | |_| | | |_| | | | | | | (_| | ||  __/         "
                                 "           ?___/|_|?__|_|_| |_| |_|?__,_|?__?___|         "
                                 "      _____ _        _____            _____               "
                                 "     |_   _(_) ___  |_   _|_ _  ___  |_   _|__   __       "
                                 "       | | | |/ __|   | |/ _` |/ __|   | |/ _ ? / _ ?     "
                                 "       | | | | (__    | | (_| | (__    | | (_) |  __/     "
                                 "       |_| |_|?___|   |_|?__,_|?___|   |_|?___/ ?___|     "
                                 "                                                          "
                                 "                 <<Press enter to start>>                 "])
      "?" "\\"))
  (read-line))

(defn -main [& args]
  (title-screen)
  (announce-winner (play-the-game human-player human-player)))

