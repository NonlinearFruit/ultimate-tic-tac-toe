(ns ultimate-tic-tac-toe.core)

(def empty-board (vec (take 9 (repeat nil))))
(def empty-multi-board (vec (take 9 (repeat empty-board))))

(def lines (apply concat (vals {
  :rows [[0 1 2] [3 4 5] [6 7 8]]
  :columns [[0 3 6] [1 4 7] [2 5 8]]
  :diagonals [[2 4 6] [0 4 8]]})))

(defn opponent [symbol]
  (if (= :x symbol) :o :x))

(defn has-player-won? [symbol board]
  (some? (some
    (fn [line] (every? #(= symbol %1) (map board line)))
    lines)))

(defn has-player-won-the-multi-board? [symbol multiboard]
  (some? (some
    (fn [line] (every? #(has-player-won? symbol %1) (map multiboard line)))
    lines)))

(defn stringify-board [board]
  (apply format
    "%s|%s|%s\n-+-+-\n%s|%s|%s\n-+-+-\n%s|%s|%s" 
    (map #(if (some? %1) (name %1) " ") (flatten board))))

(defn split-into-layers [multiboard]
  (flatten (map
    #(clojure.string/split (stringify-board %1) #"\n")
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
      (clojure.string/join "\n" [
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

(defn get-board-choice [multiboard]
  (loop []
    (let [board-choice (- (parse-long (read-line)) 1)]
     (cond
       (or (< board-choice 0) (> board-choice 8)) (do (println "Board must be 1 to 9") (recur))
       (has-player-won? :x (multiboard board-choice)) (do (println "Board must not be won") (recur))
       (has-player-won? :o (multiboard board-choice)) (do (println "Board must not be won") (recur))
       (not-any? #(= nil %1) (multiboard board-choice)) (do (println "Board must not be full") (recur))
       :else board-choice))))

(defn get-square-choice []
  (loop []
    (let [square-choice (- (parse-long (read-line)) 1)]
     (cond
       (or (< square-choice 0) (> square-choice 8)) (do (println "Square must be 1 to 9") (recur))
       :else square-choice))))

(defn get-move [multiboard]
  (clear-screen)
  (println (stringify-multi-board multiboard))
  (let [board-choice (get-board-choice multiboard)
        square-choice (get-square-choice)]
    [board-choice square-choice]))

(defn title-screen []
  (clear-screen)
  (println 
    (clojure.string/replace
      (clojure.string/join "\n" [
        "           _   _ _ _   _                 _                "
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

(defn play-the-game []
  (println (loop [multiboard empty-multi-board symbol :x]
    (cond
      (has-player-won-the-multi-board? :x multiboard) "x won!"
      (has-player-won-the-multi-board? :o multiboard) "o won!"
      false "is-multi-board-filled? cat's game" ; todo
      :continue (recur (assoc-in multiboard (get-move multiboard) symbol) (opponent symbol))))))

(defn -main [& args]
  (title-screen)
  (play-the-game))

