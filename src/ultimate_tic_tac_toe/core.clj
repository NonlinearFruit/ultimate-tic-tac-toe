(ns ultimate-tic-tac-toe.core)

(defn -main
  [& args]
  (println "Hello World"))

(def lines {
  :rows [[0 1 2] [3 4 5] [6 7 8]]
  :columns [[0 3 6] [1 4 7] [2 5 8]]
  :diagonals [[2 4 6] [0 4 8]]})

(defn has-player-won? [symbol board]
  (not (empty? (filter
    (fn [line] (= #{symbol} (into #{} (map #(board %1) line))))
    (apply concat (vals lines))))))
