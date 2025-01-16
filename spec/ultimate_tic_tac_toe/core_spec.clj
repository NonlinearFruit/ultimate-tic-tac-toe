(ns ultimate-tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [ultimate-tic-tac-toe.core :refer :all]))

(describe "_ultimate_ tic tac toe"
  (context "has player won?"
    (it "has no win for nil board"
      (should= false (has-player-won? :x [nil nil nil nil nil nil nil nil nil])))
    (it "has no win for cats game"
      (should= false (has-player-won? :x [:x :o :x :x :o :x :o :x :o])))
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

  (context "stringy board"
    (it "makes a blank board"
      (should= " | | \n-+-+-\n | | \n-+-+-\n | | " (stringify-board [nil nil nil nil nil nil nil nil nil])))
    (it "populates each cell dynamically"
      (should= "a|b|c\n-+-+-\nd|e|f\n-+-+-\ng|h|i" (stringify-board [:a :b :c :d :e :f :g :h :i]))))

  ; (context "stringy multiboard"
  ;   (it "makes a blank multiboard"
  ;     (should=  "         |         |         \n   | |   |   | |   |   | |   \n  -+-+-  |  -+-+-  |  -+-+-  \n   | |   |   | |   |   | |   \n  -+-+-  |  -+-+-  |  -+-+-  \n   | |   |   | |   |   | |   \n         |         |         \n---------+---------+---------\n         |         |         \n   | |   |   | |   |   | |   \n  -+-+-  |  -+-+-  |  -+-+-  \n   | |   |   | |   |   | |   \n  -+-+-  |  -+-+-  |  -+-+-  \n   | |   |   | |   |   | |   \n         |         |         \n---------+---------+---------\n         |         |         \n   | |   |   | |   |   | |   \n  -+-+-  |  -+-+-  |  -+-+-  \n   | |   |   | |   |   | |   \n  -+-+-  |  -+-+-  |  -+-+-  \n   | |   |   | |   |   | |   \n         |         |         " (stringify-multi-board [
  ;       [nil nil nil nil nil nil nil nil nil]
  ;       [nil nil nil nil nil nil nil nil nil]
  ;       [nil nil nil nil nil nil nil nil nil]
  ;       [nil nil nil nil nil nil nil nil nil]
  ;       [nil nil nil nil nil nil nil nil nil]
  ;       [nil nil nil nil nil nil nil nil nil]
  ;       [nil nil nil nil nil nil nil nil nil]
  ;       [nil nil nil nil nil nil nil nil nil]
  ;       [nil nil nil nil nil nil nil nil nil]])))
  ;   (it "makes a realistic multiboard"
  ;     (should= "" (stringify-multi-board [
  ;       [:x  :o  :x  :x  :o  :x  :o  :x  :o]
  ;       [:x  :o  :x  :x  :o  :x  :o  :x  :o]
  ;       [:x  :o  :x  :x  :o  :x  :o  :x  :o]
  ;       [nil nil nil nil nil nil nil nil nil]
  ;       [:x  :o  :x  :x  :o  :x  :o  :x  :o]
  ;       [:x  :o  :x  :x  :o  :x  :o  :x  :o]
  ;       [:x  :o  :x  :x  :o  :x  :o  :x  :o]
  ;       [nil nil nil nil nil nil nil nil nil]
  ;       [:x  :o  :x  :x  :o  :x  :o  :x  :o]
  ;       [:x  :o  :x  :x  :o  :x  :o  :x  :o]
  ;       [:x  :o  :x  :x  :o  :x  :o  :x  :o]
  ;       [nil nil nil nil nil nil nil nil nil]]))))
          
  (context "split multiboard into layers"
    (it "turns a single board into 5 layers"
      (should= 5 (count (split-into-layers [[nil nil nil nil nil nil nil nil nil]]))))
    (it "turns two boards into 10 layers"
      (should= 10 (count (split-into-layers [[nil nil nil nil nil nil nil nil nil] [nil nil nil nil nil nil nil nil nil]])))))
          
  (context "order layers for inserting"
    (it "puts layers in correct order"
      (should= (range 0 45) (order-layers-for-inserting [
         0  3  6  9 12   1  4  7 10 13   2  5  8 11 14
        15 18 21 24 27  16 19 22 25 28  17 20 23 26 29
        30 33 36 39 42  31 34 37 40 43  32 35 38 41 44])))
    (it "moves the 5th layer to the 13th spot"
      (should= :tracer (nth (order-layers-for-inserting [nil nil nil nil :tracer nil nil nil nil nil nil nil nil nil nil]) 12)))))

(run-specs)
