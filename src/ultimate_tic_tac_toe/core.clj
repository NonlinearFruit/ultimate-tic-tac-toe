(ns ultimate-tic-tac-toe.core)

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

(defn print-multi-board [multiboard]
  (println 
      (clojure.string/join "\n" [
        "                                      "
        "                                      "
        "                                      "
        "                  |         |         "
        "            | |   |  1|2|3  |   | |   "
        "           -+-+-  |  -+-+-  |  -+-+-  "
        "            | |   |  4|5|6  |   | |   "
        "           -+-+-  |  -+-+-  |  -+-+-  "
        "            | |   |  7|8|9  |   | |   "
        "                  |         |         "
        "         ---------+---------+---------"
        "                  |         |         "
        "           X|X|O  |   | |   |   | |   "
        "           -+-+-  |  -+-+-  |  -+-+-  "
        "           O|X|X  |  O|X|   |   | |   "
        "           -+-+-  |  -+-+-  |  -+-+-  "
        "           O|O|X  |   | |   |   | |   "
        "                  |         |         "
        "         ---------+---------+---------"
        "                  |         |         "
        "           X   X  |  OOOOO  |   | |   "
        "            X X   |  O   O  |  -+-+-  "
        "             X    |  O   O  |   | |   "
        "            X X   |  O   O  |  -+-+-  "
        "           X   X  |  OOOOO  |   | |   "
        "                  |         |         "
        "                                      "])))

(defn print-board-selection []
  (println 
      (clojure.string/join "\n" [
        "                                      "
        "                                      "
        "                                      "
        "                  |         |         "
        "             1    |   222   |  3333   "
        "             1    |  2   2  |      3  "
        "             1    |     2   |   333   "
        "             1    |    2    |      3  "
        "             1    |   2222  |  3333   "
        "                  |         |         "
        "         ---------+---------+---------"
        "                  |         |         "
        "              4   |   5555  |  66666  "
        "           4  4   |  5      |  6      "
        "           4444   |  55555  |  66666  "
        "              4   |      5  |  6   6  "
        "              4   |  55555  |  66666  "
        "                  |         |         "
        "         ---------+---------+---------"
        "                  |         |         "
        "           77777  |   888   |  99999  "
        "               7  |  8   8  |  9   9  "
        "              7   |   888   |  99999  "
        "             7    |  8   8  |      9  "
        "            7     |   888   |  99999  "
        "                  |         |         "
        "                                      "])))

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
        "                                                          "])
      "?" "\\")))

(defn -main
  [& args]
  (print-title)
  (println "                <<Press enter to start>>")
  (read-line)
  (clear-screen)
  (print-board-selection)
  (print "Board: ")
  (flush)
  (println "hmm... " (read-line))
  (print "Square: ")
  (flush)
  (println "hmm... " (read-line))
  (println (stringify-multi-board [
        [nil nil nil nil nil nil nil nil nil]
        [nil nil nil nil nil nil nil nil nil]
        [nil nil nil nil nil nil nil nil nil]
        [nil nil nil nil nil nil nil nil nil]
        [nil nil nil nil nil nil nil nil nil]
        [nil nil nil nil nil nil nil nil nil]
        [nil nil nil nil nil nil nil nil nil]
        [nil nil nil nil nil nil nil nil nil]
        [nil nil nil nil nil nil nil nil nil]])))

