(ns ultimate-tic-tac-toe.core)

(def empty-board (vec (take 9 (repeat nil))))
(def empty-multi-board (vec (take 9 (repeat empty-board))))

(def lines (apply concat (vals {
  :rows [[0 1 2] [3 4 5] [6 7 8]]
  :columns [[0 3 6] [1 4 7] [2 5 8]]
  :diagonals [[2 4 6] [0 4 8]]})))

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

(defn get-move [multiboard]
  (println (stringify-multi-board multiboard))
    [(parse-long (read-line)) (parse-long (read-line))])

(defn clear-screen []
  (print "\033[H\033[2J"))

(defn print-title []
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
      "?" "\\")))

(defn -main [& args]
  (print-title)
  (read-line)
  (clear-screen)
  (println (loop [multiboard empty-multi-board symbol :x]
    (cond
      (has-player-won-the-multi-board? :x multiboard) "x won!"
      (has-player-won-the-multi-board? :o multiboard) "o won!"
      false "is-multi-board-filled? cat's game"
      :continue (recur (assoc-in multiboard (get-move multiboard) symbol) (if (= symbol :x) :o :x))))))

