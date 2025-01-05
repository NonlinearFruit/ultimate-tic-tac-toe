(ns ultimate-tic-tac-toe.core)

(def lines {
  :rows [[0 1 2] [3 4 5] [6 7 8]]
  :columns [[0 3 6] [1 4 7] [2 5 8]]
  :diagonals [[2 4 6] [0 4 8]]})

(defn has-player-won? [symbol board]
  (not (empty? (filter
    (fn [line] (= #{symbol} (into #{} (map #(board %1) line))))
    (apply concat (vals lines))))))

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
  (print "Board: ")
  (flush)
  (println "hmm... " (read-line))
  (print "Square: ")
  (flush)
  (println "hmm... " (read-line)))

