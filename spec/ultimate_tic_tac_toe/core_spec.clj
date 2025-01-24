(ns ultimate-tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [ultimate-tic-tac-toe.core :refer :all]))

(def n [nil nil nil nil nil nil nil nil nil])
(def x [:x :x :x nil nil nil nil nil nil])
(def o [:o :o :o nil nil nil nil nil nil])
(def c [:x :o :x :x :o :x :o :x :o])

(defn fake-player [moves]
  (let [reusable-moves (atom moves)]
    (fn [multiboard last-move]
      (let [move (first @reusable-moves)]
        (swap! reusable-moves rest)
        move))))

(describe "_ultimate_ tic tac toe"
          (context "opponent"
            (it "is :x for :o"
                (should= :o (opponent :x)))
            (it "is :o for :x"
                (should= :x (opponent :o))))

          (context "possible board choices"
            (it "has all boards available when multiboard is empty"
                (should= (range 9) (possible-board-choices [n n n n n n n n n])))
            (it "has incomplete board"
                (should-contain 2 (possible-board-choices [n n (assoc n 0 :x) n n n n n n])))
            (it "ignores board won by o"
                (should-not-contain 3 (possible-board-choices [n n n o n n n n n])))
            (it "ignores board won by cat"
                (should-not-contain 4 (possible-board-choices [n n n n c n n n n])))
            (it "ignores board won by x"
                (should-not-contain 1 (possible-board-choices [n x n n n n n n n])))
            (it "only allows the square choice of the last move"
                (should= [1] (possible-board-choices [n n n n n n n n n] [3 1])))
            (it "has all (other) boards when the last square choice is a completed board"
                (should= (range 1 9) (possible-board-choices [x n n n n n n n n] [3 0]))))

          (context "possible square choices"
            (it "has all squares available when board is empty"
                (should= (range 9) (possible-square-choices n)))
            (it "ignores square that is filled"
                (should-not-contain 2 (possible-square-choices (assoc n 2 :x)))))

          (context "has the player won the board?"
            (it "has no win for nil board"
                (should= false (has-player-won? :x n)))
            (it "has no win for cats game"
                (should= false (has-player-won? :x c)))
            (it "has no win for opponents game"
                (should= false (has-player-won? :x [:o :o :o nil nil nil nil nil nil])))

            (it "finds top row win"
                (should= true (has-player-won? :x [:x :x :x nil nil nil nil nil nil])))
            (it "finds middle row win"
                (should= true (has-player-won? :x [nil nil nil :x :x :x nil nil nil])))
            (it "finds bottom row win"
                (should= true (has-player-won? :x [nil nil nil nil nil nil :x :x :x])))

            (it "finds first column win"
                (should= true (has-player-won? :x [:x nil nil :x nil nil :x nil nil])))
            (it "finds middle column win"
                (should= true (has-player-won? :x [nil :x nil nil :x nil nil :x nil])))
            (it "finds last column win"
                (should= true (has-player-won? :x [nil nil :x nil nil :x nil nil :x])))

            (it "finds forward diagonal win"
                (should= true (has-player-won? :x [nil nil :x nil :x nil :x nil nil])))
            (it "finds backward diagonal win"
                (should= true (has-player-won? :x [:x nil nil nil :x nil nil nil :x]))))

          (context "has the player won the multiboard?"
            (it "has no win for nil board"
                (should= false (has-player-won-the-multi-board? :x [n n n n n n n n n])))
            (it "has no win for cats game"
                (should= false (has-player-won-the-multi-board? :x [x o x x o x o x o])))
            (it "has no win for opponents game"
                (should= false (has-player-won-the-multi-board? :x [o o o n n n n n n])))

            (it "finds top row win"
                (should= true (has-player-won-the-multi-board? :x [x x x n n n n n n])))
            (it "finds middle row win"
                (should= true (has-player-won-the-multi-board? :x [n n n x x x n n n])))
            (it "finds bottom row win"
                (should= true (has-player-won-the-multi-board? :x [n n n n n n x x x])))

            (it "finds first column win"
                (should= true (has-player-won-the-multi-board? :x [x n n x n n x n n])))
            (it "finds middle column win"
                (should= true (has-player-won-the-multi-board? :x [n x n n x n n x n])))
            (it "finds last column win"
                (should= true (has-player-won-the-multi-board? :x [n n x n n x n n x])))

            (it "finds forward diagonal win"
                (should= true (has-player-won-the-multi-board? :x [n n x n x n x n n])))
            (it "finds backward diagonal win"
                (should= true (has-player-won-the-multi-board? :x [x n n n x n n n x]))))

          (context "stringy board"
            (it "makes a blank board"
                (should= " | | \n-+-+-\n | | \n-+-+-\n | | " (stringify-board n)))
            (it "populates each cell dynamically"
                (should= "a|b|c\n-+-+-\nd|e|f\n-+-+-\ng|h|i" (stringify-board [:a :b :c :d :e :f :g :h :i]))))

          (context "split multiboard into layers"
            (it "turns a single board into 5 layers"
                (should= 5 (count (split-into-layers [n]))))
            (it "turns two boards into 10 layers"
                (should= 10 (count (split-into-layers [n n])))))

          (context "order layers for inserting"
            (it "puts layers in correct order"
                (should= (range 0 45) (order-layers-for-inserting [0  3  6  9 12   1  4  7 10 13   2  5  8 11 14
                                                                   15 18 21 24 27  16 19 22 25 28  17 20 23 26 29
                                                                   30 33 36 39 42  31 34 37 40 43  32 35 38 41 44])))
            (it "moves the 5th layer to the 13th spot"
                (should= :tracer (nth
                                   (order-layers-for-inserting [nil nil nil nil :tracer nil nil nil nil nil nil nil nil nil nil])
                                   12))))

          (context "play the game"
            (it "returns :x when the first player wins"
                (should= :x (play-the-game
                              (fake-player [[0 0] [0 4] [0 8] [4 0] [4 4] [4 8] [8 0] [8 4] [8 8]])
                              (fake-player (repeat [0 1])))))
            (it "returns :c when the cat wins"
                (should= :c (play-the-game
                              (fake-player [[0 1] [0 3] [0 5] [0 6] [0 8]
                                            [1 0] [1 2] [1 4] [1 7]
                                            [2 1] [2 3] [2 5] [2 6] [2 8]
                                            [3 0] [3 2] [3 4] [3 7]
                                            [4 1] [4 3] [4 5] [4 6] [4 8]
                                            [5 0] [5 2] [5 4] [5 7]
                                            [6 1] [6 3] [6 5] [6 6] [6 8]
                                            [7 0] [7 2] [7 4] [7 7]
                                            [8 1] [8 3] [8 5] [8 6] [8 8]])
                              (fake-player [[0 0] [0 2] [0 4] [0 7]
                                            [1 1] [1 3] [1 5] [1 6] [1 8]
                                            [2 0] [2 2] [2 4] [2 7]
                                            [3 1] [3 3] [3 5] [3 6] [3 8]
                                            [4 0] [4 2] [4 4] [4 7]
                                            [5 1] [5 3] [5 5] [5 6] [5 8]
                                            [6 0] [6 2] [6 4] [6 7]
                                            [7 1] [7 3] [7 5] [7 6] [7 8]
                                            [8 0] [8 2] [8 4] [8 7]]))))
            (it "returns :o when the second player wins"
                (should= :o (play-the-game
                              (fake-player (repeat [0 1]))
                              (fake-player [[0 0] [0 4] [0 8] [4 0] [4 4] [4 8] [8 0] [8 4] [8 8]]))))))

(run-specs)
